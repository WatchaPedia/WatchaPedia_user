package com.watcha.watchapedia.model.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity(name = "tbAdminUser")
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Data
public class AdminUser {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long adminIdx;
    @Column(length =20)
    private String adminName;
    @Column(length =20)
    private String adminId;
    @Column(length =256)
    private String adminPw;
    @Column(length =20)
    private String adminNumber;
    @Column(length =10)
    private String adminType;
    private LocalDateTime adminRegDate;

}