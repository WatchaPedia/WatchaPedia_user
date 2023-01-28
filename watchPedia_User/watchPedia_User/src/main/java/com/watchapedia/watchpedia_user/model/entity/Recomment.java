package com.watchapedia.watchpedia_user.model.entity;

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
    @ManyToOne(fetch = FetchType.LAZY, targetEntity = Comment.class)
    @JoinColumn(name = "recomm_comm_idx")
    private Comment recommCommIdx;
    @ManyToOne(targetEntity = User.class, fetch = FetchType.LAZY)
    @JoinColumn(name = "recomm_user_idx")
    private User recommUserIdx;
    private String recommName;
    private String recommText;

    protected Recomment(){}

    public Recomment(Comment recommCommIdx, User recommUserIdx, String recommName, String recommText){
        this.recommCommIdx = recommCommIdx;
        this.recommUserIdx = recommUserIdx;
        this.recommName = recommName;
        this.recommText = recommText;
    }

    public static Recomment of(
            Comment recommCommIdx, User recommUserIdx,String recommName,String recommText
    ){
        return new Recomment(
                recommCommIdx, recommUserIdx,recommName,recommText
        );
    }
}
