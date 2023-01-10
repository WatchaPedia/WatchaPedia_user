package com.watcha.watchapedia.model.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity(name = "tbReport")
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Data

public class Report {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long reportIdx;
    @Column(length =50)
    private String reportUserName;
    @Column(length =50)
    private String reportContents;
    private LocalDateTime reportRegDate;
    @Column(length =100)
    private String reportReply;
    @Column(length =50)
    private String reportSpoiler;
    @Column(length =50)
    private String reportInappropriate;
    @Column(length =50)
    private String reportProcessing;
    @Column(length =50)
    private String reportCommentKinds;
}