package com.watchapedia.watchpedia_user.model.entity.comment;

import com.watchapedia.watchpedia_user.model.entity.User;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity(name = "tb_recomment_like")
public class Relike {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long relikeIdx;
    private Long relikeRecommIdx;
    private Long relikeUserIdx;

    public Relike(Long relikeRecommIdx, Long relikeUserIdx) {
        this.relikeRecommIdx = relikeRecommIdx;
        this.relikeUserIdx = relikeUserIdx;
    }

    public static Relike of(Long relikeRecommIdx, Long relikeUserIdx){
        return new Relike(relikeRecommIdx,relikeUserIdx);
    }
}
