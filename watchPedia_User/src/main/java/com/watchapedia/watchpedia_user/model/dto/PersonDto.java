package com.watchapedia.watchpedia_user.model.dto;

import com.watchapedia.watchpedia_user.model.entity.Person;

public record PersonDto(
        Long idx,
        String name,
        String photo,
        String role,
        String biography,
        String mov,
        String book,
        String webtoon,
        String tv
) {
    public static PersonDto from(Person entity){
        return new PersonDto(
                entity.getPerIdx(),
                entity.getPerName(),
                entity.getPerPhoto(),
                entity.getPerRole(),
                entity.getPerBiography(),
                entity.getPerMov(),
                entity.getPerBook(),
                entity.getPerWebtoon(),
                entity.getPerTv()
        );
    }
}
