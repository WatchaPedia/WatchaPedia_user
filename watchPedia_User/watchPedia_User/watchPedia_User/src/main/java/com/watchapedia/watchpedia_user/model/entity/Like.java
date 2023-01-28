package com.watchapedia.watchpedia_user.model.entity;

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
    @ManyToOne(fetch = FetchType.LAZY, targetEntity = User.class)
    @JoinColumn(name="like_user_idx")
    private User likeUserIdx;
    @ManyToOne(fetch = FetchType.LAZY, targetEntity = Comment.class)
    @JoinColumn(name="like_comment_idx")
    private Comment likeCommentIdx;

    public Like(User likeUserIdx, Comment likeCommentIdx) {
        this.likeUserIdx = likeUserIdx;
        this.likeCommentIdx = likeCommentIdx;
    }
    public static Like of(User user, Comment comment){
        return new Like(user,comment);
    }
}
