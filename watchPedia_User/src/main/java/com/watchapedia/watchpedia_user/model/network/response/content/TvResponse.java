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
        String tvRole,
        double avgStar,
        float starAvg,
        boolean isWatcha,
        boolean isNetflix
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
                people, summary, gallery, video, watch, backImg, starList,null,0.0,0,false,false
        );
    }
    public static TvResponse of(
            Long idx, String thumbnail, String title, String watch, Double avgStar
    ){
        return new TvResponse(
                idx, thumbnail, title, null, null, null, null, null, null,
                null, null, null, null, watch, null, null, null,avgStar, 0,false, false
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
                null,
                0,
                0,
                false,
                false
        );
    }
    //사용자-인물페이지--------------------------------------------------------------------------------------------------------
    public static TvResponse ofis(
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
            String tvRole,
            double avgStar,
            float starAvg,
            boolean isWatcha,
            boolean isNetflix){
        return new TvResponse(idx, thumbnail, title, titleOrg,
                makingDate, country, genre, channel, age, people, summary, gallery, video, watch, backImg, starList, tvRole, 0, starAvg, isWatcha, isNetflix);
    }

    public static TvResponse fromis(TvDto dto){
        return new TvResponse(
                dto.tvIdx(),
                dto.tvThumbnail(),
                dto.tvTitle(),
                dto.tvTitleOrg(),
                dto.tvMakingDate(),
                dto.tvChannel(),
                dto.tvGenre(),
                dto.tvCountry(),
                dto.tvAge(),
                dto.tvPeople(),
                dto.tvSummary(),
                dto.tvWatch(),
                dto.tvGallery(),
                dto.tvVideo(),
                dto.tvBackImg(),
                dto.starList(),
                null,
                0,
                0,
                false,
                false
        );
    }
    public static TvResponse fromis(TvDto dto, String tvRole, float starAvg, boolean isWatcha, boolean isNetflix){
        return new TvResponse(
                dto.tvIdx(),
                dto.tvThumbnail(),
                dto.tvTitle(),
                dto.tvTitleOrg(),
                dto.tvMakingDate(),
                dto.tvChannel(),
                dto.tvGenre(),
                dto.tvCountry(),
                dto.tvAge(),
                dto.tvPeople(),
                dto.tvSummary(),
                dto.tvWatch(),
                dto.tvGallery(),
                dto.tvVideo(),
                dto.tvBackImg(),
                dto.starList(),
                tvRole,
                0,
                starAvg,
                isWatcha,
                isNetflix
        );
    }

}
