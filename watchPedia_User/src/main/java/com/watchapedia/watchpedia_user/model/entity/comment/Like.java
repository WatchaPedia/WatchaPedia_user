package com.watchapedia.watchpedia_user.model.entity.comment;

import com.watchapedia.watchpedia_user.model.entity.User;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@Data
@Entity(name = "tb_like")
@NoArgsConstructor
@AllArgsConstructor
public class Like {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long likeIdx;
    private Long likeUserIdx;
    private Long likeCommentIdx;

    public Like(Long likeUserIdx, Long likeCommentIdx) {
        this.likeUserIdx = likeUserIdx;
        this.likeCommentIdx = likeCommentIdx;
    }
    public static Like of(Long user, Long comment){
        return new Like(user,comment);
    }
}
