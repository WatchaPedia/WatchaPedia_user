package com.watchapedia.watchpedia_user.model.dto;

import com.watchapedia.watchpedia_user.model.entity.Comment;
import com.watchapedia.watchpedia_user.model.entity.Like;
import com.watchapedia.watchpedia_user.model.entity.User;

public record LikeDto(
        Long idx,
        User userIdx,
        Comment commentIdx
) {
    public static LikeDto of(User user, Comment comment){
        return new LikeDto(null,user,comment);
    }
    public Like toEntity(){
        return Like.of(userIdx,commentIdx);
    }
}
