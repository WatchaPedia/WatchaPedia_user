package com.watchapedia.watchpedia_user.model.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Entity(name = "tb_person")
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Data
public class Person {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long perIdx;
    private String perName;
    private String perPhoto;
    private String perRole;
    private String perBiography;

    private String perMov;
    private String perBook;
    private String perWebtoon;
    private String perTv;
}
