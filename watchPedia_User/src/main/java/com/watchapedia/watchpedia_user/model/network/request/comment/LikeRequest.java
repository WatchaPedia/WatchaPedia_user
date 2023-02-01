package com.watchapedia.watchpedia_user.model.network.request.comment;

public record LikeRequest(
        Long userIdx,
        Long commentIdx
) {
}
