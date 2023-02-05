package com.watchapedia.watchpedia_user.model.dto.content.ajax;

import com.watchapedia.watchpedia_user.model.entity.User;
import com.watchapedia.watchpedia_user.model.entity.content.ajax.Watch;

public record WatchDto(
        Long idx,
        Long user,
        String contentType,
        Long contentIdx
) {
    public static WatchDto of(
            Long user, String contentType, Long contentIdx
    ){
        return new WatchDto(null,user,contentType,contentIdx);
    }
    public Watch toEntity(){
        return Watch.of(user,contentType,contentIdx);
    }
}
