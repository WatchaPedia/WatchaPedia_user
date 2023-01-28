package com.watchapedia.watchpedia_user.model.dto;

import com.watchapedia.watchpedia_user.model.entity.Tv;
import com.watchapedia.watchpedia_user.model.entity.Star;

import java.util.List;
import java.util.stream.Collectors;

public record TvDto(
        Long tvIdx, String tvThumbnail, String tvTitle, String tvTitleOrg,
        String tvMakingDate, String tvChannel, String tvGenre,
        String tvCountry, String tvAge, String tvPeople, String tvSummary,
        String tvWatch, String tvGallery, String tvVideo,
        String tvBackImg, List<Star> starList
) {
//    public static TvDto of(
//            Long tvIdx, String tvThumbnail, String tvTitle, String tvTitleOrg,
//            String tvMakingDate, String tvChannel, String tvGenre,
//            String tvCountry, String tvPeople, String tvSummary,
//            String tvWatch, String tvGallery, String tvVideo,
//            String tvBackImg
//    ) {
//
//        return new TvDto(tvIdx, tvThumbnail, tvTitle, tvTitleOrg,
//                tvMakingDate, tvChannel, tvGenre, tvCountry, null, tvPeople, tvSummary, tvWatch, tvGallery, tvVideo, tvBackImg
//        );
//    }

    public static TvDto of(
            Long tvIdx, String tvThumbnail, String tvTitle, String tvTitleOrg,
            String tvMakingDate, String tvChannel, String tvGenre,
            String tvCountry, String tvAge, String tvPeople, String tvSummary,
            String tvWatch, String tvGallery, String tvVideo,
            String tvBackImg, List<Star> starList
    ) {

        return new TvDto(tvIdx, tvThumbnail, tvTitle, tvTitleOrg,
                tvMakingDate, tvChannel, tvGenre, tvCountry, tvAge,
                tvPeople, tvSummary, tvWatch, tvGallery, tvVideo, tvBackImg, starList
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
                (List<Star>) entity.getStar().stream().map(star -> {
                    if (star.getStarContentIdx() == entity.getTvIdx()
                            && star.getStarContentType().equals("영화")
                    ) {
                        return star;
                    } else {
                        return null;
                    }
                }).collect(Collectors.toList())
        );
    }

}
