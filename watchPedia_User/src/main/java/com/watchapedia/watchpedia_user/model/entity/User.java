package com.watchapedia.watchpedia_user.model.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;

@Entity(name = "tb_user")
@Builder
@AllArgsConstructor
@Data
@ToString(callSuper = true)
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long userIdx;
    @Column(length =100)
    private String userPw;
    private Long userSsn1;
    private Long userSsn2;
    @Column(length =100)
    private String userEmail;
    @Column(length =50)
    private String userStatus;
    private Long userCautionCnt;
    private Long userWarningCnt;
    private Long userSuspensionCnt;
    private LocalDateTime userLatelyStop;
    private LocalDateTime userReleaseDate;
    @Column(length =30)
    private String userType;
    @Column(length =50)
    private String userName;
    @Column(length =100)
    private String userLikeActor;
    @Column(length =100)
    private String userLikeDirector;
    @Column(length =100)
    private String userLikeGenre;
    protected User() {}

    public User(String userPw, Long userSsn1, Long userSsn2, String userEmail, String userName) {
        this.userPw = userPw;
        this.userSsn1 = userSsn1;
        this.userSsn2 = userSsn2;
        this.userName = userName;
    }
    public static User of(
            String userPw, Long userSsn1, Long userSsn2, String userEmail,
            String userName
    ) {
        return new User(
                userPw,userSsn1,userSsn2, userEmail, userName
        );
    }

}