package com.watchapedia.watchpedia_user.model.network.response;

import com.watchapedia.watchpedia_user.model.dto.PersonDto;

public record PersonResponse(
        String name,
        String photo,
        String role
) {
    public static PersonResponse of(String name, String photo, String role){
        return new PersonResponse(name,photo,role);
    }
    public static PersonResponse from(PersonDto dto, String role){
        return new PersonResponse(
            dto.name(),
            dto.photo(),
            role
        );
    }
}
