package com.watcha.watchapedia.model.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.Date;

@Entity(name = "tbComment")
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Data
public class Comment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long commIdx;
    private Long commUserIdx;
    @Column(length =50)
    private String commName;
    private String commText;
    @Column(length =10)
    private String commContentType;
    private Long commContentIdx;
    private Date commRegDate;
}
