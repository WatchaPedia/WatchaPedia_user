package com.watchapedia.watchpedia_user.model.network.request.ajax;

public record HateRequest(
        Long userIdx,
        String contentType,
        Long contentIdx
) {
}
