package com.watchapedia.watchpedia_user.model.network.request.comment;

public record RecommentRequest(
    Long idx,
    Long userIdx,
    Long commIdx,
    String text
) {
}
