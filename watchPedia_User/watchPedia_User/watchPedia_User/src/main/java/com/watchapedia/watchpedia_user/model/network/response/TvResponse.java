package com.watchapedia.watchpedia_user.model.network.response;

import com.watchapedia.watchpedia_user.model.dto.TvDto;
import com.watchapedia.watchpedia_user.model.entity.Star;

import java.util.List;

public record TvResponse(
        Long tvIdx, String tvThumbnail, String tvTitle, String tvTitleOrg,
        String tvMakingDate, String tvChannel, String tvGenre,
        String tvCountry, String tvAge, String tvPeople, String tvSummary,
        String tvWatch, String tvGallery, String tvVideo,
        String tvBackImg, List<Star> starList
) {
    public static TvResponse of(
            Long tvIdx, String tvThumbnail, String tvTitle, String tvTitleOrg,
            String tvMakingDate, String tvChannel, String tvGenre,
            String tvCountry, String tvAge, String tvPeople, String tvSummary,
            String tvWatch, String tvGallery, String tvVideo,
            String tvBackImg,  List<Star> starList){

        return new TvResponse(tvIdx, tvThumbnail, tvTitle, tvTitleOrg,
                tvMakingDate, tvChannel, tvGenre, tvCountry, tvAge, tvPeople, tvSummary, tvWatch, tvGallery, tvVideo, tvBackImg, starList);
    }

    public static TvResponse from (TvDto dto){
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
                dto.starList()
        );
    }
}