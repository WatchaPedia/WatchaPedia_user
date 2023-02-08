package com.watchapedia.watchpedia_user.model.network.response.comment;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.watchapedia.watchpedia_user.model.dto.comment.CommentDto;
import com.watchapedia.watchpedia_user.model.entity.comment.Like;
import com.watchapedia.watchpedia_user.model.entity.User;
import org.springframework.data.domain.Page;

import java.time.LocalDateTime;
import java.util.List;

public record CommentResponse(
    Long idx,
    @JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})

    User user,
    String name,
    String text,
    String contentType,
    Long contentIdx,
    LocalDateTime regDate,
    boolean spoiler,
    int likeSum,
    Long recommSum,
    Page<RecommentResponse> recomment,
    boolean hasLike,
    boolean hasSpoiler,
    boolean hasInapp
) {
    public static CommentResponse from(
            CommentDto dto, boolean spoiler, List<Like> like, Page<RecommentResponse> recomment, boolean hasLike
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
                recomment.getTotalElements(),
                recomment,
                hasLike,
                hasSpoiler,
                hasInapp
        );
    }
    public static CommentResponse from(
            CommentDto dto, boolean spoiler, int likeSum, Long recommentSum, boolean hasLike,
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
            CommentDto dto, boolean spoiler, int likeSum, Long recommentSum, boolean hasLike
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
