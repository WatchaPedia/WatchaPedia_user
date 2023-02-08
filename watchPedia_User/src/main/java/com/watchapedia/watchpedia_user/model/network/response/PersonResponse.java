package com.watchapedia.watchpedia_user.model.network.response;

import com.watchapedia.watchpedia_user.model.dto.PersonDto;

public record PersonResponse(
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
    //    null,false 추가함(idx /  biography / mov/ book/ webtoon/ tv) => 동민
    public static PersonResponse of(String name, String photo, String role){
        return new PersonResponse(null,name,photo,role,null,null,null,null,null);
    }
    public static PersonResponse from(PersonDto dto, String role){
        return new PersonResponse(
                dto.idx(),
                dto.name(),
                dto.photo(),
                role,
                null,
                null,
                null,
                null,
                null
        );
    }
    //사용자-인물페이지--------------------------------------------------------------------------------------------------------
    public static PersonResponse ofis(        Long idx,
                                              String name,
                                              String photo,
                                              String role,
                                              String biography,
                                              String mov,
                                              String book,
                                              String webtoon,
                                              String tv){
        return new PersonResponse(idx, name, photo, role, biography, mov, book, webtoon, tv);
    }
    public static PersonResponse fromis(PersonDto dto, String role){
        return new PersonResponse(
                dto.idx(),
                dto.name(),
                dto.photo(),
                dto.biography(),
                dto.mov(),
                dto.book(),
                dto.webtoon(),
                dto.tv(),
                role
        );
    }
    public static PersonResponse fromis(PersonDto dto){
        return new PersonResponse(
                dto.idx(),
                dto.name(),
                dto.photo(),
                dto.role(),
                dto.biography(),
                dto.mov(),
                dto.book(),
                dto.webtoon(),
                dto.tv()
        );
    }


}

