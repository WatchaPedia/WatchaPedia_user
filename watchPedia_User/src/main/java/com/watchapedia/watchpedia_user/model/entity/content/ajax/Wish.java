package com.watchapedia.watchpedia_user.model.entity.content.ajax;

import com.watchapedia.watchpedia_user.model.entity.User;
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
    private Long wishUserIdx;
    public Wish(String wishContentType, Long wishContentIdx, Long wishUserIdx) {
        this.wishContentType = wishContentType;
        this.wishContentIdx = wishContentIdx;
        this.wishUserIdx = wishUserIdx;
    }

    public static Wish of(
            String wishContentType, Long wishContentIdx, Long wishUserIdx
    ){
        return new Wish(wishContentType, wishContentIdx, wishUserIdx);
    }
}
