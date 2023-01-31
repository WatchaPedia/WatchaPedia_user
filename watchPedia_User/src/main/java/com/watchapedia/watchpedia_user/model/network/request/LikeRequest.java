package com.watchapedia.watchpedia_user.model.network.request;

public record LikeRequest(
        Long userIdx,
        Long commentIdx
) {
}
