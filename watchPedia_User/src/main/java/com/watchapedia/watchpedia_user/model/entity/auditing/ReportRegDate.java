package com.watchapedia.watchpedia_user.model.entity.auditing;

import jakarta.persistence.EntityListeners;
import jakarta.persistence.MappedSuperclass;
import lombok.Getter;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDateTime;

@Getter
@MappedSuperclass
@EntityListeners(AuditingEntityListener.class)
public abstract class ReportRegDate {
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) //파싱하기 편함
    @CreatedDate private LocalDateTime reportRegDate; // 생성일시
}
