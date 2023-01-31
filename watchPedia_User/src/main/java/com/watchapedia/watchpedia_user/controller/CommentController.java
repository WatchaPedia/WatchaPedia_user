package com.watchapedia.watchpedia_user.controller;

import com.watchapedia.watchpedia_user.model.dto.StarDto;
import com.watchapedia.watchpedia_user.model.entity.Star;
import com.watchapedia.watchpedia_user.model.network.request.CommentRequest;
import com.watchapedia.watchpedia_user.model.network.request.LikeRequest;
import com.watchapedia.watchpedia_user.model.network.response.StarResponse;
import com.watchapedia.watchpedia_user.model.repository.StarRepository;
import com.watchapedia.watchpedia_user.model.repository.UserRepository;
import com.watchapedia.watchpedia_user.service.CommentService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("/comment")
@RequiredArgsConstructor
public class CommentController {
    final CommentService commentService;
    private final StarRepository starRepository;
    private final UserRepository userRepository;

    @PostMapping("/save")
    @ResponseBody
    public CommentRequest commentSave(
            @RequestBody CommentRequest request
    ){
        commentService.commentSave(request);
        commentService.spoilerSave(request);
        return request;
    }

    @PostMapping("/delete")
    @ResponseBody
    public boolean commentDelete(
            @RequestBody CommentRequest request
    ){
        commentService.commentDelete(request);
        Star star = starRepository.findByStarContentTypeAndStarContentIdxAndStarUserIdx(
                request.contentType(),request.contentIdx(),userRepository.getReferenceById(request.userIdx())
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
}
