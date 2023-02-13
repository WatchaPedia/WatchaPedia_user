package com.watchapedia.watchpedia_user.controller;

import com.watchapedia.watchpedia_user.model.dto.UserSessionDto;
import com.watchapedia.watchpedia_user.model.entity.User;
import com.watchapedia.watchpedia_user.model.network.request.UserRequestDto;
import com.watchapedia.watchpedia_user.model.network.response.content.EstimateContent;
import com.watchapedia.watchpedia_user.model.repository.comment.CommentRepository;
import com.watchapedia.watchpedia_user.model.repository.content.MovieRepository;
import com.watchapedia.watchpedia_user.model.repository.content.WebtoonRepository;
import com.watchapedia.watchpedia_user.model.repository.content.ajax.StarRepository;
import com.watchapedia.watchpedia_user.model.repository.UserRepository;
import com.watchapedia.watchpedia_user.model.repository.content.ajax.WatchRepository;
import com.watchapedia.watchpedia_user.model.repository.content.ajax.WishRepository;
import com.watchapedia.watchpedia_user.service.content.ajax.StarService;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.*;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.*;

@Controller
@RequestMapping("")
@RequiredArgsConstructor
public class EstimateController {
    final StarRepository starRepository;
    final StarService starService;
    private final MovieRepository movieRepository;
    final CommentRepository commentRepository;
    final UserRepository userRepository;
    private final WebtoonRepository webtoonRepository;
    private final WishRepository wishRepository;
    private final WatchRepository watchRepository;

    @GetMapping("/estimate") // http://localhost:8080/estimate
    public ModelAndView estimateMain(
            HttpSession session
    ){
        UserSessionDto dto = (UserSessionDto) session.getAttribute("userSession");
//        User user = userRepository.getReferenceById(userIdx);
//        List<EstimateContent> contentList = contentList = starService.movieList(user,pageable);

//        // 리스트를 페이지형으로 바꾸기
//        PageRequest pageRequest = PageRequest.of(page, 9);
//        int start = (int) pageRequest.getOffset();
//        int end = Math.min((start + pageRequest.getPageSize()), contentList.size());
//        Page<EstimateContent> newList = new PageImpl<>(contentList.subList(start, end), pageRequest, contentList.size());
        if(dto!=null){
            return new ModelAndView("/valueContent")
                    .addObject("userSession",dto)
                    .addObject("movieStar",starRepository.findByStarContentTypeAndStarUserIdx("movie",dto.userIdx()).size())
                    .addObject("tvStar",starRepository.findByStarContentTypeAndStarUserIdx("tv",dto.userIdx()).size())
                    .addObject("bookStar",starRepository.findByStarContentTypeAndStarUserIdx("book",dto.userIdx()).size())
                    .addObject("webStar",starRepository.findByStarContentTypeAndStarUserIdx("webtoon",dto.userIdx()).size());
        }
        return new ModelAndView("/user/login");
    }

    @GetMapping("/estimate/{contentType}") // http://localhost:8080/estimate/page
    @ResponseBody
    public Map<String, Object> estimatePage(
            @PathVariable String contentType,
            @PageableDefault(size = 7) Pageable pageable,
            HttpSession session
    ){
        UserSessionDto dto = (UserSessionDto) session.getAttribute("userSession");
        User user = userRepository.findById(dto.userIdx()).get();

        Map<String,Object> contentMap = new HashMap<>();
        List<EstimateContent> contentList = new ArrayList<>();
        if(contentType == null || contentType.contains("movie")) contentMap = starService.movieList(user,pageable);
        else if(contentType.contains("tv")) contentMap = starService.tvList(user,pageable);
        else if(contentType.contains("book")) contentMap = starService.bookList(user,pageable);
        else if(contentType.contains("webtoon")) contentMap = starService.webList(user,pageable);

        Map<String, Object> mv = new HashMap<>();

        mv.put("content", contentMap.get("contentList"));
        mv.put("last", contentMap.get("last"));
        return mv;
    }

}
