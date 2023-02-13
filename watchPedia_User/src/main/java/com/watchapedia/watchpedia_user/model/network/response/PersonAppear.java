package com.watchapedia.watchpedia_user.model.network.response;

import com.watchapedia.watchpedia_user.model.entity.Person;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PersonAppear {
    private Long perIdx;
    private String perName;
    private String perPhoto;
    private String perRole;
    private String perBiography;
    private List<String> appearance;

    public static PersonAppear personAppear(Person p, List<String> str){
        PersonAppear personAppear = PersonAppear.builder()
                .perIdx(p.getPerIdx())
                .perName(p.getPerName())
                .perPhoto(p.getPerPhoto())
                .perRole(p.getPerRole())
                .perBiography(p.getPerBiography())
                .appearance(str)
                .build();
        return personAppear;
    }

}
