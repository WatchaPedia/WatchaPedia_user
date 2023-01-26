package com.watcha.watchapedia.model.config;

import java.time.LocalDateTime;

public interface Auditable {
    LocalDateTime getRegDate();
    LocalDateTime getUpdateDate();

    void setRegDate(LocalDateTime regDate);
    void setUpdateDate(LocalDateTime updateDate);
}
