package com.watchapedia.watchpedia_user.model.dto;


import com.watchapedia.watchpedia_user.model.entity.Search;
import com.watchapedia.watchpedia_user.model.entity.User;
import com.watchapedia.watchpedia_user.model.network.request.UserRequestDto;

import java.time.LocalDateTime;
import java.util.List;

public record UserDto(
    Long userIdx,
    String userPw,
    Long userSsn1,
    Long userSsn2,
    String userEmail,
    String userStatus,
    Long userCautionCnt,
    Long userWarningCnt,
    Long userSuspensionCnt,
    LocalDateTime userLatelyStop,
    LocalDateTime userReleaseDate,
    String userType,
    String userName,
    String userLikeActor,
    String userLikeDirector,
    String userLikeGenre,
    List<Search> searchList
){
    public static UserDto of(
            Long userIdx, String userPw, Long userSsn1, Long userSsn2,
            String userEmail, String userStatus, Long userCautionCnt, Long userWarningCnt,
            Long userSuspensionCnt, LocalDateTime userLatelyStop, LocalDateTime userReleaseDate,
            String userType, String userName, String userLikeActor, String userLikeDirector,
            String userLikeGenre,List<Search> searchList
    ){
        return new UserDto(
                userIdx, userPw, userSsn1, userSsn2, userEmail, userStatus,
                userCautionCnt, userWarningCnt, userSuspensionCnt, userLatelyStop,
                userReleaseDate, userType, userName, userLikeActor, userLikeDirector,
                userLikeGenre,searchList
        );
    }

    public static UserDto of(
            Long userIdx, String userPw, Long userSsn1, Long userSsn2,
            String userEmail, String userStatus, Long userCautionCnt, Long userWarningCnt,
            Long userSuspensionCnt, LocalDateTime userLatelyStop, LocalDateTime userReleaseDate,
            String userType, String userName, String userLikeActor, String userLikeDirector,
            String userLikeGenre
    ){
        return new UserDto(
                userIdx, userPw, userSsn1, userSsn2, userEmail, userStatus,
                userCautionCnt, userWarningCnt, userSuspensionCnt, userLatelyStop,
                userReleaseDate, userType, userName, userLikeActor, userLikeDirector,
                userLikeGenre, null
        );
    }

    public static UserDto of(
            String userPw, Long userSsn1, Long userSsn2,
            String userEmail, String userName
    ){
        return new UserDto(
                null, userPw, userSsn1, userSsn2, userEmail, null,
                null, null, null, null, null, null, userName, null, null,
                null, null
        );
    }

    public static UserDto from(User entity){
        return new UserDto(
                entity.getUserIdx(),
                entity.getUserPw(),
                entity.getUserSsn1(),
                entity.getUserSsn2(),
                entity.getUserEmail(),
                entity.getUserStatus(),
                entity.getUserCautionCnt(),
                entity.getUserWarningCnt(),
                entity.getUserSuspensionCnt(),
                entity.getUserLatelyStop(),
                entity.getUserReleaseDate(),
                entity.getUserType(),
                entity.getUserName(),
                entity.getUserLikeActor(),
                entity.getUserLikeDirector(),
                entity.getUserLikeGenre(),
                entity.getSearchList()
        );
    }
    public static UserDto requestReceive(UserRequestDto userRequestdto){
        return new UserDto(
                null,
                userRequestdto.userPw(),
                userRequestdto.userSsn1(),
                userRequestdto.userSsn2(),
                userRequestdto.userEmail(),
                "활동",
                0L,
                0L,
                0L,
                null,
                null,
                "일반유저",
                userRequestdto.userName(),
                null,
                null,
                null,
                null
        );
    }

//    public User toEntity(){
//        return User.of(
//                userId, userPw, userSsn1, userSsn2,
//                userEmail, userName
//        );
//    }
}
