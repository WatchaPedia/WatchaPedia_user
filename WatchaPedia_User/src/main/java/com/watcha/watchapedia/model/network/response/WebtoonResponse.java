package com.watcha.watchapedia.model.network.response;


import com.watcha.watchapedia.model.dto.WebtoonDto;

import java.io.Serializable;

public record WebtoonResponse(
        Long webIdx, String webThumbnail, String webTitle, String webTitleOrg,
        String webWriter, String webGenre, String webSerDetail,
        String webSerDay, String webSerPeriod, String webAge, String webSummary,
        String webPeople, String webWatch
)implements Serializable {
    public static WebtoonResponse of(
            Long webIdx, String webThumbnail, String webTitle, String webTitleOrg,
            String webWriter, String webGenre, String webSerDetail,
            String webSerDay, String webSerPeriod, String webAge, String webSummary,
            String webPeople, String webWatch){
        return new WebtoonResponse(webIdx, webThumbnail, webTitle, webTitleOrg,
                webWriter, webGenre, webSerDetail, webSerDay, webSerPeriod, webAge, webSummary, webPeople, webWatch);
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
                dto.webWatch()
        );
    }
}