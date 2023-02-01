package com.watchapedia.watchpedia_user.model.network.response.comment;

import com.watchapedia.watchpedia_user.model.dto.comment.CommentDto;
import com.watchapedia.watchpedia_user.model.entity.comment.Like;
import com.watchapedia.watchpedia_user.model.entity.User;

import java.time.LocalDateTime;
import java.util.List;

public record CommentResponse(
    Long idx,
    User user,
    String name,
    String text,
    String contentType,
    Long contentIdx,
    LocalDateTime regDate,
    boolean spoiler,
    int likeSum,
    int recommSum,
    List<RecommentResponse> recomment,
    boolean hasLike,
    boolean hasSpoiler,
    boolean hasInapp
) {
    public static CommentResponse from(
            CommentDto dto, boolean spoiler, List<Like> like, List<RecommentResponse> recomment, boolean hasLike
            ,boolean hasSpoiler, boolean hasInapp
    ){
        return new CommentResponse(
                dto.idx(),
                dto.userIdx(),
                dto.name(),
                dto.text(),
                dto.contentType(),
                dto.contentIdx(),
                dto.regDate(),
                spoiler,
                like.size(),
                recomment.size(),
                recomment,
                hasLike,
                hasSpoiler,
                hasInapp
        );
    }
    public static CommentResponse from(
            CommentDto dto, boolean spoiler, int likeSum, int recommentSum, boolean hasLike,
            boolean hasSpoiler, boolean hasInapp
    ){
        return new CommentResponse(
                dto.idx(),
                dto.userIdx(),
                dto.name(),
                dto.text(),
                dto.contentType(),
                dto.contentIdx(),
                dto.regDate(),
                spoiler,
                likeSum,
                recommentSum,
                null,
                hasLike,
                hasSpoiler,
                hasInapp
        );
    }

    public static CommentResponse from(
            CommentDto dto, boolean spoiler, int likeSum, int recommentSum, boolean hasLike
    ){
        return new CommentResponse(
                dto.idx(),
                dto.userIdx(),
                dto.name(),
                dto.text(),
                dto.contentType(),
                dto.contentIdx(),
                dto.regDate(),
                spoiler,
                likeSum,
                recommentSum,
                null,
                hasLike,
                false,
                false
        );
    }
}
