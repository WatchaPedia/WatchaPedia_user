package com.watchapedia.watchpedia_user.controller;

import com.watchapedia.watchpedia_user.model.entity.content.ajax.Star;
import com.watchapedia.watchpedia_user.model.network.response.content.MovieResponse;
import com.watchapedia.watchpedia_user.model.repository.comment.CommentRepository;
import com.watchapedia.watchpedia_user.model.repository.content.MovieRepository;
import com.watchapedia.watchpedia_user.model.repository.content.ajax.StarRepository;
import com.watchapedia.watchpedia_user.model.repository.UserRepository;
import com.watchapedia.watchpedia_user.service.content.ajax.StarService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.*;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("")
@RequiredArgsConstructor
public class EstimateController {
    final StarRepository starRepository;
    final StarService starService;
    private final MovieRepository movieRepository;
    final CommentRepository commentRepository;
    final UserRepository userRepository;

    @GetMapping("/estimate") // http://localhost:8080/estimate
    public String estimate(
            ModelMap map,
            Pageable pageable
    ){
        int page = pageable.getPageNumber();
        Long userIdx = 12L;
        List<MovieResponse> movieList = starService.movieList(userIdx);

        // 리스트를 페이지형으로 바꾸기
        PageRequest pageRequest = PageRequest.of(page, 9);
        int start = (int) pageRequest.getOffset();
        int end = Math.min((start + pageRequest.getPageSize()), movieList.size());
        Page<MovieResponse> newList = new PageImpl<>(movieList.subList(start, end), pageRequest, movieList.size());

        System.out.println("정보출력");
        System.out.println(newList.getTotalElements());
        System.out.println(newList.getTotalPages());
        map.addAttribute("content", newList);
        return "/valueContent";
    }

}
