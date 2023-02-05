package com.watchapedia.watchpedia_user.model.dto.content.ajax;

import com.watchapedia.watchpedia_user.model.entity.content.ajax.Hate;
import com.watchapedia.watchpedia_user.model.entity.User;

public record HateDto(
        Long idx,
        Long userIdx,
        String contentType,
        Long contentIdx
) {
    public static HateDto of(
            Long userIdx, String contentType, Long contentIdx
    ){
        return new HateDto(null,userIdx,contentType,contentIdx);
    }

    public Hate toEntity(){
        return Hate.of(userIdx,contentType,contentIdx);
    }
}
