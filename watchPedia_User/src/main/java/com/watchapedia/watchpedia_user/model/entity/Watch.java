package com.watchapedia.watchpedia_user.model.entity;

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
    @ManyToOne(targetEntity = User.class, fetch = FetchType.LAZY)
    @JoinColumn(name = "watch_user_idx")
    private User watchUserIdx;
    private String watchContentType;
    private Long watchContentIdx;

    public Watch(User watchUserIdx, String watchContentType, Long watchContentIdx) {
        this.watchUserIdx = watchUserIdx;
        this.watchContentType = watchContentType;
        this.watchContentIdx = watchContentIdx;
    }

    public static Watch of(User user, String contentType, Long contentIdx){
        return new Watch(user,contentType,contentIdx);
    }
}
