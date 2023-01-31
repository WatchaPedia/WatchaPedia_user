package com.watchapedia.watchpedia_user.model.entity;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import jakarta.persistence.*;
import lombok.*;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity(name = "tb_wish")
@ToString(callSuper = true)
public class Wish {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long wishIdx;
    private String wishContentType;
    private Long wishContentIdx;
    @ManyToOne(targetEntity = User.class, fetch = FetchType.LAZY)
    @JoinColumn(name = "wish_user_idx")
    private User wishUserIdx;
    public Wish(String wishContentType, Long wishContentIdx, User wishUserIdx) {
        this.wishContentType = wishContentType;
        this.wishContentIdx = wishContentIdx;
        this.wishUserIdx = wishUserIdx;
    }

    public static Wish of(
            String wishContentType, Long wishContentIdx, User wishUserIdx
    ){
        return new Wish(wishContentType, wishContentIdx, wishUserIdx);
    }
}
