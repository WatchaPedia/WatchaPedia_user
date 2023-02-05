package com.watchapedia.watchpedia_user.controller.comment;

import com.watchapedia.watchpedia_user.model.network.request.comment.RecommentRequest;
import com.watchapedia.watchpedia_user.model.network.request.comment.RelikeRequest;
import com.watchapedia.watchpedia_user.model.network.response.comment.RecommentResponse;
import com.watchapedia.watchpedia_user.service.comment.RecommentService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/comment")
@RequiredArgsConstructor
public class RecommentController {

    final RecommentService recommentService;
    @PostMapping("/recomment/save")
    @ResponseBody
    public RecommentResponse recommentSave(
            @RequestBody RecommentRequest request
    ){
        RecommentResponse recomment = recommentService.recommentSave(request);
        return recomment;
    }

    @PostMapping("/recomment/like/save")
    @ResponseBody
    public boolean recommentLikeSave(
            @RequestBody RelikeRequest request
    ){
        return recommentService.recommentLikeSave(request);
    }

    @PostMapping("/recomment/edit")
    @ResponseBody
    public RecommentRequest recommentEdit(
            @RequestBody RecommentRequest request
    ){
        recommentService.recommentEdit(request);
        return request;
    }

    @PostMapping("/recomment/delete")
    @ResponseBody
    public Long recommentDelete(
            @RequestBody RecommentRequest request
    ){
        recommentService.recommentDelete(request.idx());
        return request.idx();
    }
}
