package com.watchapedia.watchpedia_user.model.dto.comment;

import com.watchapedia.watchpedia_user.model.entity.comment.Comment;
import com.watchapedia.watchpedia_user.model.entity.comment.Recomment;
import com.watchapedia.watchpedia_user.model.entity.User;

import java.time.LocalDateTime;

public record RecommentDto(
        Long idx,
        Long commIdx,
        User userIdx,
        String name,
        String text,
        LocalDateTime regDate
) {
    public static RecommentDto of(
            Long idx, Long comment, User user, String name, String text, LocalDateTime regDate
    ){
        return new RecommentDto(
                idx,comment,user,name,text,regDate
        );
    }
    public static RecommentDto of(
            Long comment, User user, String name, String text
    ){
        return new RecommentDto(
                null,comment,user,name,text,null
        );
    }
    public Recomment toEntity(){
        return Recomment.of(commIdx,userIdx,name,text);
    }

    public static RecommentDto from(Recomment entity){
        return RecommentDto.of(
                entity.getRecommIdx(),
                entity.getRecommCommIdx(),
                entity.getRecommUserIdx(),
                entity.getRecommName(),
                entity.getRecommText(),
                entity.getRecommRegDate()
        );
    }
}
