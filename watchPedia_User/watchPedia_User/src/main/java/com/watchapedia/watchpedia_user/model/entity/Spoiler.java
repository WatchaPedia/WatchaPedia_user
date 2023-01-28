package com.watchapedia.watchpedia_user.model.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@Data
@Entity(name = "tb_spoiler")
@AllArgsConstructor
@NoArgsConstructor
public class Spoiler {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long spoIdx;
    @OneToOne(targetEntity = Comment.class, fetch = FetchType.LAZY)
    @JoinColumn(name = "spo_comment_idx")
    private Comment spoCommentIdx;

    public Spoiler(Comment spoCommentIdx) {
        this.spoCommentIdx = spoCommentIdx;
    }

    public static Spoiler of(Comment commentIdx){
        return new Spoiler(null,commentIdx);
    }
}
