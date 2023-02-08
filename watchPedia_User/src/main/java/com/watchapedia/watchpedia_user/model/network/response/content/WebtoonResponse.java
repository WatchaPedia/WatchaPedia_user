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
        String webtoonRole,
        double avgStar,
        float starAvg,
        boolean isWatcha,
        boolean isNetflix
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
                age, summary, people, makingDate, webWatch, starList,null,0.0, 0,false,false
        );
    }
    public static WebtoonResponse of(
            Long idx, String thumbnail, String title, String watch, Double avgStar
    ){
        return new WebtoonResponse(
                idx, thumbnail, title, null, null, null, null, null, null,
                null, null, null, watch , null,null,  null, avgStar,0,false,false
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
                null,
                0,
                0,
                false,
                false
        );
    }
    //사용자-인물페이지--------------------------------------------------------------------------------------------------------
    public static WebtoonResponse ofis(
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
            String webtoonRole,
            double avgStar,
            float starAvg,
            boolean isWatcha,
            boolean isNetflix){
        return new WebtoonResponse(idx, thumbnail, title, titleOrg,
                writer, genre,
                serDetail, serDay, serPeriod, age,
                summary, people,null, webWatch, starList, webtoonRole, 0, starAvg, isWatcha, isNetflix);
    }

    public static WebtoonResponse fromis(WebtoonDto dto){
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
                null,
                dto.webWatch(),
                dto.starList(),
                null,
                0,
                0,
                false,
                false
        );
    }

    public static WebtoonResponse fromis(WebtoonDto dto, String webtoonRole, float starAvg, boolean isWatcha, boolean isNetflix){
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
                null,
                dto.webWatch(),
                dto.starList(),
                webtoonRole,
                0,
                starAvg,
                isWatcha,
                isNetflix
        );
    }

}
