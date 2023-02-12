package com.watchapedia.watchpedia_user.model.dto;

import com.watchapedia.watchpedia_user.model.entity.Person;
import com.watchapedia.watchpedia_user.model.entity.PersonLike;

public record PersonLikeDto(
        Long userIdx,
        Long perIdx
) {



    public static PersonLikeDto of(
            Long userIdx,
            Long perIdx){
        return new PersonLikeDto(
                userIdx, perIdx);
    }

    public static PersonLikeDto from(PersonLikeDto dto){
        return new PersonLikeDto(
                dto.userIdx(),
                dto.perIdx()
        );
    }
    public static PersonLike toEntity(Long userIdx,Long perIdx){
        return PersonLike.of(userIdx,perIdx);
    }
    public static PersonLike toEntity(PersonLikeDto dto){
        return PersonLike.of(dto);
    }
}
