package com.watchapedia.watchpedia_user.model.entity.comment;

import com.watchapedia.watchpedia_user.model.entity.User;
import com.watchapedia.watchpedia_user.model.entity.auditing.CommentRegDate;
import jakarta.persistence.*;
import lombok.*;

@Builder
@Data
@Entity(name = "tb_comment")
@AllArgsConstructor
public class Comment extends CommentRegDate {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long commIdx;
    @ManyToOne(targetEntity = User.class, fetch = FetchType.LAZY)
    @JoinColumn(name = "comm_user_idx")
    private User commUserIdx;
    private String commName;
    private String commText;
    private String commContentType;
    private Long commContentIdx;

    protected Comment(){}

    public Comment(User commUserIdx,String commName,String commText
            ,String commContentType, Long commContentIdx){
        this.commUserIdx = commUserIdx;
        this.commName = commName;
        this.commText = commText;
        this.commContentType = commContentType;
        this.commContentIdx = commContentIdx;
    }

    public static Comment of(
            User commUserIdx,String commName,String commText
            ,String commContentType, Long commContentIdx
    ){
        return new Comment(
                commUserIdx,commName,commText,commContentType,commContentIdx
        );
    }
}
