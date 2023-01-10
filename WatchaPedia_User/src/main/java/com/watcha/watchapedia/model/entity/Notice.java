package com.watcha.watchapedia.model.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import java.time.LocalDateTime;

@Entity(name = "tbNotice")
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Data
public class Notice {
    @Id
    private Long ntcIdx;
    @Column(length = 100)
    private String ntcTitle;
    private String ntcText;
    private LocalDateTime ntcRegDate;
    private Long ntcRegBy;
    @Column(length = 20)
    private String ntcStatus;

}

