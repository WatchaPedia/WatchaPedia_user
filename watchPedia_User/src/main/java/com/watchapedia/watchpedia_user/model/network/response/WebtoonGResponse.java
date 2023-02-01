package com.watchapedia.watchpedia_user.model.network.response;

import com.watchapedia.watchpedia_user.model.dto.WebtoonDto;
import com.watchapedia.watchpedia_user.model.entity.Star;

import java.util.List;

public record WebtoonGResponse(
        Long webIdx, String webThumbnail, String webTitle, String webTitleOrg,
        String webWriter, String webGenre, String webSerDetail,
        String webSerDay, String webSerPeriod, String webAge, String webSummary,
        String webPeople, String webWatch, List<Star> starList
) {
    public static WebtoonGResponse of(
            Long webIdx, String webThumbnail, String webTitle, String webTitleOrg,
            String webWriter, String webGenre, String webSerDetail,
            String webSerDay, String webSerPeriod, String webAge, String webSummary,
            String webPeople, String webWatch, List<Star> starList){
        return new WebtoonGResponse(webIdx, webThumbnail, webTitle, webTitleOrg,
                webWriter, webGenre,
                webSerDetail, webSerDay, webSerPeriod, webAge,
                webSummary, webPeople, webWatch, starList);
    }

    public static WebtoonGResponse from (WebtoonDto dto){
        return new WebtoonGResponse(
                dto.webIdx(),
                dto.webThumbnail(),
                dto.webTitle(),
                dto.webTitleOrg(),
                dto.webWriter(),
                dto.webGenre(),
                dto.webSerDetail(),
                dto.webSerDay(),
                dto.webSerPeriod(),
                dto.webAge(),
                dto.webSummary(),
                dto.webPeople(),
                dto.webWatch(),
                dto.starList()
        );
    }
}