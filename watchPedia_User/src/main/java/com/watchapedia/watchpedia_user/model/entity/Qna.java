package com.watchapedia.watchpedia_user.model.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;

@Entity(name = "tb_qna")
@Data
public class Qna {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long qnaIdx;

    private String qnaText;
    @ManyToOne @JoinColumn(name = "qna_user_idx") private User user;
    private String qnaUserid;
    private LocalDateTime qnaRegDate;
    private String qnaAttach;
    private String qnaStatus;
    private Long qnaAuserIdx;
    private String qnaAuserid;
    private String qnaAtext;
    private LocalDateTime qnaAregDate;
    private String qnaAnswer;
    private String qnaName;
    private String qnaFile;
    private String qnaDtext;
}
