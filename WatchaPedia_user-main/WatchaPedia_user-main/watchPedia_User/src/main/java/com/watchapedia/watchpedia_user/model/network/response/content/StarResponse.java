package com.watchapedia.watchpedia_user.model.network.response.content;

import com.watchapedia.watchpedia_user.model.dto.content.ajax.StarDto;
import com.watchapedia.watchpedia_user.model.entity.content.ajax.Star;

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
