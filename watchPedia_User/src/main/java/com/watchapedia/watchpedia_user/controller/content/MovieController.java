package com.watchapedia.watchpedia_user.controller.content;

import com.watchapedia.watchpedia_user.model.entity.content.ajax.Star;
import com.watchapedia.watchpedia_user.model.entity.User;
import com.watchapedia.watchpedia_user.model.network.response.*;
import com.watchapedia.watchpedia_user.model.network.response.comment.CommentResponse;
import com.watchapedia.watchpedia_user.model.network.response.content.MovieResponse;
import com.watchapedia.watchpedia_user.model.network.response.content.StarResponse;
import com.watchapedia.watchpedia_user.model.repository.comment.CommentRepository;
import com.watchapedia.watchpedia_user.model.repository.UserRepository;
import com.watchapedia.watchpedia_user.service.*;
import com.watchapedia.watchpedia_user.service.comment.CommentService;
import com.watchapedia.watchpedia_user.service.content.ajax.HateService;
import com.watchapedia.watchpedia_user.service.content.ajax.StarService;
import com.watchapedia.watchpedia_user.service.content.ajax.WatchService;
import com.watchapedia.watchpedia_user.service.content.ajax.WishService;
import com.watchapedia.watchpedia_user.service.content.MovieService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

@Controller
@RequestMapping("/movie")
@RequiredArgsConstructor
public class MovieController {
    final StarService starService;
    final MovieService movieService;
    final PersonService personService;
    private final CommentRepository commentRepository;
    private final UserRepository userRepository;
    private final WishService wishService;
    private final WatchService watchService;
    private final HateService hateService;

    private final CommentService commentService;

    @GetMapping(path="/main")
    public String movie(ModelMap map){
        map.addAttribute("movies", movieService.searchMovies());
        return "movie/movieMain";
    }


    @GetMapping("/{movieIdx}") // http://localhost:8080/movie/1
    public String movieDetail(
            @PathVariable Long movieIdx,
            @PageableDefault(size = 5, sort = "commIdx", direction = Sort.Direction.DESC) Pageable pageable,
            ModelMap map
    ){
        Long userIdx = 12L;

        MovieResponse movie = movieService.movieView(movieIdx);

//      평균 별점
        double sum = 0;
        double avgStar = 0;
        if(movie.starList().size() == 1){
            avgStar = movie.starList().get(0).getStarPoint();
        }else if(movie.starList().size() > 0){
            for(int i=0; i<movie.starList().size(); i++){
                sum += movie.starList().get(i).getStarPoint();
            }
            avgStar = Math.round((sum / movie.starList().size()) * 10.0) / 10.0;
        }

//        해당 유저가 별점을 매겼는지
        StarResponse hasStar = starService.findStar("movie",movie.idx(), userIdx);

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

        Page<CommentResponse> commentList = commentService.commentList("movie",movie.idx(),userIdx,pageable);
//      해당 유저가 코멘트를 달았는지
        CommentResponse hasComm = null;
        for(CommentResponse comm: commentList){
            if(comm.user().getUserIdx() == userIdx){
                hasComm = comm;
            }
        };

        boolean hasWish = wishService.findWish("movie",movie.idx(),userIdx);
        boolean hasWatch = watchService.findWatch("movie",movie.idx(),userIdx);
        boolean hasHate = hateService.findHate(userIdx,"movie",movie.idx());

//        별점 그래프
        HashMap<Long, Integer> starGraph = new HashMap<Long,Integer>(){{
            put(1L,0);put(2L,0);put(3L,0);put(4L,0);put(5L,0);
        }};
        if(movie.starList().size() > 0){
            int graphPx = 88 / movie.starList().size();
            for(Star star : movie.starList()){
                for(Long i=1L; i<=5L; i++){
                    if(star.getStarPoint() == i){
                        starGraph.put(i,starGraph.get(i) + graphPx);
                    }
                }
            }
        }
        Long bigStar = starGraph.entrySet().stream().max((m1, m2) -> m1.getValue() > m2.getValue() ? 1 : -1).get().getKey();

//        비슷한 장르 영화
        List<MovieResponse> similarGenre = movieService.similarGenre(movie.genre(), movie.idx());

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
        map.addAttribute("userIdx", userIdx);
        map.addAttribute("similarGenre", similarGenre);
        return "/movie/movieDetail";
    }

    @GetMapping("/{movieIdx}/info")
    public String movieInfo(
            @PathVariable Long movieIdx,
            ModelMap map
    ){
        MovieResponse movie = movieService.movieView(movieIdx);

        map.addAttribute("movie", movie);
        return "/movie/detailInfo";
    }

    @GetMapping("/{movieIdx}/gallery")
    public String movieGallery(
            @PathVariable Long movieIdx,
            ModelMap map
    ){
        Long userIdx = 12L;
        MovieResponse movie = movieService.movieView(movieIdx);
        List<String> gallery = Arrays.stream(movie.gallery().split("[|]")).toList();
        String title = movie.title();

        map.addAttribute("gallery", gallery);
        map.addAttribute("title", title);
        map.addAttribute("userIdx", userIdx);
        return "/gallery";
    }

}
