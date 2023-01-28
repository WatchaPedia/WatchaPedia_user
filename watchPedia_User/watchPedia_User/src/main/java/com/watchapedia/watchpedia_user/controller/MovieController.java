package com.watchapedia.watchpedia_user.controller;

import com.watchapedia.watchpedia_user.model.entity.Star;
import com.watchapedia.watchpedia_user.model.entity.User;
import com.watchapedia.watchpedia_user.model.network.request.SelectType;
import com.watchapedia.watchpedia_user.model.network.request.StarRequest;
import com.watchapedia.watchpedia_user.model.network.response.*;
import com.watchapedia.watchpedia_user.model.repository.CommentRepository;
import com.watchapedia.watchpedia_user.model.repository.UserRepository;
import com.watchapedia.watchpedia_user.service.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

@Controller
@RequestMapping("")
@RequiredArgsConstructor
public class MovieController {
    final StarService starService;

    @GetMapping(path="/estimate") // http://localhost:9090/estimate
    public String estimate(
            SelectType selectType,
            ModelMap map
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
                    System.out.println("별점 : " + star.getStarPoint());
                    if(star.getStarUserIdx().getUserIdx() == userIdx){
                        movieList.remove(j);
                        j--;
                        break;
                    }
                }
            }
            j++;
        }
        map.addAttribute("content", movieList);
        return "/valueContent";
    }

    @PostMapping("/estimate") // http://localhost:9090/estimate
    @ResponseBody
    public StarAndCommentResponse starSave(
            @RequestBody StarRequest request
    ){
        if(request.starPoint()==0){
            starService.starDelete(request);
        }else{
            StarResponse starResponse = starService.starSave(request);
        }
        String comm = null;
        try {
            comm = commentRepository.findByCommContentTypeAndCommContentIdxAndCommUserIdx(
                    request.starContentType(), request.starContentIdx(), userRepository.getReferenceById(request.starUserIdx())
            ).getCommText();
        }catch(Exception e){
            System.out.println("댓글이 없습니다.");
        }
        return StarAndCommentResponse.of(request.starPoint(),comm);
    }

    final MovieService movieService;
    final PersonService personService;
    private final CommentRepository commentRepository;
    private final UserRepository userRepository;
    private final WishService wishService;
    private final WatchService watchService;
    private final HateService hateService;

    @GetMapping(path="/movie/main")
    public String tv(ModelMap map){
        map.addAttribute("tvs", movieService.searchMovies());
        return "/movie/MovieMain";
    }

    @GetMapping("/movie/{movieIdx}") // http://localhost:9090/movie/1
    public String movieDetail(
        @PathVariable Long movieIdx,
        ModelMap map
    ){
        User user = userRepository.getReferenceById(3L);

        MovieResponse movie = movieService.movieView(movieIdx);

//      평균 별점
        double sum = 0;
        double avgStar = 0;
        if(movie.starList().size() > 0){
            for(int i=0; i<movie.starList().size(); i++){
                sum += movie.starList().get(i).getStarPoint();
            }
            avgStar = Math.round((sum / movie.starList().size()) * 10.0) / 10.0;
        }

//        해당 유저가 별점을 매겼는지
        StarResponse hasStar = starService.findStar("영화",movie.idx(), user.getUserIdx());

//        인물 리스트
        List<String> peopleList = new ArrayList<>();

        List<String> people = new ArrayList<>();
        List<PersonResponse> personList = new ArrayList<>();
        if(movie.people() != null){
            peopleList = List.of(movie.people().split(","));
            for(String per : peopleList){
                people.add(per.split("\\(")[0] + "," + per.split("\\(")[1].split("\\)")[0]);
            }
        }
        try{
            personList = personService.personList(people);
        }catch (Exception e){
            System.out.println("** 인물정보가 없습니다 **");
        }

        List<CommentResponse> commentList = movieService.commentList(movie.idx(),user);
//      해당 유저가 코멘트를 달았는지
        CommentResponse hasComm = null;
        for(CommentResponse comm: commentList){
            if(comm.user().getUserIdx() == user.getUserIdx()){
                hasComm = comm;
            }
        };

        boolean hasWish = wishService.findWish("영화",movie.idx(),user.getUserIdx());
        boolean hasWatch = watchService.findWatch("영화",movie.idx(),user.getUserIdx());
        boolean hasHate = hateService.findHate(user,"영화",movie.idx());

//        별점 그래프
        HashMap<Long, Integer> starGraph = new HashMap<Long,Integer>(){{
            put(1L,0);put(2L,0);put(3L,0);put(4L,0);put(5L,0);
        }};
        int graphPx = 88 / movie.starList().size();
        for(Star star : movie.starList()){
            for(Long i=0L; i<=5L; i++){
                if(star.getStarPoint() == i){
                    starGraph.put(i,starGraph.get(i) + graphPx);
                }
            }
        }
        Long bigStar = starGraph.entrySet().stream().max((m1, m2) -> m1.getValue() > m2.getValue() ? 1 : -1).get().getKey();

        System.out.println("코멘트 라이크여부: "+commentList.get(1).hasLike() + commentList.get(1).text());
        map.addAttribute("movie", movie);
        map.addAttribute("avg", avgStar);
        map.addAttribute("people", personList);
        map.addAttribute("comment", commentList);
        map.addAttribute("hasStar", hasStar);
        map.addAttribute("hasComm", hasComm);
        map.addAttribute("hasWish", hasWish);
        map.addAttribute("hasWatch", hasWatch);
        map.addAttribute("hasHate", hasHate);
        map.addAttribute("graph", starGraph);
        map.addAttribute("bigStar", bigStar);
        map.addAttribute("user", user);
        return "/movie/movieDetail";
    }

}
