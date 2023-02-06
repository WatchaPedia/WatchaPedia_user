package com.watchapedia.watchpedia_user.model.dto.content;




import com.watchapedia.watchpedia_user.model.entity.content.Webtoon;
import com.watchapedia.watchpedia_user.model.entity.content.ajax.Star;

import java.util.List;

public record WebtoonDto(
        Long webIdx, String webThumbnail, String webTitle, String webTitleOrg,
        String webWriter, String webGenre, String webSerDetail,
        String webSerDay, String webSerPeriod, String webAge, String webSummary,
        String webPeople, String webWatch, List<Star> starList, double avg
) {


    public static WebtoonDto of(
            Long webIdx, String webThumbnail, String webTitle, String webTitleOrg,
            String webWriter, String webGenre, String webSerDetail,
            String webSerDay, String webSerPeriod, String webAge, String webSummary,
            String webPeople, String webWatch, List<Star> starList, double avg
    ){

        return new WebtoonDto(webIdx, webThumbnail, webTitle, webTitleOrg,
                webWriter, webGenre, webSerDetail, webSerDay, webSerPeriod, webAge, webSummary, webPeople, webWatch, starList,avg
        );
    }

    public static WebtoonDto from(Webtoon entity){
        return new WebtoonDto(
                entity.getWebIdx(),
                entity.getWebThumbnail(),
                entity.getWebTitle(),
                entity.getWebTitleOrg(),
                entity.getWebWriter(),
                entity.getWebGenre(),
                entity.getWebSerDetail(),
                entity.getWebSerDay(),
                entity.getWebSerPeriod(),
                entity.getWebAge(),
                entity.getWebSummary(),
                entity.getWebPeople(),
//                entity.getWebMakingDate(),
                entity.getWebWatch(),
                entity.getStar(),
                0.0
        );
    }
    public static WebtoonDto from(Webtoon entity, double avg){
        return new WebtoonDto(
                entity.getWebIdx(),
                entity.getWebThumbnail(),
                entity.getWebTitle(),
                entity.getWebTitleOrg(),
                entity.getWebWriter(),
                entity.getWebGenre(),
                entity.getWebSerDetail(),
                entity.getWebSerDay(),
                entity.getWebSerPeriod(),
                entity.getWebAge(),
                entity.getWebSummary(),
                entity.getWebPeople(),
                entity.getWebWatch(),
                entity.getStar(),
                avg
        );
    }

    }

