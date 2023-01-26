package com.watcha.watchapedia.model.network.request;


import com.watcha.watchapedia.model.dto.TvDto;

public record WebtoonRequest(
        Long webIdx, String webThumbnail, String webTitle, String webTitleOrg,
        String webWriter, String webGenre, String webSerDetail,
        String webSerDay, String webSerPeriod, String webAge, String webSummary,
        String webPeople, String webWatch
) {

    public static WebtoonRequest of(Long webIdx, String webThumbnail, String webTitle, String webTitleOrg,
                                    String webWriter, String webGenre, String webSerDetail,
                                    String webSerDay, String webSerPeriod, String webAge, String webSummary,
                                    String webPeople, String webWatch) {
        return new WebtoonRequest(webIdx, webThumbnail, webTitle, webTitleOrg,
                webWriter, webGenre, webSerDetail, webSerDay, webSerPeriod, webAge, webSummary, webPeople, webWatch);
    }

    public WebtoonRequest toDto() {
        return WebtoonRequest.of(
                webIdx, webThumbnail, webTitle, webTitleOrg,
                webWriter, webGenre, webSerDetail, webSerDay, webSerPeriod, webAge, webSummary, webPeople, webWatch
        );
    }
}