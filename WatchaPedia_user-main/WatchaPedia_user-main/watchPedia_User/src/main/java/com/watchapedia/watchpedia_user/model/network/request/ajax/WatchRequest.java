package com.watchapedia.watchpedia_user.model.network.request.ajax;

public record WatchRequest(
        Long userIdx,
        String contentType,
        Long contentIdx
) {
}
