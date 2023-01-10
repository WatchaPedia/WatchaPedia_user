package com.watcha.watchapedia.model.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

@Entity(name = "tbHate")
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Data
public class Hate {
    @Id
    private Long hateIdx;
    private Long hateUserIdx;
    @Column(length = 10)
    private String hateContentType;
    private Long hateContentIdx;
}
