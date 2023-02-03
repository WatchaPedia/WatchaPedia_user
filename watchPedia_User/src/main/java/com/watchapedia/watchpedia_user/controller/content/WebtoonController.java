package com.watchapedia.watchpedia_user.controller.content;


import com.watchapedia.watchpedia_user.model.entity.content.Webtoon;
import com.watchapedia.watchpedia_user.model.entity.content.ajax.Star;
import com.watchapedia.watchpedia_user.model.network.response.PersonResponse;
import com.watchapedia.watchpedia_user.model.network.response.comment.CommentResponse;
import com.watchapedia.watchpedia_user.model.network.response.content.StarResponse;
import com.watchapedia.watchpedia_user.model.network.response.content.TvResponse;
import com.watchapedia.watchpedia_user.model.network.response.content.WebtoonResponse;
import com.watchapedia.watchpedia_user.model.repository.UserRepository;
import com.watchapedia.watchpedia_user.model.repository.comment.CommentRepository;
import com.watchapedia.watchpedia_user.service.PersonService;
import com.watchapedia.watchpedia_user.service.content.WebtoonService;
import com.watchapedia.watchpedia_user.service.content.ajax.HateService;
import com.watchapedia.watchpedia_user.service.content.ajax.StarService;
import com.watchapedia.watchpedia_user.service.content.ajax.WatchService;
import com.watchapedia.watchpedia_user.service.content.ajax.WishService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@Controller
@RequestMapping("/webtoon")
@RequiredArgsConstructor
public class WebtoonController {
    final StarService starService;
    final WebtoonService webtoonService;
    final PersonService personService;
    private final CommentRepository commentRepository;
    private final UserRepository userRepository;
    private final WishService wishService;
    private final WatchService watchService;
    private final HateService hateService;


    @GetMapping(path="/main")
    public String webtoon(ModelMap map){
        map.addAttribute("webtoons", webtoonService.searchWebtoons());
        return "/webtoon/webtoonMain";
    }

    @GetMapping("/{webIdx}") // http://localhost:8080/movie/1
    public String webtoonDetail(
            @PathVariable Long webIdx,
            ModelMap map
    ){
        Long userIdx = 12L;

        WebtoonResponse webtoon = webtoonService.webtoonView(webIdx);
        List<Webtoon> webtoonG = webtoonService.Genre(webIdx);
        webtoonG.remove(0);

//      평균 별점
        double sum = 0;
        double avgStar = 0;
        if(webtoon.starList().size() == 1){
            avgStar = webtoon.starList().get(0).getStarPoint();
        }else if(webtoon.starList().size() > 0){
            for(int i=0; i<webtoon.starList().size(); i++){
                sum += webtoon.starList().get(i).getStarPoint();
            }
            avgStar = Math.round((sum / webtoon.starList().size()) * 10.0) / 10.0;
        }

//        해당 유저가 별점을 매겼는지
        StarResponse hasStar = starService.findStar("webtoon",webtoon.webIdx(), userIdx);

//        인물 리스트
        List<String> peopleList = new ArrayList<>();

        List<String> people = new ArrayList<>();
        List<PersonResponse> personList = new ArrayList<>();
        if(webtoon.webPeople() != null){
            peopleList = List.of(webtoon.webPeople().split(","));
            for(String per : peopleList){
                people.add(per.split("\\(")[0] + "," + per.split("\\(")[1].split("\\)")[0]);
            }
        }
        try{
            personList = personService.personList(people);
        }catch (Exception e){
            System.out.println("** 인물정보가 없습니다 **");
        }

        List<CommentResponse> commentList = webtoonService.commentList(webtoon.webIdx(),userIdx);
//      해당 유저가 코멘트를 달았는지
        CommentResponse hasComm = null;
        for(CommentResponse comm: commentList){
            if(comm.user().getUserIdx() == userIdx){
                hasComm = comm;
            }
        };

        boolean hasWish = wishService.findWish("webtoon",webtoon.webIdx(),userIdx);
        boolean hasWatch = watchService.findWatch("webtoon",webtoon.webIdx(),userIdx);
        boolean hasHate = hateService.findHate(userIdx,"webtoon",webtoon.webIdx());

//        별점 그래프
        HashMap<Long, Integer> starGraph = new HashMap<Long,Integer>(){{
            put(1L,0);put(2L,0);put(3L,0);put(4L,0);put(5L,0);
        }};
        if(webtoon.starList().size() > 0){
            int graphPx = 88 / webtoon.starList().size();
            for(Star star : webtoon.starList()){
                for(Long i=1L; i<=5L; i++){
                    if(star.getStarPoint() == i){
                        starGraph.put(i,starGraph.get(i) + graphPx);
                    }
                }
            }
        }
        Long bigStar = starGraph.entrySet().stream().max((m1, m2) -> m1.getValue() > m2.getValue() ? 1 : -1).get().getKey();


        map.addAttribute("webtoonG", webtoonG);
        map.addAttribute("webtoon", webtoon);
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
        map.addAttribute("webtoons", webtoonService.searchWebtoons());
        return "/webtoon/webtoonDetail";
    }

    @GetMapping("/{webIdx}/webtoonview")
    public String webtoonInfo(
            @PathVariable Long webIdx,
            ModelMap map
    ){
        WebtoonResponse webtoon = webtoonService.webtoonView(webIdx);

        map.addAttribute("webtoon", webtoon);
        return "/webtoon/detailInfoWebtoon";
    }



}
