package com.watchapedia.watchpedia_user.model.network.request;


import com.watchapedia.watchpedia_user.model.dto.TvDto;
import com.watchapedia.watchpedia_user.model.dto.WebtoonDto;
import com.watchapedia.watchpedia_user.model.entity.Star;

import java.util.List;

public record WebtoonRequest(
        Long webIdx, String webThumbnail, String webTitle, String webTitleOrg,
        String webWriter, String webGenre, String webSerDetail,
        String webSerDay, String webSerPeriod, String webAge, String webSummary,
        String webPeople, String webWatch, List<Star> starList
) {

    public static WebtoonRequest of(   Long webIdx, String webThumbnail, String webTitle, String webTitleOrg,
                                  String webWriter, String webGenre, String webSerDetail,
                                  String webSerDay, String webSerPeriod, String webAge, String webSummary,
                                  String webPeople, String webWatch, List<Star> starList) {
        return new WebtoonRequest( webIdx, webThumbnail, webTitle, webTitleOrg,
                webWriter, webGenre,
                webSerDetail, webSerDay, webSerPeriod, webAge,
                webSummary, webPeople, webWatch, starList);
    }

    public WebtoonDto toDto() {
        return WebtoonDto.of(
                webIdx, webThumbnail, webTitle, webTitleOrg,
                webWriter, webGenre,
                webSerDetail, webSerDay, webSerPeriod, webAge,
                webSummary, webPeople, webWatch, starList
        );
    }
}