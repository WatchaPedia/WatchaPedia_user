package com.watchapedia.watchpedia_user.service;

import com.watchapedia.watchpedia_user.model.dto.MovieDto;
import com.watchapedia.watchpedia_user.model.dto.StarDto;
import com.watchapedia.watchpedia_user.model.dto.TvDto;
import com.watchapedia.watchpedia_user.model.entity.Star;
import com.watchapedia.watchpedia_user.model.entity.User;
import com.watchapedia.watchpedia_user.model.network.request.StarRequest;
import com.watchapedia.watchpedia_user.model.network.response.MovieResponse;
import com.watchapedia.watchpedia_user.model.network.response.StarResponse;
import com.watchapedia.watchpedia_user.model.repository.MovieRepository;
import com.watchapedia.watchpedia_user.model.repository.StarRepository;
import com.watchapedia.watchpedia_user.model.repository.TvRepository;
import com.watchapedia.watchpedia_user.model.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class StarService {
    final MovieRepository movieRepository;
    final TvRepository tvRepository;
    final StarRepository starRepository;
    @Transactional(readOnly = true) //데이터를 불러오기만 할 때(수정X)
    public List<MovieDto> movieList(){
        return movieRepository.findAll().stream().map(MovieDto::from).collect(Collectors.toList());
    }
    @Transactional(readOnly = true) //데이터를 불러오기만 할 때(수정X)
    public List<TvDto> tvList(){
        return tvRepository.findAll().stream().map(TvDto::from).toList();
    }
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
                request.starContentType(),request.starContentIdx(), userRepository.getReferenceById(request.starUserIdx())
        );
        if(starEntity==null){
            StarDto starDto = StarDto.of(request.starContentType(), request.starContentIdx(),
                    userRepository.getReferenceById(request.starUserIdx()), request.starPoint());
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
                request.starContentType(),request.starContentIdx(), userRepository.getReferenceById(request.starUserIdx())
        );
        starRepository.delete(starEntity);
    }

    public StarResponse findStar(
            String starContentType, Long starContentIdx, Long starUserIdx
    ){
        try {
            Star hasStar = starRepository.findByStarContentTypeAndStarContentIdxAndStarUserIdx(
                    starContentType, starContentIdx, userRepository.getReferenceById(starUserIdx)
            );
            return StarResponse.from(StarDto.from(hasStar));
        }catch(Exception e){
            return null;
        }
    }
}
