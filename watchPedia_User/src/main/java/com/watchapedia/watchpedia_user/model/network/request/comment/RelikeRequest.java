package com.watchapedia.watchpedia_user.model.network.request.comment;

public record RelikeRequest(
        Long recommIdx,
        Long userIdx
) {
}
