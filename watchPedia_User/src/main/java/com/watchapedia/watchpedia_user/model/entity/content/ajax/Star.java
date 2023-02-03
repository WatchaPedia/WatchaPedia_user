package com.watchapedia.watchpedia_user.model.entity.content.ajax;


import com.watchapedia.watchpedia_user.model.entity.User;
import jakarta.persistence.*;
import lombok.*;

@Data
@Builder
@Entity(name = "tb_star")
@AllArgsConstructor
@ToString(callSuper = true)
public class Star {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long starIdx;
    private String starContentType;
    private Long starContentIdx;
    private Long starUserIdx;
    private Long starPoint;

    protected Star(){}

    public Star(String starContentType, Long starContentIdx, Long starUserIdx, Long starPoint) {
        this.starContentType = starContentType;
        this.starContentIdx = starContentIdx;
        this.starUserIdx = starUserIdx;
        this.starPoint = starPoint;
    }

//    public static Star of(Long starIdx, String starContentType, Long starContentIdx, Long starUserIdx, Long starPoint){
//        return new Star(starIdx, starContentType, starContentIdx, starUserIdx, starPoint);
//    }

    public static Star of(String starContentType, Long starContentIdx, Long starUserIdx, Long starPoint){
        return new Star(starContentType, starContentIdx, starUserIdx, starPoint);
    }
}
