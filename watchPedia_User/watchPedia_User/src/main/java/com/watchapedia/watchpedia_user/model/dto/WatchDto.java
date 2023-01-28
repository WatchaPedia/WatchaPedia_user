package com.watchapedia.watchpedia_user.model.dto;

import com.watchapedia.watchpedia_user.model.entity.User;
import com.watchapedia.watchpedia_user.model.entity.Watch;

public record WatchDto(
        Long idx,
        User user,
        String contentType,
        Long contentIdx
) {
    public static WatchDto of(
            User user, String contentType, Long contentIdx
    ){
        return new WatchDto(null,user,contentType,contentIdx);
    }
    public Watch toEntity(){
        return Watch.of(user,contentType,contentIdx);
    }
}
