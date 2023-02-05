package com.watchapedia.watchpedia_user.controller.content.ajax;

import com.watchapedia.watchpedia_user.model.network.request.ajax.HateRequest;
import com.watchapedia.watchpedia_user.model.repository.UserRepository;
import com.watchapedia.watchpedia_user.service.content.ajax.HateService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("")
@RequiredArgsConstructor
public class HateController {

    final HateService hateService;
    private final UserRepository userRepository;

    @PostMapping("/hate/save")
    @ResponseBody
    public boolean hateSave(
            @RequestBody HateRequest request
    ){
        return hateService.hateSave(request);
    }

}
