package com.watchapedia.watchpedia_user.model.network.request;

import com.watchapedia.watchpedia_user.model.dto.UserDto;

public record UserRequestDto(
        String userPw,
        Long userSsn1,
        Long userSsn2,
        String userEmail,
        String userName
) {


    public static UserRequestDto userRegist(
                                            String userPw,
                                            Long userSsn1,
                                            Long userSsn2,
                                            String userEmail,
                                            String userName){
        return new UserRequestDto(
                userPw,
                userSsn1,
                userSsn2,
                userEmail,
                userName);
    }


    public static UserRequestDto of(
            String userPw,
            Long userSsn1,
            Long userSsn2,
            String userEmail,
            String userName){
        return new UserRequestDto(
                userPw,
                userSsn1,
                userSsn2,
                userEmail,
                userName);
    }

    public static UserRequestDto from(UserDto dto){
        return new UserRequestDto(
                dto.userPw(),
                dto.userSsn1(),
                dto.userSsn2(),
                dto.userEmail(),
                dto.userName());
    }
}
