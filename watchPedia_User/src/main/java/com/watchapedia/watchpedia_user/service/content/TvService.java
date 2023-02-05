package com.watchapedia.watchpedia_user.service.content;


import com.watchapedia.watchpedia_user.model.dto.content.TvDto;
import com.watchapedia.watchpedia_user.model.entity.content.Tv;
import com.watchapedia.watchpedia_user.model.entity.content.ajax.Star;
import com.watchapedia.watchpedia_user.model.network.response.content.TvResponse;
import com.watchapedia.watchpedia_user.model.repository.content.TvRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

@Service
@RequiredArgsConstructor
public class TvService {

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

    @Transactional(readOnly = true) //데이터를 불러오기만 할 때(수정X)
    public List<Tv> searchTvs() {
        return tvRepository.findAll();
    }

}
