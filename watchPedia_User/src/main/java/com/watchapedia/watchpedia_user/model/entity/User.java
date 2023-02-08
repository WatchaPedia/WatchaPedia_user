package com.watchapedia.watchpedia_user.model.entity;

import com.watchapedia.watchpedia_user.config.PasswordConverter;
import com.watchapedia.watchpedia_user.model.dto.UserDto;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity(name = "tbUser")
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Data
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long userIdx;
    @Convert(converter = PasswordConverter.class)
    private String userPw;
    private Long userSsn1;
    private Long userSsn2;
    @Column(length =100)
    private String userEmail;
    @Column(length =50)
    private String userStatus;
    private Long userCautionCnt;
    private Long userWarningCnt;
    private Long userSuspensionCnt;
    private LocalDateTime userLatelyStop;
    private LocalDateTime userReleaseDate;
    @Column(length =30)
    private String userType;
    @Column(length =50)
    private String userName;
    @Column(length =100)
    private String userLikeActor;
    @Column(length =100)
    private String userLikeDirector;
    @Column(length =100)
    private String userLikeGenre;


    @ToString.Exclude
    @OrderBy("searchIdx ASC")
    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
    private final List<Search> searchList = new ArrayList<>();

    public static User of(UserDto userDto) {
        return new User(
                userDto.userIdx(),
                userDto.userPw(),
                userDto.userSsn1(),
                userDto.userSsn2(),
                userDto.userEmail(),
                userDto.userStatus(),
                userDto.userCautionCnt(),
                userDto.userWarningCnt(),
                userDto.userSuspensionCnt(),
                userDto.userLatelyStop(),
                userDto.userReleaseDate(),
                userDto.userType(),
                userDto.userName(),
                userDto.userLikeActor(),
                userDto.userLikeDirector(),
                userDto.userLikeGenre());
    }
}