package com.watchapedia.watchpedia_user.model.dto.content.ajax;

import com.watchapedia.watchpedia_user.model.entity.User;
import com.watchapedia.watchpedia_user.model.entity.content.ajax.Wish;

public record WishDto(
        Long idx,
        String contentType,
        Long contentIdx,
        Long userIdx
) {
    public static WishDto of(
            String contentType, Long contentIdx, Long userIdx
    ){
        return new WishDto(null, contentType,contentIdx,userIdx);
    }

    public Wish toEntity(){
        return Wish.of(contentType,contentIdx,userIdx);
    }
}
