package com.watchapedia.watchpedia_user.service.content;


import com.watchapedia.watchpedia_user.model.dto.content.TvDto;
import com.watchapedia.watchpedia_user.model.entity.content.Tv;
import com.watchapedia.watchpedia_user.model.entity.content.ajax.Star;
import com.watchapedia.watchpedia_user.model.network.response.content.TvResponse;
import com.watchapedia.watchpedia_user.model.repository.content.TvRepository;
import com.watchapedia.watchpedia_user.model.repository.content.ajax.StarRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

@Service
@RequiredArgsConstructor
public class TvService {
    final StarRepository starRepository;
    final TvRepository tvRepository;
    @Transactional(readOnly = true)
    public TvResponse tvView(Long tvIdx){
        TvDto tv = tvRepository.findById(tvIdx).map(TvDto::from).get();
        return TvResponse.from(tv);
    }

    @Transactional(readOnly = true)
    public List<TvResponse> similarGenre(String tvGenre, Long tvIdx){
        List<TvResponse> result = new ArrayList<>();

        List<Long> tvIdxList = new ArrayList<>(16);
        if(tvGenre.contains("/")){
            List<String> genreList = Arrays.stream(tvGenre.split("/")).toList();
            HashMap<Long, Integer> containTv = new HashMap<>();
            for(String idx: genreList){
                for(Tv tv: tvRepository.findByTvGenreContaining(idx)){
                    containTv.put(tv.getTvIdx(),
                            containTv.get(tv.getTvIdx()) != null ?
                                    containTv.get(tv.getTvIdx()) + 1 : 1
                    );
                }
            }
            List<Map.Entry<Long, Integer>> entryList = new LinkedList<>(containTv.entrySet());
            entryList.sort(((o1, o2) -> o2.getValue() - o1.getValue()));
            for(Map.Entry<Long, Integer> entry : entryList){
                tvIdxList.add(entry.getKey());
            }
        }else{
            for(Tv idx: tvRepository.findByTvGenreContaining(tvGenre)){
                tvIdxList.add(idx.getTvIdx());
            }
        }

        for(Long idx: tvIdxList){
            if(idx == tvIdx) continue;
            TvDto dto = TvDto.from(tvRepository.getReferenceById(idx));
            double sum = 0;

            if(dto.starList().size() > 0){
                for(Star star: dto.starList()){
                    sum += star.getStarPoint();
                }
                result.add(TvResponse.of(dto.tvIdx(), dto.tvThumbnail(),dto.tvTitle(),dto.tvWatch(),Math.round((sum / dto.starList().size()) * 10.0) / 10.0));
            }else{
                result.add(TvResponse.of(dto.tvIdx(), dto.tvThumbnail(),dto.tvTitle(),dto.tvWatch(),0.0));
            }
        }
        return result;
    }


    @Transactional(readOnly = true)
    public List<TvDto> tvs() {
        List<TvDto> result = new ArrayList<>();
        List<TvDto> result2 = new ArrayList<>();
        List<Tv> tvList = tvRepository.findAll();

        for(Tv tv : tvList){
            double sum = 0;
            int starCount = 0;
            for(Star star : tv.getStar()){
                sum += star.getStarPoint();
                starCount = tv.getStar().size();
            }
            Double avg = Math.round((sum / starCount) * 10.0) / 10.0;
            result.add(TvDto.from(tv, avg));
        }
        for(int i = 0; i < 10; i++){
            result2.add(result.get(i));
        }
        return result2;
    }

    //
    @Transactional(readOnly = true)
    public List<TvDto> searchCountry(String country) {

        List<TvDto> result = new ArrayList<>();

        List<Tv> tvList2 = tvRepository.findByTvCountryContaining(country);

        for(Tv t : tvList2){
            double sum = 0;
            int starCount = 0;
            for(Star star : t.getStar()){
                sum += star.getStarPoint();
                starCount = t.getStar().size();
            }
            Double avg = Math.round((sum / starCount) * 10.0) / 10.0;
            result.add(TvDto.from(t, avg));
        }
        return result;
    }

    @Transactional(readOnly = true)
    public List<TvDto> searchTvDate(String tvMakingDate) {

        List<TvDto> result = new ArrayList<>();

        List<Tv> tvList2 = tvRepository.findByTvMakingDate(tvMakingDate);

        for(Tv t : tvList2){
            double sum = 0;
            int starCount = 0;
            for(Star star : t.getStar()){
                sum += star.getStarPoint();
                starCount = t.getStar().size();
            }
            Double avg = Math.round((sum / starCount) * 10.0) / 10.0;
            result.add(TvDto.from(t, avg));
        }
        return result;
    }

    @Transactional(readOnly = true)
    public List<TvDto> searchChannel(String tvChannel) {

        List<TvDto> result = new ArrayList<>();

        List<Tv> tvList2 = tvRepository.findByTvChannel(tvChannel);

        for(Tv t : tvList2){
            double sum = 0;
            int starCount = 0;
            for(Star star : t.getStar()){
                sum += star.getStarPoint();
                starCount = t.getStar().size();
            }
            Double avg = Math.round((sum / starCount) * 10.0) / 10.0;
            result.add(TvDto.from(t, avg));
        }
        return result;
    }

    @Transactional(readOnly = true)
    public List<TvDto> searchGenre(String tvGenre) {

        List<TvDto> result = new ArrayList<>();

        List<Tv> tvList2 = tvRepository.findByTvGenreContaining(tvGenre);

        for(Tv t : tvList2){
            double sum = 0;
            int starCount = 0;
            for(Star star : t.getStar()){
                sum += star.getStarPoint();
                starCount = t.getStar().size();
            }
            Double avg = Math.round((sum / starCount) * 10.0) / 10.0;
            result.add(TvDto.from(t, avg));
        }
        return result;
    }

    @Transactional(readOnly = true)
    public List<TvDto> searchTitle(String tvTitle) {

        List<TvDto> result = new ArrayList<>();

        List<Tv> tvList2 = tvRepository.findByTvTitleContaining(tvTitle);

        for(Tv t : tvList2){
            double sum = 0;
            int starCount = 0;
            for(Star star : t.getStar()){
                sum += star.getStarPoint();
                starCount = t.getStar().size();
            }
            Double avg = Math.round((sum / starCount) * 10.0) / 10.0;
            result.add(TvDto.from(t, avg));
        }
        return result;
    }
    //----------------------------------------------------------------------------------------------------

    @Transactional(readOnly = true)
    public TvResponse tvWithRole(Long tvIdx, Long perIdx){
        TvDto tv = tvRepository.findById(tvIdx).map(TvDto::from).get();

        List<Star> starResponseList = starRepository.findByStarContentTypeAndStarContentIdx("tv", tv.tvIdx());
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
            if(!tv.tvWatch().isEmpty()){
                String[] tvWatch = tv.tvWatch().split(",");
                for(String tvWatchs : tvWatch){
                    if(tvWatchs.contains("aHR0cHM6Ly93YXRjaGEuY29tL3dhdGNoL21")){
                        isWatcha = true;
                    }
                    if(tvWatchs.contains("aHR0cHM6Ly93d3cubmV0ZmxpeC5jb20vdGl0bGUvOD")){
                        isNetflix = true;
                    }
                }

            }
        }catch (NullPointerException e){
            e.printStackTrace();
        }



        int num = tv.tvPeople().indexOf(String.valueOf(perIdx));  // 특정문자 => 문자열로 변환 => 동일한 부분을 찾기(영화 사람에서) => 결과를 인덱스로 반환 => 다시 숫자형으로 변환
        String tvPersonRole = tv.tvPeople().substring(num+1);  // 숫자 + ( 이후이므로 +1를 함 => (시작번을 포함해서)부터 자르기 => 00 | 00), 숫자(00 | 00)
        String role2 = "";
        String role3 = "";
        String role = "";
        if(tvPersonRole.length() > -1){
            String[] tvRoles = tvPersonRole.split("\\)");     // String 안에 이스케이프 문자인 \를 써 주려면 \\라고 써 줘야 한다. 따라서 \\라고 쓰는 것이다. 그래서 \\)이라고 쓰면 정규식 쪽에서는 \)라고 인식을 하고 실제 )을 찾게 되는 것
            role2 = tvRoles[0];
            String[] tvRoles2 = role2.split("\\|");
            role3 = tvRoles2[0];
            String[] tvRoles3 = role3.split("\\(");
            role = tvRoles3[1];
        }
        return TvResponse.fromis(tv, role, starAvg, isWatcha, isNetflix);
    }

}
