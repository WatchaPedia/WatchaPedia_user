package com.watchapedia.watchpedia_user.model.entity.auditing;

import java.time.LocalDateTime;

public interface Auditable {
    LocalDateTime getRegDate();
    LocalDateTime getUpdateDate();

    void setRegDate(LocalDateTime regDate);
    void setUpdateDate(LocalDateTime updateDate);
}