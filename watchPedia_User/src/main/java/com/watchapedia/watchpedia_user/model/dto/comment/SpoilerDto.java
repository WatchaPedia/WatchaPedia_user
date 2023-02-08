package com.watchapedia.watchpedia_user.model.dto.comment;

import com.watchapedia.watchpedia_user.model.entity.comment.Comment;
import com.watchapedia.watchpedia_user.model.entity.comment.Spoiler;

public record SpoilerDto(
        Long idx,
        Long commentIdx
) {
    public static SpoilerDto of(Long commentIdx){
        return new SpoilerDto(null, commentIdx);
    }

    public Spoiler toEntity(){
        return Spoiler.of(commentIdx);
    }
}
