package com.watchapedia.watchpedia_user.model.dto;

import com.watchapedia.watchpedia_user.model.entity.Star;
import com.watchapedia.watchpedia_user.model.entity.User;
import org.springframework.beans.factory.annotation.Autowired;

public record StarDto(
        Long starIdx,
        String starContentType,
        Long starContentIdx,
        User starUserIdx,
        Long starPoint
) {
    public static StarDto of(
            Long starIdx, String starContentType, Long starContentIdx, User starUserIdx, Long starPoint
    ){
        return new StarDto(starIdx,starContentType,starContentIdx,starUserIdx,starPoint);
    }

    public static StarDto of(
            String starContentType, Long starContentIdx, User starUserIdx, Long starPoint
    ){
        return new StarDto(null,starContentType,starContentIdx,starUserIdx,starPoint);
    }

    public static StarDto from(Star entity){
        return new StarDto(
                entity.getStarIdx(),
                entity.getStarContentType(),
                entity.getStarContentIdx(),
                entity.getStarUserIdx(),
                entity.getStarPoint()
        );
    }

    public Star toEntity(){
        return Star.of(
                starContentType,starContentIdx,starUserIdx,starPoint
        );
    }
}
