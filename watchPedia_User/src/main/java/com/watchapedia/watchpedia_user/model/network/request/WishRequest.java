package com.watchapedia.watchpedia_user.model.network.request;

import com.watchapedia.watchpedia_user.model.dto.WishDto;

public record WishRequest(
        String contentType,
        Long contentIdx,
        Long userIdx
) {
}
