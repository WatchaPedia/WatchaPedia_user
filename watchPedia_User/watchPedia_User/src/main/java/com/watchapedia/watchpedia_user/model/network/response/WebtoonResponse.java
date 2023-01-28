package com.watchapedia.watchpedia_user.model.network.response;



import com.watchapedia.watchpedia_user.model.dto.WebtoonDto;
import com.watchapedia.watchpedia_user.model.entity.Star;

import java.io.Serializable;
import java.util.List;

public record WebtoonResponse(
        Long webIdx, String webThumbnail, String webTitle, String webTitleOrg,
        String webWriter, String webGenre, String webSerDetail,
        String webSerDay, String webSerPeriod, String webAge, String webSummary,
        String webPeople, String webWatch, List<Star> starList
) {
    public static WebtoonResponse of(
            Long webIdx, String webThumbnail, String webTitle, String webTitleOrg,
            String webWriter, String webGenre, String webSerDetail,
            String webSerDay, String webSerPeriod, String webAge, String webSummary,
            String webPeople, String webWatch, List<Star> starList){
        return new WebtoonResponse(webIdx, webThumbnail, webTitle, webTitleOrg,
                webWriter, webGenre,
                webSerDetail, webSerDay, webSerPeriod, webAge,
                webSummary, webPeople, webWatch, starList);
    }

    public static WebtoonResponse from (WebtoonDto dto){
        return new WebtoonResponse(
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