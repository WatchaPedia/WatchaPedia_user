package com.watchapedia.watchpedia_user.model.entity.comment;


import com.watchapedia.watchpedia_user.model.entity.auditing.ReportRegDate;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@Data
@Entity(name = "tb_report")
@AllArgsConstructor
@NoArgsConstructor
public class Report extends ReportRegDate {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long reportIdx;
    private Long reportUserIdx;
    private String reportCommType;
    private Long reportCommIdx;
    private String reportText;
    private Long reportSpoiler;
    private Long reportInappropriate;
    private String reportReporter;
    private String reportProcessing;

    public Report(Long reportUserIdx, String reportCommType, Long reportCommIdx, String reportText, Long reportSpoiler, Long reportInappropriate, String reportReporter) {
        this.reportUserIdx = reportUserIdx;
        this.reportCommType = reportCommType;
        this.reportCommIdx = reportCommIdx;
        this.reportText = reportText;
        this.reportSpoiler = reportSpoiler;
        this.reportInappropriate = reportInappropriate;
        this.reportReporter = reportReporter;
    }

    public static Report of(
            Long reportUserIdx, String reportCommType, Long reportCommIdx,
         String reportText, Long reportSpoiler, Long reportInappropriate, String reportReporter
    ){
        return new Report(
                reportUserIdx,reportCommType,reportCommIdx,reportText,
                reportSpoiler,reportInappropriate,reportReporter
        );
    }
}
