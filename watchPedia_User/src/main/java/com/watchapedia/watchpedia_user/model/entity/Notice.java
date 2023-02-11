package com.watchapedia.watchpedia_user.model.entity;

import com.watchapedia.watchpedia_user.model.entity.auditing.Auditable;
import com.watchapedia.watchpedia_user.model.entity.auditing.BaseEntity;
import jakarta.persistence.*;
import lombok.*;

@Entity(name="tbNotice")
@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
public class Notice extends BaseEntity implements Auditable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long ntcIdx;
    private String ntcTitle;
    private String ntcText;
    private String ntcRegBy;
    private String ntcStatus;
    private String ntcImagepath;
    private String ntcBtnColor;
    private String ntcBtnText;
    private String ntcBtnLink;
}