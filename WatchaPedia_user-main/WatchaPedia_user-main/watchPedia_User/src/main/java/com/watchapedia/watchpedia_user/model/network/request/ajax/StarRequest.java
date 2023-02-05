package com.watchapedia.watchpedia_user.model.network.request.ajax;

import com.watchapedia.watchpedia_user.model.dto.content.ajax.StarDto;
import com.watchapedia.watchpedia_user.model.entity.User;

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
            String starContentType, Long starContentIdx, Long starUserIdx, Long starPoint
    ){
        return StarDto.of(
                starContentType, starContentIdx, starUserIdx, starPoint
        );
    }
}
