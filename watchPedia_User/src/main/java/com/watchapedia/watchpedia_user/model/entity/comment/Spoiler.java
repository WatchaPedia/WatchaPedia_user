package com.watchapedia.watchpedia_user.model.entity.comment;

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
    private Long spoCommentIdx;

    public Spoiler(Long spoCommentIdx) {
        this.spoCommentIdx = spoCommentIdx;
    }

    public static Spoiler of(Long commentIdx){
        return new Spoiler(null,commentIdx);
    }
}
