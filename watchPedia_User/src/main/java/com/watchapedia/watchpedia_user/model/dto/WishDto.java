package com.watchapedia.watchpedia_user.model.dto;

import com.watchapedia.watchpedia_user.model.entity.User;
import com.watchapedia.watchpedia_user.model.entity.Wish;

public record WishDto(
        Long idx,
        String contentType,
        Long contentIdx,
        User userIdx
) {
    public static WishDto of(
            String contentType, Long contentIdx, User userIdx
    ){
        return new WishDto(null, contentType,contentIdx,userIdx);
    }

    public Wish toEntity(){
        return Wish.of(contentType,contentIdx,userIdx);
    }
}
