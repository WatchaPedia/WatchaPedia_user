package com.watchapedia.watchpedia_user.model.dto;


import com.watchapedia.watchpedia_user.model.entity.Star;
import com.watchapedia.watchpedia_user.model.entity.Webtoon;

import java.util.List;
import java.util.stream.Collectors;

public record WebtoonDto(
        Long webIdx, String webThumbnail, String webTitle, String webTitleOrg,
        String webWriter, String webGenre, String webSerDetail,
        String webSerDay, String webSerPeriod, String webAge, String webSummary,
        String webPeople, String webWatch, List<Star> starList
) {
//    public static WebtoonDto of(
//            String webThumbnail, String webTitle, String webTitleOrg,
//            String webWriter, String webGenre, String webSerDetail,
//            String webSerDay, String webSerPeriod, String webAge, String webSummary,
//            String webPeople, String webWatch
//    ){
//
//        return new WebtoonDto(null, webThumbnail, webTitle, webTitleOrg,
//                webWriter, webGenre, webSerDetail, webSerDay, webSerPeriod, webAge, webSummary, webPeople, webWatch
//        );
//    }

    public static WebtoonDto of(
            Long webIdx, String webThumbnail, String webTitle, String webTitleOrg,
            String webWriter, String webGenre, String webSerDetail,
            String webSerDay, String webSerPeriod, String webAge, String webSummary,
            String webPeople, String webWatch, List<Star> starList
    ){

        return new WebtoonDto(webIdx, webThumbnail, webTitle, webTitleOrg,
                webWriter, webGenre, webSerDetail, webSerDay, webSerPeriod, webAge, webSummary, webPeople, webWatch, starList
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
                entity.getWebWatch(),
                entity.getStar()
        );
    }

    }

