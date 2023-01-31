package com.watchapedia.watchpedia_user.model.network.response;

import com.watchapedia.watchpedia_user.model.dto.CommentDto;
import com.watchapedia.watchpedia_user.model.entity.Like;
import com.watchapedia.watchpedia_user.model.entity.Recomment;
import com.watchapedia.watchpedia_user.model.entity.User;

import java.util.List;

public record CommentResponse(
    Long idx,
    User user,
    String name,
    String text,
    boolean spoiler,
    List<Like> like,
    List<Recomment> recomment,
    boolean hasLike
) {
    public static CommentResponse from(
            CommentDto dto, boolean spoiler, List<Like> like, List<Recomment> recomment, boolean hasLike
    ){
        return new CommentResponse(
                dto.idx(),
                dto.userIdx(),
                dto.name(),
                dto.text(),
                spoiler,
                like,
                recomment,
                hasLike
        );
    }
}
