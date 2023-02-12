package com.watchapedia.watchpedia_user.controller.content;

import com.watchapedia.watchpedia_user.model.dto.UserSessionDto;
import com.watchapedia.watchpedia_user.model.dto.comment.CommentDto;
import com.watchapedia.watchpedia_user.model.dto.content.MovieDto;
import com.watchapedia.watchpedia_user.model.dto.content.TvDto;
import com.watchapedia.watchpedia_user.model.entity.comment.Comment;
import com.watchapedia.watchpedia_user.model.entity.content.ajax.Star;
import com.watchapedia.watchpedia_user.model.network.response.*;
import com.watchapedia.watchpedia_user.model.network.response.comment.CommentResponse;
import com.watchapedia.watchpedia_user.model.network.response.content.MovieResponse;
import com.watchapedia.watchpedia_user.model.network.response.content.StarResponse;
import com.watchapedia.watchpedia_user.model.network.response.content.TvResponse;
import com.watchapedia.watchpedia_user.model.repository.comment.CommentRepository;
import com.watchapedia.watchpedia_user.model.repository.UserRepository;
import com.watchapedia.watchpedia_user.model.repository.comment.SpoilerRepository;
import com.watchapedia.watchpedia_user.service.*;
import com.watchapedia.watchpedia_user.service.comment.CommentService;
import com.watchapedia.watchpedia_user.service.content.TvService;
import com.watchapedia.watchpedia_user.service.content.ajax.HateService;
import com.watchapedia.watchpedia_user.service.content.ajax.StarService;
import com.watchapedia.watchpedia_user.service.content.ajax.WatchService;
import com.watchapedia.watchpedia_user.service.content.ajax.WishService;
import com.watchapedia.watchpedia_user.service.content.MovieService;
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
    private final SpoilerRepository spoilerRepository;

    @GetMapping(path="/main")
    public String tv(
            ModelMap map,HttpSession session
    ){
        UserSessionDto userSessionDto = (UserSessionDto) session.getAttribute("userSession");
        map.addAttribute("userSession", userSessionDto);
        List<TvDto> tvs = tvService.tvs();
        map.addAttribute("tvs", tvs);
        map.addAttribute("tvs1", tvService.searchCountry("한국"));
        map.addAttribute("tvs2", tvService.searchCountry("미국"));
        map.addAttribute("tvs3", tvService.searchTvDate("2022"));
        map.addAttribute("tvs4", tvService.searchChannel("Netflix"));
        map.addAttribute("tvs5", tvService.searchGenre("스릴러"));
        map.addAttribute("tvs6", tvService.searchGenre("애니메이션"));
        map.addAttribute("tvs7", tvService.searchTitle("시즌"));
        map.addAttribute("tvs8", tvService.searchCountry("일본"));

        Long totalCnt = starService.getTotalCnt();
        map.addAttribute("totalCnt",totalCnt);

        return "/tv/tvMain";
    }


    @GetMapping("/{tvIdx}") // http://localhost:8080/movie/1
    public String tvDetail(
            @PathVariable Long tvIdx,
            @PageableDefault(size = 5, sort = "commIdx", direction = Sort.Direction.DESC) Pageable pageable,
            ModelMap map,
            HttpSession session
    ) {
        UserSessionDto dto = (UserSessionDto) session.getAttribute("userSession");

        TvResponse tv = tvService.tvView(tvIdx);

//      평균 별점
        double sum = 0;
        double avgStar = 0;
        if (tv.starList().size() == 1) {
            avgStar = tv.starList().get(0).getStarPoint();
        } else if (tv.starList().size() > 0) {
            for (int i = 0; i < tv.starList().size(); i++) {
                sum += tv.starList().get(i).getStarPoint();
            }
            avgStar = Math.round((sum / tv.starList().size()) * 10.0) / 10.0;
        }

        StarResponse hasStar = null;
        if (dto != null) {
//        해당 유저가 별점을 매겼는지
            hasStar = starService.findStar("tv", tv.idx(), dto.userIdx());
        }

//        인물 리스트
        List<String> peopleList = new ArrayList<>();

        List<String> people = new ArrayList<>();
        List<PersonResponse> personList = new ArrayList<>();
        if (tv.people() != null) {
            peopleList = List.of(tv.people().split(","));
            for (String per : peopleList) {
                people.add(per.split("\\(")[0] + "," + per.split("\\(")[1].split("\\)")[0]);
            }
        }
        try {
            personList = personService.personList(people);
        } catch (Exception e) {
            System.out.println("** 인물정보가 없습니다 **");
        }

        CommentResponse hasComm = null;
        boolean hasWish = false;
        boolean hasWatch = false;
        boolean hasHate = false;
        Page<CommentResponse> commentList = commentService.commentList("tv", tv.idx(), dto != null ? dto.userIdx() : null, pageable);
        if (dto != null) {
            Comment comment = commentRepository.findByCommContentTypeAndCommContentIdxAndCommUserIdx(
                    "tv", tv.idx(), userRepository.getReferenceById(dto.userIdx())
            );
            if(comment != null){
                hasComm = CommentResponse.from(CommentDto.from(comment),spoilerRepository.findBySpoCommentIdx(comment.getCommIdx())!=null?true:false,
                        0,0L,false);
            }

            hasWish = wishService.findWish("tv", tv.idx(), dto.userIdx());
            hasWatch = watchService.findWatch("tv", tv.idx(), dto.userIdx());
            hasHate = hateService.findHate(dto.userIdx(), "tv", tv.idx());
        }
//        별점 그래프
        HashMap<Long, Integer> starGraph = new HashMap<Long, Integer>() {{
            put(1L, 0);
            put(2L, 0);
            put(3L, 0);
            put(4L, 0);
            put(5L, 0);
        }};
        if (tv.starList().size() > 0) {
            int graphPx = 88 / tv.starList().size();
            for (Star star : tv.starList()) {
                for (Long i = 1L; i <= 5L; i++) {
                    if (star.getStarPoint() == i) {
                        starGraph.put(i, starGraph.get(i) + graphPx);
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
        map.addAttribute("userSession", dto);
        map.addAttribute("similarGenre", similarGenre);

        Long totalCnt = starService.getTotalCnt();
        map.addAttribute("totalCnt",totalCnt);
        return "/tv/tvDetail";
    }
    @GetMapping("/{tvIdx}/info")
    public String tvInfo(
            @PathVariable Long tvIdx,
            ModelMap map,
            HttpSession session
    ){
        UserSessionDto dto = (UserSessionDto) session.getAttribute("userSession");
        TvResponse tv = tvService.tvView(tvIdx);

        map.addAttribute("tv", tv);
        map.addAttribute("userSession", dto);
        return "/tv/detailInfoTv";
    }

    @GetMapping("/{tvIdx}/gallery")
    public String tvGallery(
            @PathVariable Long tvIdx,
            ModelMap map,
            HttpSession session
    ){
        UserSessionDto dto = (UserSessionDto) session.getAttribute("userSession");
        TvResponse tv = tvService.tvView(tvIdx);
        List<String> gallery = Arrays.stream(tv.gallery().split("[|]")).toList();
        String title = tv.title();

        map.addAttribute("gallery", gallery);
        map.addAttribute("title", title);
        map.addAttribute("userSession", dto);
        return "/gallery";
    }


}
