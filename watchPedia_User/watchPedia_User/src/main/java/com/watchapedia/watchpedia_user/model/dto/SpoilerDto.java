package com.watchapedia.watchpedia_user.model.dto;

import com.watchapedia.watchpedia_user.model.entity.Comment;
import com.watchapedia.watchpedia_user.model.entity.Spoiler;

public record SpoilerDto(
        Long idx,
        Comment commentIdx
) {
    public static SpoilerDto of(Comment commentIdx){
        return new SpoilerDto(null, commentIdx);
    }

    public Spoiler toEntity(){
        return Spoiler.of(commentIdx);
    }
}
