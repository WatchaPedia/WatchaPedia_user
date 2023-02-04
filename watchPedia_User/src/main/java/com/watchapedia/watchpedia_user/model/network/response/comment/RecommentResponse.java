package com.watchapedia.watchpedia_user.model.network.response.comment;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.watchapedia.watchpedia_user.model.entity.comment.Relike;
import com.watchapedia.watchpedia_user.model.entity.User;

import java.time.LocalDateTime;
import java.util.List;

public record RecommentResponse(
        Long idx,
        Long commIdx,
        Long userIdx,
        String name,
        String text,
        @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
        LocalDateTime regDate,
        List<Relike>like,
        boolean hasLike,
        boolean hasReport
) {
    public static RecommentResponse of(
            Long idx, Long commIdx, Long userIdx, String name, String text, LocalDateTime regDate,List<Relike> like, boolean hasLike,boolean hasReport
    ){
        return new RecommentResponse(
                idx,commIdx,userIdx,name,text,regDate,like, hasLike, hasReport
        );
    }
}
