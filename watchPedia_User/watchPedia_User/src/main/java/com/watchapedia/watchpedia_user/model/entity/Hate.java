package com.watchapedia.watchpedia_user.model.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@Data
@Entity(name = "tb_hate")
@AllArgsConstructor
@NoArgsConstructor
public class Hate {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long hateIdx;
    @ManyToOne(fetch = FetchType.LAZY, targetEntity = User.class)
    @JoinColumn(name = "hate_user_idx")
    private User hateUserIdx;
    private String hateContentType;
    private Long hateContentIdx;

    public Hate(User hateUserIdx, String hateContentType, Long hateContentIdx) {
        this.hateUserIdx = hateUserIdx;
        this.hateContentType = hateContentType;
        this.hateContentIdx = hateContentIdx;
    }

    public static Hate of(
            User user, String contentType, Long contentIdx
    ){
        return new Hate(user,contentType,contentIdx);
    }
}
