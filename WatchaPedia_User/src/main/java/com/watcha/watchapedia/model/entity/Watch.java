package com.watcha.watchapedia.model.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

@Entity(name = "tbWatch")
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Data
public class Watch {
    @Id
    private Long watchIdx;
    private Long watchUserIdx;
    @Column(length = 10)
    private String watchContentType;
    private Long watchContentIdx;
}

