package com.watchapedia.watchpedia_user.model.network.response;

import com.watchapedia.watchpedia_user.model.dto.StarDto;
import com.watchapedia.watchpedia_user.model.entity.Star;

public record StarResponse(
        Long starPoint
) {
    public static StarResponse of(
            Long starPoint
    ){
        return new StarResponse(starPoint);
    }

    public static StarResponse from(StarDto dto){
        return new StarResponse(dto.starPoint());
    }
    public static StarResponse from(Star entity){
        return new StarResponse(entity.getStarPoint());
    }
}
