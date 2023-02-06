package com.watchapedia.watchpedia_user.controller.content;

import com.watchapedia.watchpedia_user.model.dto.content.TvDto;
import com.watchapedia.watchpedia_user.model.dto.content.WebtoonDto;
import com.watchapedia.watchpedia_user.model.entity.content.ajax.Star;
import com.watchapedia.watchpedia_user.model.network.response.PersonResponse;
import com.watchapedia.watchpedia_user.model.network.response.comment.CommentResponse;
import com.watchapedia.watchpedia_user.model.network.response.content.StarResponse;
import com.watchapedia.watchpedia_user.model.network.response.content.TvResponse;
import com.watchapedia.watchpedia_user.model.repository.UserRepository;
import com.watchapedia.watchpedia_user.model.repository.comment.CommentRepository;
import com.watchapedia.watchpedia_user.service.PersonService;
import com.watchapedia.watchpedia_user.service.comment.CommentService;
import com.watchapedia.watchpedia_user.service.content.TvService;
import com.watchapedia.watchpedia_user.service.content.ajax.HateService;
import com.watchapedia.watchpedia_user.service.content.ajax.StarService;
import com.watchapedia.watchpedia_user.service.content.ajax.WatchService;
import com.watchapedia.watchpedia_user.service.content.ajax.WishService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

@Controller
@RequestMapping("/tv")
@RequiredArgsConstructor
public class TvController {
    final StarService starService;
    final TvService tvService;
    final PersonService personService;
    private final CommentRepository commentRepository;
    private final UserRepository userRepository;
    private final WishService wishService;
    private final WatchService watchService;
    private final HateService hateService;

    private final CommentService commentService;

    @GetMapping(path="/main")
    public String tv(
            ModelMap map
    ){
        List<TvDto> tvs = tvService.tvs();
        map.addAttribute("tvs", tvs);
        return "/tv/tvMain";
    }


    @GetMapping("/{tvIdx}") // http://localhost:8080/movie/1
    public String tvDetail(
            @PathVariable Long tvIdx,
            @PageableDefault(size = 5, sort = "commIdx", direction = Sort.Direction.DESC) Pageable pageable,
            ModelMap map
    ){
        Long userIdx = 12L;

        TvResponse tv = tvService.tvView(tvIdx);

//      평균 별점
        double sum = 0;
        double avgStar = 0;
        if(tv.starList().size() == 1){
            avgStar = tv.starList().get(0).getStarPoint();
        }else if(tv.starList().size() > 0){
            for(int i=0; i<tv.starList().size(); i++){
                sum += tv.starList().get(i).getStarPoint();
            }
            avgStar = Math.round((sum / tv.starList().size()) * 10.0) / 10.0;
        }

//        해당 유저가 별점을 매겼는지
        StarResponse hasStar = starService.findStar("tv",tv.idx(), userIdx);

//        인물 리스트
        List<String> peopleList = new ArrayList<>();

        List<String> people = new ArrayList<>();
        List<PersonResponse> personList = new ArrayList<>();
        if(tv.people() != null){
            peopleList = List.of(tv.people().split(","));
            for(String per : peopleList){
                people.add(per.split("\\(")[0] + "," + per.split("\\(")[1].split("\\)")[0]);
            }
        }
        try{
            personList = personService.personList(people);
        }catch (Exception e){
            System.out.println("** 인물정보가 없습니다 **");
        }

        Page<CommentResponse> commentList = commentService.commentList("tv",tv.idx(),userIdx,pageable);
//      해당 유저가 코멘트를 달았는지
        CommentResponse hasComm = null;
        for(CommentResponse comm: commentList){
            if(comm.user().getUserIdx() == userIdx){
                hasComm = comm;
            }
        };

        boolean hasWish = wishService.findWish("tv",tv.idx(),userIdx);
        boolean hasWatch = watchService.findWatch("tv",tv.idx(),userIdx);
        boolean hasHate = hateService.findHate(userIdx,"tv",tv.idx());

//        별점 그래프
        HashMap<Long, Integer> starGraph = new HashMap<Long,Integer>(){{
            put(1L,0);put(2L,0);put(3L,0);put(4L,0);put(5L,0);
        }};
        if(tv.starList().size() > 0){
            int graphPx = 88 / tv.starList().size();
            for(Star star : tv.starList()){
                for(Long i=1L; i<=5L; i++){
                    if(star.getStarPoint() == i){
                        starGraph.put(i,starGraph.get(i) + graphPx);
                    }
                }
            }
        }
        Long bigStar = starGraph.entrySet().stream().max((m1, m2) -> m1.getValue() > m2.getValue() ? 1 : -1).get().getKey();

//        비슷한 장르 영화
        List<TvResponse> similarGenre = tvService.similarGenre(tv.genre(), tv.idx());

        map.addAttribute("tv", tv);
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
        return "/tv/tvDetail";
    }

    @GetMapping("/{tvIdx}/info")
    public String tvInfo(
            @PathVariable Long tvIdx,
            ModelMap map
    ){
        TvResponse tv = tvService.tvView(tvIdx);

        map.addAttribute("tv", tv);
        return "/tv/detailInfoTv";
    }

    @GetMapping("/{tvIdx}/gallery")
    public String tvGallery(
            @PathVariable Long tvIdx,
            ModelMap map
    ){
        Long userIdx = 12L;
        TvResponse tv = tvService.tvView(tvIdx);
        List<String> gallery = Arrays.stream(tv.gallery().split("[|]")).toList();
        String title = tv.title();

        map.addAttribute("gallery", gallery);
        map.addAttribute("title", title);
        map.addAttribute("userIdx", userIdx);
        return "/gallery";
    }

}
