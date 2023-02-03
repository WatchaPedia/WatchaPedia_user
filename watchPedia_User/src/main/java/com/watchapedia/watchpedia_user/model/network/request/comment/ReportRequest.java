package com.watchapedia.watchpedia_user.model.network.request.comment;

public record ReportRequest(
        Long userIdx,
        String commType,
        Long commIdx,
        String text,
        boolean spoiler,
        boolean inap,
        Long reporter
) {
}
