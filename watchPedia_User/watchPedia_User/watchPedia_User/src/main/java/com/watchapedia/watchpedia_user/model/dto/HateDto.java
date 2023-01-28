package com.watchapedia.watchpedia_user.model.dto;

import com.watchapedia.watchpedia_user.model.entity.Hate;
import com.watchapedia.watchpedia_user.model.entity.User;

public record HateDto(
        Long idx,
        User userIdx,
        String contentType,
        Long contentIdx
) {
    public static HateDto of(
            User userIdx, String contentType, Long contentIdx
    ){
        return new HateDto(null,userIdx,contentType,contentIdx);
    }

    public Hate toEntity(){
        return Hate.of(userIdx,contentType,contentIdx);
    }
}
