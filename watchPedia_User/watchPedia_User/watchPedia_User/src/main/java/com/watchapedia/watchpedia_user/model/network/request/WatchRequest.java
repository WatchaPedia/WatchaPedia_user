package com.watchapedia.watchpedia_user.model.network.request;

public record WatchRequest(
        Long userIdx,
        String contentType,
        Long contentIdx
) {
}
