package com.watchapedia.watchpedia_user.model.dto;


import com.watchapedia.watchpedia_user.model.entity.User;
import com.watchapedia.watchpedia_user.model.network.request.UserRequestDto;

import java.time.LocalDateTime;

public record UserSessionDto(
    Long userIdx,
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
    String userLikeGenre
){
    public static UserSessionDto of(
            Long userIdx,
            String userEmail, String userStatus, Long userCautionCnt, Long userWarningCnt,
            Long userSuspensionCnt, LocalDateTime userLatelyStop, LocalDateTime userReleaseDate,
            String userType, String userName, String userLikeActor, String userLikeDirector,
            String userLikeGenre
    ){
        return new UserSessionDto(
                userIdx, userEmail, userStatus,
                userCautionCnt, userWarningCnt, userSuspensionCnt, userLatelyStop,
                userReleaseDate, userType, userName, userLikeActor, userLikeDirector,
                userLikeGenre
        );
    }



    public static UserSessionDto from(User entity){
        return new UserSessionDto(
                entity.getUserIdx(),
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
                entity.getUserLikeGenre()
        );
    }
}
