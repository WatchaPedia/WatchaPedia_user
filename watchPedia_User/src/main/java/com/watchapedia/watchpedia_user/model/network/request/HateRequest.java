package com.watchapedia.watchpedia_user.model.network.request;

public record HateRequest(
        Long userIdx,
        String contentType,
        Long contentIdx
) {
}
