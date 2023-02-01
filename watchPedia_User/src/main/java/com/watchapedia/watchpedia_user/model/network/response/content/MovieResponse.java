package com.watchapedia.watchpedia_user.model.network.response.content;

import com.watchapedia.watchpedia_user.model.dto.content.MovieDto;
import com.watchapedia.watchpedia_user.model.entity.content.ajax.Star;

import java.util.List;

public record MovieResponse(
        Long idx,
        String thumbnail,
        String title,
        String titleOrg,
        String makingDate,
        String country,
        String genre,
        String time,
        String age,
        String people,
        String summary,
        String gallery,
        String video,
        String watch,
        String backImg,
        List<Star> starList
) {
    public static MovieResponse of(
            Long idx, String thumbnail, String title, String titleOrg, String makingDate,
            String country, String genre, String time, String age, String people, String summary,
            String gallery, String video, String watch, String backImg, List<Star> starList
    ){
        return new MovieResponse(
                idx, thumbnail, title, titleOrg, makingDate, country, genre, time, age,
                people, summary, gallery, video, watch, backImg, starList
        );
    }

    public static MovieResponse from(MovieDto dto){
        return new MovieResponse(
                dto.movIdx(),
                dto.movThumbnail(),
                dto.movTitle(),
                dto.movTitleOrg(),
                dto.movMakingDate(),
                dto.movCountry(),
                dto.movGenre(),
                dto.movTime(),
                dto.movAge(),
                dto.movPeople(),
                dto.movSummary(),
                dto.movGallery(),
                dto.movVideo(),
                dto.movWatch(),
                dto.movBackImg(),
                dto.starList()
        );
    }
}
