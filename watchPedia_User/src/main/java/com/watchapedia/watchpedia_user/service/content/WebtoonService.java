package com.watchapedia.watchpedia_user.service.content;


import com.watchapedia.watchpedia_user.model.dto.content.WebtoonDto;
import com.watchapedia.watchpedia_user.model.entity.content.Webtoon;
import com.watchapedia.watchpedia_user.model.entity.content.ajax.Star;
import com.watchapedia.watchpedia_user.model.network.response.content.WebtoonResponse;
import com.watchapedia.watchpedia_user.model.repository.content.WebtoonRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

@Service
@RequiredArgsConstructor
public class WebtoonService {

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

    @Transactional(readOnly = true) //데이터를 불러오기만 할 때(수정X)
    public List<Webtoon> searchWebtoons() {
        return webtoonRepository.findAll();
    }

}
