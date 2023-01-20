package com.watcha.watchapedia.model.network.response;


import com.watcha.watchapedia.model.dto.TvDto;

import java.io.Serializable;

public record TvResponse(
        Long tvIdx, String tvThumbnail, String tvTitle, String tvTitleOrg,
        String tvMakingDate, String tvChannel, String tvGenre,
        String tvCountry, String tvAge, String tvPeople, String tvSummary,
        String tvWatch, String tvGallery, String tvVideo,
        String tvBackImg
)implements Serializable {
    public static TvResponse of(
            Long tvIdx, String tvThumbnail, String tvTitle, String tvTitleOrg,
            String tvMakingDate, String tvChannel, String tvGenre,
            String tvCountry, String tvAge, String tvPeople, String tvSummary,
            String tvWatch, String tvGallery, String tvVideo,
            String tvBackImg){
        return new TvResponse(tvIdx, tvThumbnail, tvTitle, tvTitleOrg,
                tvMakingDate, tvChannel, tvGenre, tvCountry, tvAge, tvPeople, tvSummary, tvWatch, tvGallery, tvVideo, tvBackImg);
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
                dto.tvBackImg()
        );
    }
}