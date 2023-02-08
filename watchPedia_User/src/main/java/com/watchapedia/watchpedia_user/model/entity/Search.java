package com.watchapedia.watchpedia_user.model.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDateTime;

@Entity(name = "tbSearch")
@AllArgsConstructor
@NoArgsConstructor
@Data
public class Search {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long searchIdx;

    @JsonIgnore
    @ManyToOne @JoinColumn(name = "search_user") private User user;

    @Column(length = 50) private String searchContent;
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
    private LocalDateTime searchRegDate;

    private Long searchDuplicate;

    public Search(User user, String searchContent, LocalDateTime searchRegDate, Long searchDuplicate) {
        this.user = user;
        this.searchContent = searchContent;
        this.searchRegDate = searchRegDate;
        this.searchDuplicate = searchDuplicate;
    }

    public Search of(User user, String searchContent, LocalDateTime searchRegDate, Long searchDuplicate){
        return new Search(user, searchContent, searchRegDate,searchDuplicate);
    }
}
