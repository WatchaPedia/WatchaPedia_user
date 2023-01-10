package com.watcha.watchapedia.model.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity(name = "tbRecomment")
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Data
public class Recomment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long recommIdx;
    private Long recommCommIdx;
    private Long recommUserIdx;
    @Column(length =20)
    private String recommName;
    private String recommText;
    private LocalDateTime recommRegDate;

}


