package com.watchapedia.watchpedia_user.model.dto.content;



import com.watchapedia.watchpedia_user.model.entity.content.Tv;
import com.watchapedia.watchpedia_user.model.entity.content.ajax.Star;

import java.util.List;

public record TvDto(
        Long tvIdx, String tvThumbnail, String tvTitle, String tvTitleOrg,
        String tvMakingDate, String tvChannel, String tvGenre,
        String tvCountry, String tvAge, String tvPeople, String tvSummary,
        String tvWatch, String tvGallery, String tvVideo,
        String tvBackImg, List<Star> starList, double avg
) {

    public static TvDto of(
            Long tvIdx, String tvThumbnail, String tvTitle, String tvTitleOrg,
            String tvMakingDate, String tvChannel, String tvGenre,
            String tvCountry, String tvAge, String tvPeople, String tvSummary,
            String tvWatch, String tvGallery, String tvVideo,
            String tvBackImg, List<Star> starList, double avg
    ) {

        return new TvDto(tvIdx, tvThumbnail, tvTitle, tvTitleOrg,
                tvMakingDate, tvChannel, tvGenre, tvCountry, tvAge,
                tvPeople, tvSummary, tvWatch, tvGallery, tvVideo, tvBackImg, starList, avg
        );
    }

    public static TvDto from(Tv entity) {
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
                entity.getTvBackImg(),
                entity.getStar(),
                0.0
        );
    }
    public static TvDto from(Tv entity, double avg) {
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
                entity.getTvBackImg(),
                entity.getStar(),
                avg
        );
    }

}