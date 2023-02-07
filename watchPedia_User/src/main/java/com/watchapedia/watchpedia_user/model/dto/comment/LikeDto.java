package com.watchapedia.watchpedia_user.model.dto.comment;

import com.watchapedia.watchpedia_user.model.entity.comment.Comment;
import com.watchapedia.watchpedia_user.model.entity.comment.Like;
import com.watchapedia.watchpedia_user.model.entity.User;

public record LikeDto(
        Long idx,
        Long userIdx,
        Long commentIdx
) {
    public static LikeDto of(Long user, Long comment){
        return new LikeDto(null,user,comment);
    }
    public Like toEntity(){
        return Like.of(userIdx,commentIdx);
    }
}
