package com.watcha.watchapedia.model.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity(name = "tbAdvertise")
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Data

public class Advertise {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long adIdx;

}