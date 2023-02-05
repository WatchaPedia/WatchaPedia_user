package com.watchapedia.watchpedia_user.model.network.response.content;

import com.watchapedia.watchpedia_user.model.dto.content.TvDto;
import com.watchapedia.watchpedia_user.model.entity.content.ajax.Star;

import java.util.List;

public record TvResponse(
        Long tvIdx,
        String tvThumbnail,
        String tvTitle,
        String tvTitleOrg,
        String tvMakingDate,
        String tvCountry,
        String tvGenre,
        String tvChannel,
        String tvAge,
        String tvPeople,
        String tvSummary,
        String tvGallery,
        String tvVideo,
        String tvWatch,
        String tvBackImg,
        List<Star> starList,
        double avgStar
) {
    public static TvResponse of(
            Long tvIdx,
            String tvThumbnail,
            String tvTitle,
            String tvTitleOrg,
            String tvMakingDate,
            String tvCountry,
            String tvGenre,
            String tvChannel,
            String tvAge,
            String tvPeople,
            String tvSummary,
            String tvGallery,
            String tvVideo,
            String tvWatch,
            String tvBackImg,
            List<Star> starList
    ){
        return new TvResponse(
                tvIdx, tvThumbnail, tvTitle, tvTitleOrg, tvMakingDate, tvCountry, tvGenre, tvChannel, tvAge,
                tvPeople, tvSummary, tvGallery, tvVideo, tvWatch, tvBackImg, starList,0.0
        );
    }
    public static TvResponse of(
            Long tvIdx, String tvThumbnail, String tvTitle, String tvWatch, Double avgStar
    ){
        return new TvResponse(
                tvIdx, tvThumbnail, tvTitle, null, null, null, null, null, null,
                null, null, null, null, tvWatch, null, null, avgStar
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
