package com.watchapedia.watchpedia_user.model.entity.content.ajax;

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
@Entity(name = "tb_watch")
public class Watch {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long watchIdx;
    private Long watchUserIdx;
    private String watchContentType;
    private Long watchContentIdx;

    public Watch(Long watchUserIdx, String watchContentType, Long watchContentIdx) {
        this.watchUserIdx = watchUserIdx;
        this.watchContentType = watchContentType;
        this.watchContentIdx = watchContentIdx;
    }

    public static Watch of(Long user, String contentType, Long contentIdx){
        return new Watch(user,contentType,contentIdx);
    }
}
