package com.watchapedia.watchpedia_user.controller.content;


import com.watchapedia.watchpedia_user.model.dto.UserSessionDto;
import com.watchapedia.watchpedia_user.model.dto.comment.CommentDto;
import com.watchapedia.watchpedia_user.model.dto.content.WebtoonDto;
import com.watchapedia.watchpedia_user.model.entity.comment.Comment;
import com.watchapedia.watchpedia_user.model.entity.content.ajax.Star;
import com.watchapedia.watchpedia_user.model.network.response.PersonResponse;
import com.watchapedia.watchpedia_user.model.network.response.comment.CommentResponse;
import com.watchapedia.watchpedia_user.model.network.response.content.StarResponse;
import com.watchapedia.watchpedia_user.model.network.response.content.WebtoonResponse;
import com.watchapedia.watchpedia_user.model.repository.UserRepository;
import com.watchapedia.watchpedia_user.model.repository.comment.CommentRepository;
import com.watchapedia.watchpedia_user.model.repository.comment.SpoilerRepository;
import com.watchapedia.watchpedia_user.service.PersonService;
import com.watchapedia.watchpedia_user.service.comment.CommentService;
import com.watchapedia.watchpedia_user.service.content.WebtoonService;
import com.watchapedia.watchpedia_user.service.content.ajax.HateService;
import com.watchapedia.watchpedia_user.service.content.ajax.StarService;
import com.watchapedia.watchpedia_user.service.content.ajax.WatchService;
import com.watchapedia.watchpedia_user.service.content.ajax.WishService;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

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
    private final CommentService commentService;
    private final SpoilerRepository spoilerRepository;

    @GetMapping(path="/main")
    public String webtoon(
            ModelMap map, HttpSession session
    ){
        UserSessionDto userSessionDto = (UserSessionDto) session.getAttribute("userSession");
        map.addAttribute("userSession", userSessionDto);
        List<WebtoonDto> webtoons = webtoonService.webtoons();
        map.addAttribute("webtoons", webtoons);

        Long totalCnt = starService.getTotalCnt();
        map.addAttribute("totalCnt",totalCnt);
        return "/webtoon/webtoonMain";
    }

    @GetMapping("/{webIdx}") // http://localhost:8080/movie/1
    public String webtoonDetail(
            @PathVariable Long webIdx,
            @PageableDefault(size = 5, sort = "commIdx", direction = Sort.Direction.DESC) Pageable pageable,
            ModelMap map,
            HttpSession session
    ){
        UserSessionDto dto = (UserSessionDto) session.getAttribute("userSession");

        WebtoonResponse webtoon = webtoonService.webtoonView(webIdx);

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
        StarResponse hasStar = null;
        if (dto != null) {
//        해당 유저가 별점을 매겼는지
            hasStar = starService.findStar("webtoon", webtoon.idx(), dto.userIdx());
        }

//        인물 리스트
        List<String> peopleList = new ArrayList<>();

        List<String> people = new ArrayList<>();
        List<PersonResponse> personList = new ArrayList<>();
        if(webtoon.people() != null){
            peopleList = List.of(webtoon.people().split(","));
            for(String per : peopleList){
                people.add(per.split("\\(")[0] + "," + per.split("\\(")[1].split("\\)")[0]);
            }
        }
        try{
            personList = personService.personList(people);
        }catch (Exception e){
            System.out.println("** 인물정보가 없습니다 **");
        }

        CommentResponse hasComm = null;
        boolean hasWish = false;
        boolean hasWatch = false;
        boolean hasHate = false;
        Page<CommentResponse> commentList = commentService.commentList("webtoon", webtoon.idx(), dto != null ? dto.userIdx() : null, pageable);
        if (dto != null) {
            Comment comment = commentRepository.findByCommContentTypeAndCommContentIdxAndCommUserIdx(
                    "webtoon", webtoon.idx(), userRepository.getReferenceById(dto.userIdx())
            );
            if(comment != null){
                hasComm = CommentResponse.from(CommentDto.from(comment),spoilerRepository.findBySpoCommentIdx(comment.getCommIdx())!=null?true:false,
                        0,0L,false);
            }

            hasWish = wishService.findWish("webtoon", webtoon.idx(), dto.userIdx());
            hasWatch = watchService.findWatch("webtoon", webtoon.idx(), dto.userIdx());
            hasHate = hateService.findHate(dto.userIdx(), "webtoon", webtoon.idx());
        }
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

//        비슷한 장르 영화
        List<WebtoonResponse> similarGenre = webtoonService.similarGenre(webtoon.genre(), webtoon.idx());

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
        map.addAttribute("userSession", dto);
        map.addAttribute("similarGenre", similarGenre);

        Long totalCnt = starService.getTotalCnt();
        map.addAttribute("totalCnt",totalCnt);
        return "/webtoon/webtoonDetail";
    }


    @GetMapping("/{webIdx}/info")
    public String webtoonInfo(
            @PathVariable Long webIdx,
            ModelMap map,
            HttpSession session
    ){
        UserSessionDto dto = (UserSessionDto) session.getAttribute("userSession");
        WebtoonResponse webtoon = webtoonService.webtoonView(webIdx);

        map.addAttribute("webtoon", webtoon);
        map.addAttribute("userSession", dto);
        return "/webtoon/detailInfoWebtoon";
    }



}
