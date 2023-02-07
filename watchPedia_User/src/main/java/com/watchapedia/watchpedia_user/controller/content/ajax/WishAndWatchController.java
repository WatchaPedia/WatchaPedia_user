package com.watchapedia.watchpedia_user.controller.content.ajax;

import com.watchapedia.watchpedia_user.model.network.request.ajax.WatchRequest;
import com.watchapedia.watchpedia_user.model.network.request.ajax.WishRequest;
import com.watchapedia.watchpedia_user.service.content.ajax.WatchService;
import com.watchapedia.watchpedia_user.service.content.ajax.WishService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("")
@RequiredArgsConstructor
public class WishAndWatchController {
    final WishService wishService;
    final WatchService watchService;
    @PostMapping("/wish/save")
    @ResponseBody
    public boolean wishSave(
        @RequestBody WishRequest request
    ){
        boolean result = wishService.wishSave(request);
        return result;
    }

    @PostMapping("/watch/save")
    @ResponseBody
    public boolean watchSave(
            @RequestBody WatchRequest request
    ){
        boolean result = watchService.watchSave(request);
        return result;
    }
}
