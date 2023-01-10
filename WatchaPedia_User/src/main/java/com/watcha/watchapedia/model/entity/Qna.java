package com.watcha.watchapedia.model.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import java.time.LocalDateTime;

@Entity(name = "tbQna")
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Data
public class Qna {
    @Id
    private Long qnaIdx;

    private String qnaText;
    private Long qnaUserIdx;
    @Column(length = 20)
    private String qnaUserid;
    private LocalDateTime qnaRegDate;
    @Column
    private String qnaAttach;
    @Column(length = 20)
    private String qnaStatus;
    private Long qnaAUserIdx;
    @Column(length = 20)
    private String qnaAUserid;

    private String qnaAText;
    private LocalDateTime qnaARegDate;

}




