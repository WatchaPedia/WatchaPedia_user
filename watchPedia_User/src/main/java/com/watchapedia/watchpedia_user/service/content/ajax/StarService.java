package com.watchapedia.watchpedia_user.service.content.ajax;

import com.watchapedia.watchpedia_user.model.dto.content.MovieDto;
import com.watchapedia.watchpedia_user.model.dto.content.ajax.StarDto;
import com.watchapedia.watchpedia_user.model.entity.content.ajax.Star;
import com.watchapedia.watchpedia_user.model.network.request.ajax.StarRequest;
import com.watchapedia.watchpedia_user.model.network.response.content.StarResponse;
import com.watchapedia.watchpedia_user.model.repository.content.MovieRepository;
import com.watchapedia.watchpedia_user.model.repository.content.ajax.StarRepository;
import com.watchapedia.watchpedia_user.model.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class StarService {
    final MovieRepository movieRepository;
    final StarRepository starRepository;
    @Transactional(readOnly = true) //데이터를 불러오기만 할 때(수정X)
    public List<MovieDto> movieList(){
        return movieRepository.findAll().stream().map(MovieDto::from).collect(Collectors.toList());
    }
//    @Transactional(readOnly = true) //데이터를 불러오기만 할 때(수정X)
//    public List<TVDto> tvList(){
//        return movieRepository.findAll().stream().map(MovieDto::from).toList();
//    }
//    @Transactional(readOnly = true) //데이터를 불러오기만 할 때(수정X)
//    public List<BookDto> bookList(){
//        return movieRepository.findAll().stream().map(MovieDto::from).toList();
//    }
//    @Transactional(readOnly = true) //데이터를 불러오기만 할 때(수정X)
//    public List<WebtoonDto> webList(){
//        return movieRepository.findAll().stream().map(MovieDto::from).toList();
//    }

    final UserRepository userRepository;
    public StarResponse starSave(StarRequest request){
        Star starEntity = starRepository.findByStarContentTypeAndStarContentIdxAndStarUserIdx(
                request.starContentType(),request.starContentIdx(), request.starUserIdx()
        );
        if(starEntity==null){
            StarDto starDto = StarDto.of(request.starContentType(), request.starContentIdx(),
                    request.starUserIdx(), request.starPoint());
            Star starSave = Star.of(starDto.starContentType(), starDto.starContentIdx(),
                    starDto.starUserIdx(), starDto.starPoint());
            starRepository.save(starSave);
            return StarResponse.from(starSave);
        }else{
            starEntity.setStarPoint(request.starPoint());
            starRepository.save(starEntity);
            return StarResponse.from(starEntity);
        }
    }

    public void starDelete(StarRequest request){
        Star starEntity = starRepository.findByStarContentTypeAndStarContentIdxAndStarUserIdx(
                request.starContentType(),request.starContentIdx(), request.starUserIdx()
        );
        starRepository.delete(starEntity);
    }

    public StarResponse findStar(
            String starContentType, Long starContentIdx, Long starUserIdx
    ){
        try {
            Star hasStar = starRepository.findByStarContentTypeAndStarContentIdxAndStarUserIdx(
                    starContentType, starContentIdx, starUserIdx
            );
            return StarResponse.from(StarDto.from(hasStar));
        }catch(Exception e){
            return null;
        }
    }
}
