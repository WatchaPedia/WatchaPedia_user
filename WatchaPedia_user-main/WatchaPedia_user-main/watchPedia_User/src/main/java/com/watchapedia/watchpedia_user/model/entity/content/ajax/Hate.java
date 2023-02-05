package com.watchapedia.watchpedia_user.model.entity.content.ajax;

import com.watchapedia.watchpedia_user.model.entity.User;
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
    private Long hateUserIdx;
    private String hateContentType;
    private Long hateContentIdx;

    public Hate(Long hateUserIdx, String hateContentType, Long hateContentIdx) {
        this.hateUserIdx = hateUserIdx;
        this.hateContentType = hateContentType;
        this.hateContentIdx = hateContentIdx;
    }

    public static Hate of(
            Long user, String contentType, Long contentIdx
    ){
        return new Hate(user,contentType,contentIdx);
    }
}
