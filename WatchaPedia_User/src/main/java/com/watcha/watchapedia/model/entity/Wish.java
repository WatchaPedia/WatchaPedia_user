package com.watcha.watchapedia.model.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

@Entity(name = "tbWish")
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Wish {
    @Id
    private Long wishIdx;
    @Column(length = 10)
    private String wishContentType;
    private Long wishContentIdx;
    private Long wishUserIdx;

}

//
//wish_idx int auto_increment primary key,
//        wish_content_type varchar(10) not null,
//        wish_content_idx int not null,
//        wish_user_idx int not null