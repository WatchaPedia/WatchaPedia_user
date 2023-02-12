package com.watchapedia.watchpedia_user.model.entity;


import com.watchapedia.watchpedia_user.model.dto.PersonLikeDto;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity(name = "tb_person_like")
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Data
public class PersonLike {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long plikeIdx;
    private Long perIdx;
    private Long userIdx;

    public PersonLike(Long userIdx, Long perIdx) {
    this.userIdx=userIdx;
    this.perIdx=perIdx;
    }
    public PersonLike(PersonLikeDto dto){
        this.plikeIdx = dto.plikeIdx();
        this.userIdx = dto.userIdx();
        this.perIdx = dto.perIdx();
    }

    public static PersonLike of(Long userIdx,Long perIdx){
        return new PersonLike(userIdx,perIdx);
    }
    public static PersonLike of(PersonLikeDto personLikeDto){
        return new PersonLike(personLikeDto);
    }
    public static PersonLike of(Long plikeIdx,Long userIdx,Long perIdx){
        return new PersonLike(plikeIdx, userIdx,perIdx);
    }
}

