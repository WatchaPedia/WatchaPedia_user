package com.watchapedia.watchpedia_user.model.entity.comment;

import com.watchapedia.watchpedia_user.model.entity.User;
import com.watchapedia.watchpedia_user.model.entity.auditing.RecommentRegDate;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Builder
@Data
@Entity(name = "tb_recomment")
@AllArgsConstructor
public class Recomment extends RecommentRegDate {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long recommIdx;
    private Long recommCommIdx;
    @ManyToOne(targetEntity = User.class, fetch = FetchType.LAZY)
    @JoinColumn(name = "recomm_user_idx")
    private User recommUserIdx;
    private String recommName;
    private String recommText;

    protected Recomment(){}

    public Recomment(Long recommCommIdx, User recommUserIdx, String recommName, String recommText){
        this.recommCommIdx = recommCommIdx;
        this.recommUserIdx = recommUserIdx;
        this.recommName = recommName;
        this.recommText = recommText;
    }

    public static Recomment of(
            Long recommCommIdx, User recommUserIdx,String recommName,String recommText
    ){
        return new Recomment(
                recommCommIdx, recommUserIdx,recommName,recommText
        );
    }
}
