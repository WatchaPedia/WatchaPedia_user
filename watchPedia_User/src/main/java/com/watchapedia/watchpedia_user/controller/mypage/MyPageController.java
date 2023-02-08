package com.watchapedia.watchpedia_user.controller.mypage;

import com.watchapedia.watchpedia_user.model.dto.UserDto;
import com.watchapedia.watchpedia_user.model.dto.UserSessionDto;
import com.watchapedia.watchpedia_user.model.entity.User;
import com.watchapedia.watchpedia_user.model.entity.comment.Comment;
import com.watchapedia.watchpedia_user.model.entity.content.ajax.Star;
import com.watchapedia.watchpedia_user.model.entity.content.ajax.Wish;
import com.watchapedia.watchpedia_user.model.network.request.comment.CommentRequest;
import com.watchapedia.watchpedia_user.model.network.request.comment.LikeRequest;
import com.watchapedia.watchpedia_user.model.network.response.comment.CommentResponse;
import com.watchapedia.watchpedia_user.model.network.response.content.BookResponse;
import com.watchapedia.watchpedia_user.model.network.response.content.MovieResponse;
import com.watchapedia.watchpedia_user.model.network.response.content.TvResponse;
import com.watchapedia.watchpedia_user.model.network.response.content.WebtoonResponse;
import com.watchapedia.watchpedia_user.model.repository.UserRepository;
import com.watchapedia.watchpedia_user.model.repository.content.ajax.StarRepository;
import com.watchapedia.watchpedia_user.model.repository.content.ajax.WishRepository;
import com.watchapedia.watchpedia_user.service.comment.CommentService;
import com.watchapedia.watchpedia_user.service.content.BookService;
import com.watchapedia.watchpedia_user.service.content.MovieService;
import com.watchapedia.watchpedia_user.service.content.TvService;
import com.watchapedia.watchpedia_user.service.content.WebtoonService;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
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
    private final UserRepository userRepository;


    @GetMapping(path="/analysis")  // localhost:9090/mypage/analysis
    public ModelAndView analysis(){
        return new ModelAndView("/mypage/analysis") ;
    }


    @GetMapping(path="/user/{userIdx}")  // localhost:9090/mypage/myPage
    public ModelAndView myPage(HttpSession session,@PathVariable Long userIdx){
        UserSessionDto userSessionDto = (UserSessionDto)session.getAttribute("userSession");

        User user = userRepository.findAllByUserIdx(userIdx);

//      컨텐츠별 평가한 갯수
        List<Star> starList = starRepository.findAllByStarUserIdx(userIdx).stream().toList();

        Long stMovieCnt=0L;
        Long stBookCnt=0L;
        Long stTvCnt=0L;
        Long stWebtoonCnt=0L;
        for(Star star : starList){
            if(star.getStarContentType().equals("movie")){
                stMovieCnt+=1L;
            }else if(star.getStarContentType().equals("book")){
                stBookCnt+=1L;
            }else if(star.getStarContentType().equals("tv")){
                stTvCnt+=1L;
            }else {
                stWebtoonCnt+=1L;
            }
        }
        // 컨텐츠별 보고싶어요 갯수
        List<Wish> wishList = wishRepository.findAllByWishUserIdx(userIdx).stream().toList();
        Long movieCnt=0L;
        Long bookCnt=0L;
        Long tvCnt=0L;
        Long webtoonCnt=0L;
        for(Wish wish : wishList){
            if(wish.getWishContentType().equals("movie")){
                movieCnt+=1L;
            }else if(wish.getWishContentType().equals("book")){
                bookCnt+=1L;
            }else if(wish.getWishContentType().equals("tv")){
                tvCnt+=1L;
            }else {
                webtoonCnt+=1L;
            }
        }


        return new ModelAndView("/mypage/myPage")
                .addObject("user",user)
                .addObject("stMovieCnt",stMovieCnt)
                .addObject("stBookCnt",stBookCnt)
                .addObject("stTvCnt",stTvCnt)
                .addObject("stWebtoonCnt",stWebtoonCnt)
                .addObject("movieCnt",movieCnt)
                .addObject("bookCnt",bookCnt)
                .addObject("tvCnt",tvCnt)
                .addObject("webtoonCnt",webtoonCnt)
                .addObject("userSession",userSessionDto);


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
    private final WishRepository wishRepository;

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
