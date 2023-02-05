package com.watchapedia.watchpedia_user.model.dto.comment;

import com.watchapedia.watchpedia_user.model.entity.comment.Comment;
import com.watchapedia.watchpedia_user.model.entity.User;

import java.time.LocalDateTime;

public record CommentDto(
        Long idx,
        User userIdx,
        String name,
        String text,
        String contentType,
        Long contentIdx,
        LocalDateTime regDate
) {
    public static CommentDto from(Comment entity){
        return new CommentDto(
                entity.getCommIdx(),
                entity.getCommUserIdx(),
                entity.getCommName(),
                entity.getCommText(),
                entity.getCommContentType(),
                entity.getCommContentIdx(),
                entity.getCommRegDate()
        );
    }

    public static CommentDto of(
            User userIdx, String text, String contentType, Long contentIdx
    ){
        return new CommentDto(null,userIdx,userIdx.getUserName(),text,contentType,contentIdx,null);
    }

    public Comment toEntity(){
        return Comment.of(userIdx,name,text,contentType,contentIdx);
    }
}
