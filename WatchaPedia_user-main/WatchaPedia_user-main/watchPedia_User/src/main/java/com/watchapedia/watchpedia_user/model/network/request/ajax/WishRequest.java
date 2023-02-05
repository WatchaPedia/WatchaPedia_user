package com.watchapedia.watchpedia_user.model.network.request.ajax;

public record WishRequest(
        String contentType,
        Long contentIdx,
        Long userIdx
) {
}
