package com.watchapedia.watchpedia_user.model.network.request;


import com.watchapedia.watchpedia_user.model.dto.TvDto;
import com.watchapedia.watchpedia_user.model.entity.Star;

import java.util.List;

public record TvRequest(
        Long tvIdx, String tvThumbnail, String tvTitle, String tvTitleOrg,
        String tvMakingDate, String tvChannel, String tvGenre,
        String tvCountry, String tvAge, String tvPeople, String tvSummary,
        String tvWatch, String tvGallery, String tvVideo,
        String tvBackImg, List<Star> starList
) {

    public static TvRequest of(   Long tvIdx, String tvThumbnail, String tvTitle, String tvTitleOrg,
                                  String tvMakingDate, String tvChannel, String tvGenre,
                                  String tvCountry, String tvAge, String tvPeople, String tvSummary,
                                  String tvWatch, String tvGallery, String tvVideo,
                                  String tvBackImg, List<Star> starList) {
        return new TvRequest( tvIdx, tvThumbnail, tvTitle, tvTitleOrg,
                tvMakingDate, tvChannel, tvGenre, tvCountry, tvAge,
                tvPeople, tvSummary, tvWatch, tvGallery, tvVideo, tvBackImg, starList);
    }

    public TvDto toDto() {
        return TvDto.of(
                tvIdx, tvThumbnail, tvTitle, tvTitleOrg,
                tvMakingDate, tvChannel, tvGenre, tvCountry, tvAge,
                tvPeople, tvSummary, tvWatch, tvGallery, tvVideo, tvBackImg, starList
        );
    }
}