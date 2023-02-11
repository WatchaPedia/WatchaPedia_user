package com.watchapedia.watchpedia_user.controller.mypage;

import com.watchapedia.watchpedia_user.model.dto.UserSessionDto;
import com.watchapedia.watchpedia_user.model.entity.comment.Comment;
import com.watchapedia.watchpedia_user.model.entity.content.ajax.Star;
import com.watchapedia.watchpedia_user.model.network.request.comment.CommentRequest;
import com.watchapedia.watchpedia_user.model.network.request.comment.LikeRequest;
import com.watchapedia.watchpedia_user.model.network.response.NoticeResponse;
import com.watchapedia.watchpedia_user.model.network.response.UserResponse;
import com.watchapedia.watchpedia_user.model.network.response.comment.CommentResponse;
import com.watchapedia.watchpedia_user.model.network.response.content.BookResponse;
import com.watchapedia.watchpedia_user.model.network.response.content.MovieResponse;
import com.watchapedia.watchpedia_user.model.network.response.content.TvResponse;
import com.watchapedia.watchpedia_user.model.network.response.content.WebtoonResponse;
import com.watchapedia.watchpedia_user.model.repository.UserRepository;
import com.watchapedia.watchpedia_user.model.repository.content.ajax.StarRepository;
import com.watchapedia.watchpedia_user.service.AnalysisService;
import com.watchapedia.watchpedia_user.service.UserService;
import com.watchapedia.watchpedia_user.service.comment.CommentService;
import com.watchapedia.watchpedia_user.service.content.BookService;
import com.watchapedia.watchpedia_user.service.content.MovieService;
import com.watchapedia.watchpedia_user.service.content.TvService;
import com.watchapedia.watchpedia_user.service.content.WebtoonService;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.HashMap;
import java.util.List;
import java.util.Map;


@Controller
@RequestMapping("")
@RequiredArgsConstructor
public class MyPageController {
    final CommentService commentService;
    private final StarRepository starRepository;
    final AnalysisService analysisService;

    @GetMapping(path="/user/{userIdx}/analysis")  // localhost:9090/mypage/analysis
    public ModelAndView analysis(
            @PathVariable Long userIdx
    ){
        Map<String, Integer> starSum = analysisService.starSum(userIdx);
        Double starAvg = starRepository.findByStarAvg(userIdx);
        //        별점 그래프
        double graphPx = 88.0 / starRepository.findByStarCount(userIdx);
        HashMap<Integer, Double> starGraph = new HashMap<Integer,Double>(){{
            put(1,starRepository.findByStarPointCount(userIdx,1L)!=0.0?starRepository.findByStarPointCount(userIdx,1L)*graphPx:1.0);
            put(2,starRepository.findByStarPointCount(userIdx,2L)!=0.0?starRepository.findByStarPointCount(userIdx,2L)*graphPx:1.0);
            put(3,starRepository.findByStarPointCount(userIdx,3L)!=0.0?starRepository.findByStarPointCount(userIdx,3L)*graphPx:1.0);
            put(4,starRepository.findByStarPointCount(userIdx,4L)!=0.0?starRepository.findByStarPointCount(userIdx,4L)*graphPx:1.0);
            put(5,starRepository.findByStarPointCount(userIdx,5L)!=0.0?starRepository.findByStarPointCount(userIdx,5L)*graphPx:1.0);
        }};

        return new ModelAndView("/mypage/analysis")
                .addObject("userName",userRepository.getReferenceById(userIdx).getUserName())
                .addObject("starSum",starSum)
                .addObject("starCnt",starRepository.findByStarCount(userIdx))
                .addObject("starMax",starRepository.findByStarMax(userIdx))
                .addObject("starGraph",starGraph)
                .addObject("starAvg",Math.round(starAvg * 10) / 10.0);
    }


    final UserService userService;
    @GetMapping(path="/user/{userIdx}")  // localhost:9090/user/1
    public ModelAndView myPage(HttpSession session,@PathVariable Long userIdx){
        UserSessionDto userSessionDto = (UserSessionDto)session.getAttribute("userSession");
        UserResponse user = userService.myPageUser(userIdx);
        List<NoticeResponse> notice = userService.noticeAll();
        return new ModelAndView("/mypage/myPage")
                .addObject("userSession",userSessionDto)
                .addObject("notice",notice)
                .addObject("user", user);
    }

    @GetMapping("/user/{userIdx}/{contentType}")
    @ResponseBody
    public ModelAndView myPageMovieList(
            HttpSession session, @PathVariable Long userIdx
    ){
        UserSessionDto dto = (UserSessionDto) session.getAttribute("userSession");
        return new ModelAndView("/mypage/valuedBoxTest")
                .addObject("userSession",dto)
                .addObject("userName",userRepository.getReferenceById(userIdx).getUserName());
    }

    @GetMapping("/user/{userIdx}/{contentType}/ratings")
    public ModelAndView ratingsAllPage(
            HttpSession session, @PathVariable Long userIdx
    ){
        UserSessionDto dto = (UserSessionDto) session.getAttribute("userSession");
        return new ModelAndView("/mypage/valuedPlus")
                .addObject("userSession",dto)
                .addObject("userName",userRepository.getReferenceById(userIdx).getUserName());
    }
    @GetMapping("/user/{userIdx}/{contentType}/ratings/list")
    @ResponseBody
    public Map<String, Object> ratingsAll(
            @PathVariable Long userIdx, @PathVariable String contentType,
            @PageableDefault(size=10, sort="starIdx", direction = Sort.Direction.DESC) Pageable pageable
    ){
        Map<String, Object> mv = userService.findRatings(contentType,userIdx,pageable);
        return  mv;
    }
    @GetMapping("/user/{userIdx}/{contentType}/ratings/starpointList")
    @ResponseBody
    public Map<String, Object> ratingsStarPointAll(
            @PathVariable Long userIdx, @PathVariable String contentType,
            @PageableDefault(size=10, sort="starIdx", direction = Sort.Direction.DESC) Pageable pageable
    ){
        Map<String, Object> mv = userService.findRatingsStarPointAll(contentType,userIdx,pageable);
        return  mv;
    }

    @GetMapping("/user/{userIdx}/{contentType}/ratings/{starPoint}")
    @ResponseBody
    public ModelAndView ratingsStarPointPage(
            HttpSession session, @PathVariable Long userIdx
    ){
        UserSessionDto dto = (UserSessionDto) session.getAttribute("userSession");
        return new ModelAndView("/mypage/selectStarList")
                .addObject("userSession",dto)
                .addObject("userName",userRepository.getReferenceById(userIdx).getUserName());
    }
    @GetMapping("/user/{userIdx}/{contentType}/ratings/{starPoint}/new")
    @ResponseBody
    public Map<String, Object> ratingsStarPoint(
            @PathVariable Long userIdx, @PathVariable String contentType, @PathVariable Long starPoint,
            @PageableDefault(size=9, sort="starIdx", direction = Sort.Direction.DESC) Pageable pageable
    ){
        Map<String, Object> mv = userService.findRatingsStarPoint(contentType,userIdx,starPoint,pageable);
        return  mv;
    }

    @GetMapping("/user/{userIdx}/{contentType}/{type}")
    @ResponseBody
    public ModelAndView wishList(
            HttpSession session, @PathVariable Long userIdx
    ){
        UserSessionDto dto = (UserSessionDto) session.getAttribute("userSession");
        return new ModelAndView("/mypage/wishWatch")
                .addObject("userSession",dto)
                .addObject("userName",userRepository.getReferenceById(userIdx).getUserName());
    }

    @GetMapping("/user/{userIdx}/{contentType}/{type}/new")
    @ResponseBody
    public Map<String, Object> ratingsStarPoint(
            @PathVariable Long userIdx, @PathVariable String contentType, @PathVariable String type,
            Pageable pageable
    ){
        if(type.equals("wish")){
            pageable = PageRequest.of(pageable.getPageNumber(),9,Sort.by("wishIdx").descending());
        }else{
            pageable = PageRequest.of(pageable.getPageNumber(),9,Sort.by("watchIdx").descending());
        }
        Map<String, Object> mv = userService.findWishWatch(contentType,userIdx,type,pageable);
        return  mv;
    }


    @PostMapping("/save")
    @ResponseBody
    public Long commentSave(
            @RequestBody CommentRequest request
    ){
        Comment comment = commentService.commentSave(request);
        commentService.spoilerSave(comment, request.spoiler());
        return comment.getCommIdx();
    }

    @PostMapping("/delete")
    @ResponseBody
    public boolean commentDelete(
            @RequestBody CommentRequest request
    ){
        commentService.commentDelete(request);
        Star star = starRepository.findByStarContentTypeAndStarContentIdxAndStarUserIdx(
                request.contentType(),request.contentIdx(),request.userIdx()
        );
        if(star != null){
            return true;
        }else {
            return false;
        }
    }

    @PostMapping("/like/save")
    @ResponseBody
    public boolean likeSave(
            @RequestBody LikeRequest request
    ){
        return commentService.likeSave(request);
    }
    final TvService tvService;
    final WebtoonService webtoonService;
    final MovieService movieService;
    final BookService bookService;
    private final UserRepository userRepository;

    @GetMapping("/{commentIdx}")
    public String commentView(
            @PathVariable Long commentIdx,
            @PageableDefault(size = 9, sort = "recommIdx", direction = Sort.Direction.ASC) Pageable pageable,
            ModelMap map,
            HttpSession session
    ){
        UserSessionDto dto = (UserSessionDto) session.getAttribute("userSession");
        CommentResponse comment = commentService.findComment(commentIdx, dto!=null?dto.userIdx():null, pageable);
        switch (comment.contentType()){
            case "movie" -> {
                MovieResponse content =movieService.movieView(comment.contentIdx());
                map.addAttribute("content", content);
            }
            case "tv" -> {
                TvResponse content = tvService.tvView(comment.contentIdx());
                map.addAttribute("content", content);
            }

            case "webtoon" -> {
                WebtoonResponse content = webtoonService.webtoonView(comment.contentIdx());
                map.addAttribute("content", content);
            }
            case "book" -> {
                BookResponse content = bookService.bookView(comment.contentIdx());
                map.addAttribute("content", content);
            }
        }
        map.addAttribute("comment", comment);
        map.addAttribute("userSession", dto);
        map.addAttribute("userIdx", dto!=null?dto.userIdx():null);

        return "/recomment";
    }

    @GetMapping("/{commentIdx}/new")
    @ResponseBody
    public Map<String, Object> commentView(
            @PathVariable Long commentIdx,
            @PageableDefault(size = 9, sort = "recommIdx", direction = Sort.Direction.ASC) Pageable pageable,
            HttpSession session
    ){
        UserSessionDto dto = (UserSessionDto) session.getAttribute("userSession");
        CommentResponse comment = commentService.findComment(commentIdx, dto!=null? dto.userIdx():null, pageable);
        Map<String, Object> mv = new HashMap<>();

        mv.put("comment", comment.recomment());
        return mv;
    }
}
