package com.watchapedia.watchpedia_user.controller;

import com.watchapedia.watchpedia_user.model.dto.TvDto;
import com.watchapedia.watchpedia_user.model.entity.Star;
import com.watchapedia.watchpedia_user.model.entity.Tv;
import com.watchapedia.watchpedia_user.model.entity.User;
import com.watchapedia.watchpedia_user.model.network.request.SelectType;
import com.watchapedia.watchpedia_user.model.network.request.StarRequest;
import com.watchapedia.watchpedia_user.model.network.response.*;
import com.watchapedia.watchpedia_user.model.repository.CommentRepository;
import com.watchapedia.watchpedia_user.model.repository.TvRepository;
import com.watchapedia.watchpedia_user.model.repository.UserRepository;
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
public class TvController {
    final StarService starService;

    @GetMapping(path="/tvestimate") // http://localhost:9090/estimate
    public String tvestimate(
            SelectType selectType,
            ModelMap map
    ){
        Long userIdx = 8L;
//        리스트 인덱스 삭제 문제 때문에 new ArrayList로 감쌈
        List<TvResponse> tvList = new ArrayList<>(starService.tvList().stream().map(TvResponse::from).toList());
//        평점을 남겼던 영화라면 인덱스 삭제
        int j = 0;
        for(int i=0; i<tvList.size(); i++){
            TvResponse tv = tvList.get(j);
            if(tv.starList().size() > 0){
                for(Star star : tv.starList()){
                    System.out.println("별점 : " + star.getStarPoint());
                    if(star.getStarUserIdx().getUserIdx() == userIdx){
                        tvList.remove(j);
                        j--;
                        break;
                    }
                }
            }
            j++;
        }
        map.addAttribute("content", tvList);
        return "/tvvalueContent";
    }

    @PostMapping("/tvestimate") // http://localhost:9090/estimate
    @ResponseBody
    public StarAndCommentResponse tvstarSave(
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

    final TvService tvService;
    final TvRepository tvRepository;
    final PersonService personService;
    private final CommentRepository commentRepository;
    private final UserRepository userRepository;
    private final WishService wishService;
    private final WatchService watchService;
    private final HateService hateService;

    @GetMapping(path="/tv/main")
    public String tv(ModelMap map){
        map.addAttribute("tvs", tvService.searchTvs());
        return "/tv/tvMain";
    }

    @GetMapping("/tv/{tvIdx}") // http://localhost:9090/tv/1
    public String tvDetail(
            @PathVariable Long tvIdx,
            ModelMap map
    ){
        User user = userRepository.getReferenceById(3L);

        TvResponse tv = tvService.tvView(tvIdx);

//      평균 별점
        double sum = 0;
        double avgStar = 0;
        if(tv.starList().size() == 1) {
            avgStar = tv.starList().get(0).getStarPoint();
        }else if(tv.starList().size()>0){
            for(int i=0; i<tv.starList().size(); i++){
                sum += tv.starList().get(i).getStarPoint();
            }
            avgStar = Math.round((sum / tv.starList().size()) * 10.0) / 10.0;
        }

//        해당 유저가 별점을 매겼는지
        StarResponse hasStar = starService.findStar("tv",tv.tvIdx(), user.getUserIdx());

//        인물 리스트
        List<String> peopleList = new ArrayList<>();

        List<String> people = new ArrayList<>();
        List<PersonResponse> personList = new ArrayList<>();
        if(tv.tvPeople() != null){
            peopleList = List.of(tv.tvPeople().split(","));
            for(String per : peopleList){
                people.add(per.split("\\(")[0] + "," + per.split("\\(")[1].split("\\)")[0]);
            }
        }
        try{
            personList = personService.personList(people);
        }catch (Exception e){
            System.out.println("** 인물정보가 없습니다 **");
        }

        List<CommentResponse> commentList = tvService.commentList(tv.tvIdx(),user);
//      해당 유저가 코멘트를 달았는지
        CommentResponse hasComm = null;
        for(CommentResponse comm: commentList){
            if(comm.user().getUserIdx() == user.getUserIdx()){
                hasComm = comm;
            }
        };

        boolean hasWish = wishService.findWish("tv",tv.tvIdx(),user.getUserIdx());
        boolean hasWatch = watchService.findWatch("tv",tv.tvIdx(),user.getUserIdx());
        boolean hasHate = hateService.findHate(user,"tv",tv.tvIdx());

//        별점 그래프
        HashMap<Long, Integer> starGraph = new HashMap<Long,Integer>(){{
            put(1L,0);put(2L,0);put(3L,0);put(4L,0);put(5L,0);
        }};
        if(tv.starList().size() > 0){
            int graphPx = 88 / tv.starList().size();
            for(Star star : tv.starList()){
                for(Long i=0L; i<=5L; i++){
                    if(star.getStarPoint() == i){
                        starGraph.put(i,starGraph.get(i) + graphPx);
                    }
                }
            }
        }
        Long bigStar = starGraph.entrySet().stream().max((m1, m2) -> m1.getValue() > m2.getValue() ? 1 : -1).get().getKey();
        map.addAttribute("tv", tv);
        map.addAttribute("avg", avgStar);
        map.addAttribute("people", personList);
        map.addAttribute("comment", commentList);
        System.out.println(commentList  + "@@@@@@@@@@@@@@@asdasdasd");
        map.addAttribute("hasStar", hasStar);
        map.addAttribute("hasComm", hasComm);
        map.addAttribute("hasWish", hasWish);
        map.addAttribute("hasWatch", hasWatch);
        map.addAttribute("hasHate", hasHate);
        map.addAttribute("graph", starGraph);
        map.addAttribute("bigStar", bigStar);
        map.addAttribute("user", user);
        return "/tv/tvDetail";
    }

    @GetMapping(path="/tv/{tvIdx}/tvview")
    public String allTv(@PathVariable Long tvIdx, ModelMap map){
        Optional<Tv> tv = tvRepository.findById(tvIdx);
        TvResponse tvResponse = TvResponse.from(TvDto.from(tv.get()));
        map.addAttribute("tv", tvResponse);
        map.addAttribute("tvs", tvService.searchTvs());
        return "/tv/detailInfoTv";
    }

}
