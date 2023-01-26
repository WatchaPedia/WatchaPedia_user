package com.watcha.watchapedia.model.dto;


import com.watcha.watchapedia.model.entity.Webtoon;

public record WebtoonDto(
        Long webIdx, String webThumbnail, String webTitle, String webTitleOrg,
        String webWriter, String webGenre, String webSerDetail,
        String webSerDay, String webSerPeriod, String webAge, String webSummary,
        String webPeople, String webWatch
) {
    public static WebtoonDto of(
            String webThumbnail, String webTitle, String webTitleOrg,
            String webWriter, String webGenre, String webSerDetail,
            String webSerDay, String webSerPeriod, String webAge, String webSummary,
            String webPeople, String webWatch
    ){

        return new WebtoonDto(null, webThumbnail, webTitle, webTitleOrg,
                webWriter, webGenre, webSerDetail, webSerDay, webSerPeriod, webAge, webSummary, webPeople, webWatch
        );
    }

    public static WebtoonDto of(
            Long webIdx, String webThumbnail, String webTitle, String webTitleOrg,
            String webWriter, String webGenre, String webSerDetail,
            String webSerDay, String webSerPeriod, String webAge, String webSummary,
            String webPeople, String webWatch
    ){

        return new WebtoonDto(webIdx, webThumbnail, webTitle, webTitleOrg,
                webWriter, webGenre, webSerDetail, webSerDay, webSerPeriod, webAge, webSummary, webPeople, webWatch
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
                entity.getWebWatch()
        );
    }
    public Webtoon toEntity(){
        return  Webtoon.of(
                webIdx, webThumbnail, webTitle, webTitleOrg,
                webWriter, webGenre, webSerDetail, webSerDay, webSerPeriod, webAge, webSummary, webPeople, webWatch

        );
    }
}
