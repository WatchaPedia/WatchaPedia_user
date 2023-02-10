package com.watchapedia.watchpedia_user.service.content;


import com.watchapedia.watchpedia_user.model.dto.content.WebtoonDto;
import com.watchapedia.watchpedia_user.model.entity.content.Webtoon;
import com.watchapedia.watchpedia_user.model.entity.content.ajax.Star;
import com.watchapedia.watchpedia_user.model.network.response.content.WebtoonResponse;
import com.watchapedia.watchpedia_user.model.repository.content.WebtoonRepository;
import com.watchapedia.watchpedia_user.model.repository.content.ajax.StarRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

@Service
@RequiredArgsConstructor
public class WebtoonService {
    final StarRepository starRepository;
    final WebtoonRepository webtoonRepository;
    @Transactional(readOnly = true)
    public WebtoonResponse webtoonView(Long webIdx){
        WebtoonDto webtoon = webtoonRepository.findById(webIdx).map(WebtoonDto::from).get();
        return WebtoonResponse.from(webtoon);
    }

    @Transactional(readOnly = true)
    public List<WebtoonResponse> similarGenre(String webGenre, Long webIdx){
        List<WebtoonResponse> result = new ArrayList<>();

        List<Long> webIdxList = new ArrayList<>(16);
        if(webGenre.contains("/")){
            List<String> genreList = Arrays.stream(webGenre.split("/")).toList();
            HashMap<Long, Integer> containWebtoon = new HashMap<>();
            for(String idx: genreList){
                for(Webtoon webtoon: webtoonRepository.findByWebGenreContaining(idx)){
                    containWebtoon.put(webtoon.getWebIdx(),
                            containWebtoon.get(webtoon.getWebIdx()) != null ?
                                    containWebtoon.get(webtoon.getWebIdx()) + 1 : 1
                    );
                }
            }
            List<Map.Entry<Long, Integer>> entryList = new LinkedList<>(containWebtoon.entrySet());
            entryList.sort(((o1, o2) -> o2.getValue() - o1.getValue()));
            for(Map.Entry<Long, Integer> entry : entryList){
                webIdxList.add(entry.getKey());
            }
        }else{
            for(Webtoon idx: webtoonRepository.findByWebGenreContaining(webGenre)){
                webIdxList.add(idx.getWebIdx());
            }
        }

        for(Long idx: webIdxList){
            if(idx == webIdx) continue;
            WebtoonDto dto = WebtoonDto.from(webtoonRepository.getReferenceById(idx));
            double sum = 0;

            if(dto.starList().size() > 0){
                for(Star star: dto.starList()){
                    sum += star.getStarPoint();
                }
                result.add(WebtoonResponse.of(dto.webIdx(), dto.webThumbnail(),dto.webTitle(),dto.webWatch(),Math.round((sum / dto.starList().size()) * 10.0) / 10.0));
            }else{
                result.add(WebtoonResponse.of(dto.webIdx(), dto.webThumbnail(),dto.webTitle(),dto.webWatch(),0.0));
            }
        }
        return result;
    }

    @Transactional(readOnly = true)
    public List<WebtoonDto> webtoons() {
        //빈 웹툰리스폰스 리스트
        List<WebtoonDto> result = new ArrayList<>();

        List<Webtoon> webtoonList = webtoonRepository.findAll();

        for(Webtoon webtoon : webtoonList){
            double sum = 0;
            int starCount = 0;
            for(Star star : webtoon.getStar()){
                sum += star.getStarPoint();
                starCount = webtoon.getStar().size();
            }
            Double avg = Math.round((sum / starCount) * 10.0) / 10.0;
            result.add(WebtoonDto.from(webtoon, avg));
        }
        return result;
    }
    @Transactional(readOnly = true)
    public WebtoonResponse webtoonWithRole(Long webIdx, Long perIdx){

        WebtoonDto webtoon = webtoonRepository.findById(webIdx).map(WebtoonDto::from).get();
        List<Star> starResponseList = starRepository.findByStarContentTypeAndStarContentIdx("webtoon", webtoon.webIdx());
        int starCount= starResponseList.size();
        float starPoint = 0;
        for(Star star : starResponseList){
            starPoint = starPoint + (star.getStarPoint()).intValue();
        }
        float starAvg = 0;
        if(starCount != 0){
            float avg = (starPoint / starCount);
            float avg2 = (float) ((avg*100)/100.0);
            starAvg = (float)(Math.round(avg2*10)/10.0);
        }

        boolean isWatcha = false;
        boolean isNetflix = false;

        try{
            if(!webtoon.webWatch().isEmpty()){
                String[] webWatch = webtoon.webWatch().split(",");
                for(String webWatchs : webWatch){
                    if(webWatchs.contains("aHR0cHM6Ly93YXRjaGEuY29tL3dhdGNoL21")){
                        isWatcha = true;
                    }
                    if(webWatchs.contains("aHR0cHM6Ly93d3cubmV0ZmxpeC5jb20vdGl0bGUvOD")){
                        isNetflix = true;
                    }
                }

            }
        }catch (NullPointerException e){
            e.printStackTrace();
        }

        int num = webtoon.webPeople().indexOf(String.valueOf(perIdx));  // 특정문자 => 문자열로 변환 => 동일한 부분을 찾기(영화 사람에서) => 결과를 인덱스로 반환 => 다시 숫자형으로 변환
        String webtoonPersonRole = webtoon.webPeople().substring(num+1);  // 숫자 + ( 이후이므로 +1를 함 => (시작번을 포함해서)부터 자르기 => 00 | 00), 숫자(00 | 00)
        String role2 = "";
        String role3 = "";
        String role = "";
        if(webtoonPersonRole.length() > -1){
            String[] tvRoles = webtoonPersonRole.split("\\)");     // String 안에 이스케이프 문자인 \를 써 주려면 \\라고 써 줘야 한다. 따라서 \\라고 쓰는 것이다. 그래서 \\)이라고 쓰면 정규식 쪽에서는 \)라고 인식을 하고 실제 )을 찾게 되는 것
            role2 = tvRoles[0];
            String[] tvRoles2 = role2.split("\\|");
            role3 = tvRoles2[0];
            String[] tvRoles3 = role3.split("\\(");
            role = tvRoles3[1];
        }
        return WebtoonResponse.fromis(webtoon, role, starAvg, isWatcha, isNetflix);
    }
}
