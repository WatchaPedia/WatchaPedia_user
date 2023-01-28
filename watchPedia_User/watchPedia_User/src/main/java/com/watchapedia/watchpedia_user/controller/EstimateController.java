package com.watchapedia.watchpedia_user.controller;

import com.watchapedia.watchpedia_user.model.entity.Star;
import com.watchapedia.watchpedia_user.model.network.Header;
import com.watchapedia.watchpedia_user.model.network.request.SelectType;
import com.watchapedia.watchpedia_user.model.network.request.StarRequest;
import com.watchapedia.watchpedia_user.model.network.response.MovieResponse;
import com.watchapedia.watchpedia_user.model.network.response.StarResponse;
import com.watchapedia.watchpedia_user.model.repository.MovieRepository;
import com.watchapedia.watchpedia_user.model.repository.StarRepository;
import com.watchapedia.watchpedia_user.service.StarService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.tags.form.SelectTag;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class EstimateController {
    final StarRepository starRepository;
    final StarService starService;
    private final MovieRepository movieRepository;

    @GetMapping("/estimate") // http://localhost:9090/api/estimate
    public Header<List<MovieResponse>> estimate(
            SelectType selectType
    ){
        Long userIdx = 1L;
//        리스트 인덱스 삭제 문제 때문에 new ArrayList로 감쌈
        List<MovieResponse> movieList = new ArrayList<>(starService.movieList().stream().map(MovieResponse::from).toList());
//        평점을 남겼던 영화라면 인덱스 삭제
        int j = 0;
        for(int i=0; i<movieList.size(); i++){
            MovieResponse mov = movieList.get(j);
            if(mov.starList().size() > 0){
                for(Star star : mov.starList()){
                    if(star.getStarUserIdx().getUserIdx() == userIdx){
                        movieList.remove(j);
                        j--;
                        break;
                    }
                }
            }
            j++;
        }
        return Header.OK(movieList);
    }

}
