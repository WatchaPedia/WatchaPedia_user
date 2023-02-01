package com.watchapedia.watchpedia_user.controller;

import com.watchapedia.watchpedia_user.model.entity.content.ajax.Star;
import com.watchapedia.watchpedia_user.model.network.Header;
import com.watchapedia.watchpedia_user.model.network.request.SelectType;
import com.watchapedia.watchpedia_user.model.network.response.content.MovieResponse;
import com.watchapedia.watchpedia_user.model.repository.comment.CommentRepository;
import com.watchapedia.watchpedia_user.model.repository.content.MovieRepository;
import com.watchapedia.watchpedia_user.model.repository.content.ajax.StarRepository;
import com.watchapedia.watchpedia_user.model.repository.UserRepository;
import com.watchapedia.watchpedia_user.service.content.ajax.StarService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class EstimateController {
    final StarRepository starRepository;
    final StarService starService;
    private final MovieRepository movieRepository;
    final CommentRepository commentRepository;
    final UserRepository userRepository;

    @GetMapping("/estimate") // http://localhost:8080/api/estimate
    public Header<List<MovieResponse>> estimate(
            SelectType selectType
    ){
        Long userIdx = 12L;
//        리스트 인덱스 삭제 문제 때문에 new ArrayList로 감쌈
        List<MovieResponse> movieList = new ArrayList<>(starService.movieList().stream().map(MovieResponse::from).toList());
//        평점을 남겼던 영화라면 인덱스 삭제
        int j = 0;
        for(int i=0; i<movieList.size(); i++){
            MovieResponse mov = movieList.get(j);
            if(mov.starList().size() > 0){
                for(Star star : mov.starList()){
                    if(star.getStarUserIdx() == userIdx){
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

//    @GetMapping(path="/estimate") // http://localhost:8080/estimate
//    public String estimate(
//            SelectType selectType,
//            ModelMap map
//    ){
//        Long userIdx = 1L;
////        리스트 인덱스 삭제 문제 때문에 new ArrayList로 감쌈
//        List<MovieResponse> movieList = new ArrayList<>(starService.movieList().stream().map(MovieResponse::from).toList());
////        평점을 남겼던 영화라면 인덱스 삭제
//        int j = 0;
//        for(int i=0; i<movieList.size(); i++){
//            MovieResponse mov = movieList.get(j);
//            if(mov.starList().size() > 0){
//                for(Star star : mov.starList()){
//                    System.out.println("별점 : " + star.getStarPoint());
//                    if(star.getStarUserIdx().getUserIdx() == userIdx){
//                        movieList.remove(j);
//                        j--;
//                        break;
//                    }
//                }
//            }
//            j++;
//        }
//        map.addAttribute("content", movieList);
//        return "/valueContent";
//    }

}
