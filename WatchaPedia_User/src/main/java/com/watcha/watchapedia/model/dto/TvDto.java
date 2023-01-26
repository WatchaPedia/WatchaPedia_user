package com.watcha.watchapedia.model.dto;

import com.watcha.watchapedia.model.entity.Tv;

public record TvDto(
        Long tvIdx, String tvThumbnail, String tvTitle, String tvTitleOrg,
        String tvMakingDate, String tvChannel, String tvGenre,
        String tvCountry, String tvAge, String tvPeople, String tvSummary,
        String tvWatch, String tvGallery, String tvVideo,
        String tvBackImg
) {
    public static TvDto of(
            Long tvIdx, String tvThumbnail, String tvTitle, String tvTitleOrg,
            String tvMakingDate, String tvChannel, String tvGenre,
            String tvCountry, String tvPeople, String tvSummary,
            String tvWatch, String tvGallery, String tvVideo,
            String tvBackImg
    ){

        return new TvDto(tvIdx, tvThumbnail, tvTitle, tvTitleOrg,
                tvMakingDate, tvChannel, tvGenre, tvCountry, null, tvPeople, tvSummary, tvWatch, tvGallery, tvVideo, tvBackImg
        );
    }

    public static TvDto of(
            Long tvIdx, String tvThumbnail, String tvTitle, String tvTitleOrg,
            String tvMakingDate, String tvChannel, String tvGenre,
            String tvCountry, String tvAge, String tvPeople, String tvSummary,
            String tvWatch, String tvGallery, String tvVideo,
            String tvBackImg
    ){

        return new TvDto(tvIdx, tvThumbnail, tvTitle, tvTitleOrg,
                tvMakingDate, tvChannel, tvGenre, tvCountry, tvAge, tvPeople, tvSummary, tvWatch, tvGallery, tvVideo, tvBackImg
        );
    }

    public static TvDto from(Tv entity){
        return new TvDto(
                entity.getTvIdx(),
                entity.getTvThumbnail(),
                entity.getTvTitle(),
                entity.getTvTitleOrg(),
                entity.getTvMakingDate(),
                entity.getTvChannel(),
                entity.getTvGenre(),
                entity.getTvCountry(),
                entity.getTvAge(),
                entity.getTvPeople(),
                entity.getTvSummary(),
                entity.getTvWatch(),
                entity.getTvGallery(),
                entity.getTvVideo(),
                entity.getTvBackImg()
        );
    }
    public Tv toEntity(){
        return  Tv.of(
                tvIdx, tvThumbnail, tvTitle, tvTitleOrg,
                tvMakingDate, tvChannel, tvGenre, tvCountry, tvAge, tvPeople, tvSummary, tvWatch, tvGallery, tvVideo, tvBackImg

        );
    }
}
