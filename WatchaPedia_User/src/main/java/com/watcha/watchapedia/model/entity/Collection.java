package com.watcha.watchapedia.model.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

@Entity(name = "tbCollection")
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Data
public class Collection {
    @Id
    private Long colIdx;
    @Column(length = 10)
    private String colContentType;
    @Column(length = 50)
    private String colName;

}
