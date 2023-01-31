package com.watchapedia.watchpedia_user.model.network.request;

import com.watchapedia.watchpedia_user.model.dto.CommentDto;
import com.watchapedia.watchpedia_user.model.entity.User;

public record CommentRequest(
        Long userIdx,
        String text,
        String contentType,
        Long contentIdx,
        boolean spoiler
) {
    public static CommentDto toDto(
            User userIdx, String text, String contentType, Long contentIdx
    ){
        return CommentDto.of(userIdx,text,contentType,contentIdx);
    }
}
