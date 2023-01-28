package com.watchapedia.watchpedia_user.model.dto;

import com.watchapedia.watchpedia_user.model.entity.Movie;
import com.watchapedia.watchpedia_user.model.entity.Star;
import com.watchapedia.watchpedia_user.model.network.response.MovieResponse;

import java.util.List;
import java.util.stream.Collectors;

public record MovieDto(
        Long movIdx,
        String movThumbnail,
        String movTitle,
        String movTitleOrg,
        String movMakingDate,
        String movCountry,
        String movGenre,
        String movTime,
        String movAge,
        String movPeople,
        String movSummary,
        String movGallery,
        String movVideo,
        String movWatch,
        String movBackImg,
        List<Star> starList
) {
    public static MovieDto of(
            Long movIdx, String movThumbnail, String movTitle,
            String movMakingDate, String movCountry
    ){
        return new MovieDto(
                movIdx,movThumbnail,movTitle,null,movMakingDate,movCountry,
                null, null, null, null, null,
                null, null, null, null, null
        );
    }
    public static MovieDto from(Movie entity){
        return new MovieDto(
                entity.getMovIdx(),
                entity.getMovThumbnail(),
                entity.getMovTitle(),
                entity.getMovTitleOrg(),
                entity.getMovMakingDate(),
                entity.getMovCountry(),
                entity.getMovGenre(),
                entity.getMovTime(),
                entity.getMovAge(),
                entity.getMovPeople(),
                entity.getMovSummary(),
                entity.getMovGallery(),
                entity.getMovVideo(),
                entity.getMovWatch(),
                entity.getMovBackImg(),
                (List<Star>) entity.getStar().stream().map(star -> {
                    if(star.getStarContentIdx() == entity.getMovIdx()
                        && star.getStarContentType().equals("영화")
                    ){
                        return star;
                    }
                    else{
                        return null;
                    }
                }).collect(Collectors.toList())
        );
    }
}
