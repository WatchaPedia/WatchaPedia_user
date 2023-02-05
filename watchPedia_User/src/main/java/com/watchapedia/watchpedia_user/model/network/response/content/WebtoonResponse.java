package com.watchapedia.watchpedia_user.model.network.response.content;

import com.watchapedia.watchpedia_user.model.dto.content.WebtoonDto;
import com.watchapedia.watchpedia_user.model.entity.content.ajax.Star;

import java.util.List;

public record WebtoonResponse(
        Long idx,
        String thumbnail,
        String title,
        String titleOrg,
        String writer,
        String genre,
        String serDetail,
        String serDay,
        String serPeriod,
        String age,
        String summary,
        String people,
        String makingDate,
        String webWatch,
        List<Star> starList,
        double avgStar
) {
    public static WebtoonResponse of(
            Long idx,
            String thumbnail,
            String title,
            String titleOrg,
            String writer,
            String genre,
            String serDetail,
            String serDay,
            String serPeriod,
            String age,
            String summary,
            String people,
            String makingDate,
            String webWatch,
            List<Star> starList
    ){
        return new WebtoonResponse(
                idx, thumbnail, title, titleOrg, writer, genre, serDetail, serDay, serPeriod,
                age, summary, people, makingDate, webWatch, starList,0.0
        );
    }
    public static WebtoonResponse of(
            Long idx, String thumbnail, String title, String watch, Double avgStar
    ){
        return new WebtoonResponse(
                idx, thumbnail, title, null, null, null, null, null, null,
                null, null, null, watch , null,null,  avgStar
        );
    }

    public static WebtoonResponse from(WebtoonDto dto){
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
                "",
                dto.webWatch(),
                dto.starList(),
                0
        );
    }


}
