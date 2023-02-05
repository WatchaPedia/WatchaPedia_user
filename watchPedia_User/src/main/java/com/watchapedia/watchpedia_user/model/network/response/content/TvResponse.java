package com.watchapedia.watchpedia_user.model.network.response.content;

import com.watchapedia.watchpedia_user.model.dto.content.TvDto;
import com.watchapedia.watchpedia_user.model.entity.content.ajax.Star;

import java.util.List;

public record TvResponse(
        Long idx,
        String thumbnail,
        String title,
        String titleOrg,
        String makingDate,
        String country,
        String genre,
        String channel,
        String age,
        String people,
        String summary,
        String gallery,
        String video,
        String watch,
        String backImg,
        List<Star> starList,
        double avgStar
) {
    public static TvResponse of(
            Long idx,
            String thumbnail,
            String title,
            String titleOrg,
            String makingDate,
            String country,
            String genre,
            String channel,
            String age,
            String people,
            String summary,
            String gallery,
            String video,
            String watch,
            String backImg,
            List<Star> starList

    ){
        return new TvResponse(
                idx, thumbnail, title, titleOrg, makingDate, country, genre, channel, age,
                people, summary, gallery, video, watch, backImg, starList,0.0
        );
    }
    public static TvResponse of(
            Long idx, String thumbnail, String title, String watch, Double avgStar
    ){
        return new TvResponse(
                idx, thumbnail, title, null, null, null, null, null, null,
                null, null, null, null, watch, null, null, avgStar
        );
    }

    public static TvResponse from(TvDto dto){
        return new TvResponse(
                dto.tvIdx(),
                dto.tvThumbnail(),
                dto.tvTitle(),
                dto.tvTitleOrg(),
                dto.tvMakingDate(),
                dto.tvCountry(),
                dto.tvGenre(),
                dto.tvChannel(),
                dto.tvAge(),
                dto.tvPeople(),
                dto.tvSummary(),
                dto.tvGallery(),
                dto.tvVideo(),
                dto.tvWatch(),
                dto.tvBackImg(),
                dto.starList(),
                0
        );
    }
}
