package com.watchapedia.watchpedia_user.model.dto;

import com.watchapedia.watchpedia_user.model.entity.Person;
import com.watchapedia.watchpedia_user.model.entity.PersonLike;

public record PersonLikeDto(
        Long plikeIdx,
        Long userIdx,
        Long perIdx
) {



    public static PersonLikeDto of(Long plikeIdx,
            Long userIdx,
            Long perIdx){
        return new PersonLikeDto(null,
                userIdx, perIdx);
    }

    public static PersonLikeDto from(PersonLikeDto dto){
        return new PersonLikeDto(null,
                dto.userIdx(),
                dto.perIdx()
        );
    }
    public static PersonLike toEntity(Long plikeIdx,Long userIdx,Long perIdx){
        return PersonLike.of(plikeIdx,userIdx,perIdx);
    }
    public static PersonLike toEntity(Long userIdx,Long perIdx){
        return PersonLike.of(userIdx,perIdx);
    }
    public static PersonLike toEntity(PersonLikeDto dto){
        return PersonLike.of(dto);
    }
}
