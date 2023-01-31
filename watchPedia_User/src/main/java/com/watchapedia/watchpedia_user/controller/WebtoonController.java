package com.watchapedia.watchpedia_user.controller;

import com.watchapedia.watchpedia_user.model.dto.TvDto;
import com.watchapedia.watchpedia_user.model.dto.WebtoonDto;
import com.watchapedia.watchpedia_user.model.entity.Star;
import com.watchapedia.watchpedia_user.model.entity.Tv;
import com.watchapedia.watchpedia_user.model.entity.User;
import com.watchapedia.watchpedia_user.model.entity.Webtoon;
import com.watchapedia.watchpedia_user.model.network.request.SelectType;
import com.watchapedia.watchpedia_user.model.network.request.StarRequest;
import com.watchapedia.watchpedia_user.model.network.response.*;
import com.watchapedia.watchpedia_user.model.repository.CommentRepository;
import com.watchapedia.watchpedia_user.model.repository.UserRepository;
import com.watchapedia.watchpedia_user.model.repository.WebtoonRepository;
import com.watchapedia.watchpedia_user.service.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("")
@RequiredArgsConstructor
public class WebtoonController {
    final StarService starService;

    @GetMapping(path="/webestimate") // http://localhost:9090/estimate
    public String webestimate(
            SelectType selectType,
            ModelMap map
    ){
        Long userIdx = 1L;
//        리스트 인덱스 삭제 문제 때문에 new ArrayList로 감쌈
        List<WebtoonResponse> webList = new ArrayList<>(starService.webList().stream().map(WebtoonResponse::from).toList());
//        평점을 남겼던 영화라면 인덱스 삭제
        int j = 0;
        for(int i=0; i<webList.size(); i++){
            WebtoonResponse web = webList.get(j);
            if(web.starList().size() > 0){
                for(Star star : web.starList()){
                    System.out.println("별점 : " + star.getStarPoint());
                    if(star.getStarUserIdx().getUserIdx() == userIdx){
                        webList.remove(j);
                        j--;
                        break;
                    }
                }
            }
            j++;
        }
        map.addAttribute("content", webList);
        return "/valueContent";
    }

    @PostMapping("/webestimate") // http://localhost:9090/estimate
    @ResponseBody
    public StarAndCommentResponse webstarSave(
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

    final WebtoonService webtoonService;
    final PersonService personService;
    private final CommentRepository commentRepository;
    private final UserRepository userRepository;
    private final WishService wishService;
    private final WatchService watchService;
    private final HateService hateService;
    private final WebtoonRepository webtoonRepository;

    @GetMapping(path="/webtoon/main")
    public String webtoon(ModelMap map){
        map.addAttribute("webtoons", webtoonService.searchWebtoons());
        return "/webtoon/webtoonMain";
    }

    @GetMapping("/webtoon/{webIdx}") // http://localhost:9090/tv/1
    public String webDetail(
            @PathVariable Long webIdx,
            ModelMap map
    ){
        User user = userRepository.getReferenceById(3L);

        WebtoonResponse webtoon = webtoonService.webtoonView(webIdx);

//      평균 별점
        double sum = 0;
        double avgStar = 0;
        if(webtoon.starList().size() == 1) {
            avgStar = webtoon.starList().get(0).getStarPoint();
        }else if(webtoon.starList().size()>0){
            for(int i=0; i<webtoon.starList().size(); i++){
                sum += webtoon.starList().get(i).getStarPoint();
            }
            avgStar = Math.round((sum / webtoon.starList().size()) * 10.0) / 10.0;
        }

//        해당 유저가 별점을 매겼는지
        StarResponse hasStar = starService.findStar("webtoon",webtoon.webIdx(), user.getUserIdx());

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

        List<CommentResponse> commentList = webtoonService.commentList(webtoon.webIdx(),user);
//      해당 유저가 코멘트를 달았는지
        CommentResponse hasComm = null;
        for(CommentResponse comm: commentList){
            if(comm.user().getUserIdx() == user.getUserIdx()){
                hasComm = comm;
            }
        };

        boolean hasWish = wishService.findWish("webtoon",webtoon.webIdx(),user.getUserIdx());
        boolean hasWatch = watchService.findWatch("webtoon",webtoon.webIdx(),user.getUserIdx());
        boolean hasHate = hateService.findHate(user,"webtoon",webtoon.webIdx());

//        별점 그래프
        HashMap<Long, Integer> starGraph = new HashMap<Long,Integer>(){{
            put(1L,0);put(2L,0);put(3L,0);put(4L,0);put(5L,0);
        }};
        if(webtoon.starList().size() > 0){
            int graphPx = 88 / webtoon.starList().size();
            for(Star star : webtoon.starList()){
                for(Long i=0L; i<=5L; i++){
                    if(star.getStarPoint() == i){
                        starGraph.put(i,starGraph.get(i) + graphPx);
                    }
                }
            }
        }
        Long bigStar = starGraph.entrySet().stream().max((m1, m2) -> m1.getValue() > m2.getValue() ? 1 : -1).get().getKey();

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
        map.addAttribute("user", user);
        return "/webtoon/webtoonDetail";
    }

    @GetMapping(path="/webtoon/{webIdx}/webview")
    public String allTv(@PathVariable Long webIdx, ModelMap map){
        Optional<Webtoon> webtoon = webtoonRepository.findById(webIdx);
        WebtoonResponse webtoonResponse = WebtoonResponse.from(WebtoonDto.from(webtoon.get()));
        map.addAttribute("webtoon", webtoonResponse);
        map.addAttribute("webtoonss", webtoonService.searchWebtoons());
        return "/webtoon/detailInfoWebtoon";
    }

}
