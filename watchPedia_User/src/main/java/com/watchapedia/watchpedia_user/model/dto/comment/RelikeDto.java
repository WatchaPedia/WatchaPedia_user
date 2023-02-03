package com.watchapedia.watchpedia_user.model.dto.comment;

import com.watchapedia.watchpedia_user.model.entity.comment.Recomment;
import com.watchapedia.watchpedia_user.model.entity.comment.Relike;
import com.watchapedia.watchpedia_user.model.entity.User;

public record RelikeDto(
        Long idx,
        Long recommIdx,
        Long userIdx
) {
    public static RelikeDto of(Long recomment, Long user){
        return new RelikeDto(null, recomment,user);
    }
    public Relike toEntity(){
        return Relike.of(recommIdx,userIdx);
    }
}
