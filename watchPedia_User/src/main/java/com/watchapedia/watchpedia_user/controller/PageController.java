package com.watchapedia.watchpedia_user.controller;

import com.watchapedia.watchpedia_user.model.dto.UserSessionDto;
import com.watchapedia.watchpedia_user.model.dto.comment.CommentDto;
import com.watchapedia.watchpedia_user.model.entity.content.ajax.Star;
import com.watchapedia.watchpedia_user.model.network.request.ajax.StarRequest;
import com.watchapedia.watchpedia_user.model.network.response.PersonResponse;
import com.watchapedia.watchpedia_user.model.network.response.comment.CommentResponse;
import com.watchapedia.watchpedia_user.model.network.response.content.MovieResponse;
import com.watchapedia.watchpedia_user.model.network.response.content.StarAndCommentResponse;
import com.watchapedia.watchpedia_user.model.network.response.content.StarResponse;
import com.watchapedia.watchpedia_user.model.repository.comment.CommentRepository;
import com.watchapedia.watchpedia_user.model.repository.UserRepository;
import com.watchapedia.watchpedia_user.model.repository.content.MovieRepository;
import com.watchapedia.watchpedia_user.service.comment.CommentService;
import com.watchapedia.watchpedia_user.service.content.MovieService;
import com.watchapedia.watchpedia_user.service.content.ajax.StarService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("")
@RequiredArgsConstructor
public class PageController {
    final StarService starService;

    final MovieService movieService;
    private final CommentRepository commentRepository;
    private final UserRepository userRepository;

    @GetMapping(path="/")
    public String movie(ModelMap map, HttpSession session, HttpServletRequest request){
        UserSessionDto userSessionDto = (UserSessionDto) session.getAttribute("userSession");

        map.addAttribute("userSession", userSessionDto);
        map.addAttribute("movies", movieService.searchMovies());
        return "movie/movieMain";
    }

    //     별점 저장
    @PostMapping("/estimate") // http://localhost:8080/estimate
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

    private final CommentService commentService;
    private final MovieRepository movieRepository;

    @GetMapping("/{contentType}/{contentIdx}/comments") // http://localhost:8080/movie/1/comments
    public String commentList(
            @PathVariable String contentType,
            @PathVariable Long contentIdx,
            @PageableDefault(size = 3, sort = "commIdx", direction = Sort.Direction.DESC) Pageable pageable,
            ModelMap map
    ){
        Long userIdx = 12L;
        Page<CommentResponse> commentList = commentService.commentList(contentType,contentIdx,userIdx, pageable);
        String contentTitle = "";

        switch (contentType){
            case "movie" ->{
                contentTitle = movieRepository.findById(contentIdx).get().getMovTitle();
            }
//            case "tv" -> {
//
//            }
//            case "webtoon" -> {
//
//            }
//            case "book" -> {
//
//            }
        }
        map.addAttribute("userIdx", userIdx);
        map.addAttribute("commentList", commentList);
        map.addAttribute("contentTitle", contentTitle);
        return "/comments";
    }

    @GetMapping("/{contentType}/{contentIdx}/comments/new") // http://localhost:8080/movie/1/comments
    @ResponseBody
    public Map<String, Object> commentList(
            @PathVariable String contentType,
            @PathVariable Long contentIdx,
            @PageableDefault(size = 3, sort = "commIdx", direction = Sort.Direction.DESC) Pageable pageable
    ){
        Long userIdx = 12L;
        Page<CommentResponse> commentList = commentService.commentList(contentType,contentIdx,userIdx, pageable);

        Map<String, Object> mv = new HashMap<>();
        mv.put("commentList", commentList);
        return mv;
    }

    @GetMapping("/{contentType}/{contentIdx}/new") // http://localhost:8080/movie/1/new
    @ResponseBody
    public Map<String, Object> movieDetail(
            @PathVariable String contentType,
            @PathVariable Long contentIdx,
            @PageableDefault(size = 5, sort = "commIdx", direction = Sort.Direction.DESC) Pageable pageable
    ){
        Long userIdx = 12L;

        Page<CommentResponse> commentList = commentService.commentList(contentType,contentIdx,userIdx,pageable);

        Map<String, Object> mv = new HashMap<>();
        mv.put("commentList", commentList);
        return mv;
    }



}
