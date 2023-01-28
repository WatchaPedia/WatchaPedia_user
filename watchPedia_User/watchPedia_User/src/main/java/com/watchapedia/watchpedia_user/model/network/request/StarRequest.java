package com.watchapedia.watchpedia_user.model.network.request;

import com.watchapedia.watchpedia_user.model.dto.StarDto;
import com.watchapedia.watchpedia_user.model.entity.User;
import com.watchapedia.watchpedia_user.service.StarService;

public record StarRequest(
        String starContentType,
        Long starContentIdx,
        Long starUserIdx,
        Long starPoint
) {
    public static StarRequest of(
            String starContentType, Long starContentIdx, Long starUserIdx, Long starPoint
    ){
        return new StarRequest(starContentType, starContentIdx, starUserIdx, starPoint);
    }
    public static StarDto toDto(
            String starContentType, Long starContentIdx, User starUserIdx, Long starPoint
    ){
        return StarDto.of(
                starContentType, starContentIdx, starUserIdx, starPoint
        );
    }
}
