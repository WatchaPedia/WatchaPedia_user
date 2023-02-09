package com.watchapedia.watchpedia_user.model.network.response;

import com.watchapedia.watchpedia_user.model.dto.UserDto;
import com.watchapedia.watchpedia_user.model.entity.User;

public record UserResponse(
        Long userIdx,
        String name,
        int starMov,
        int starTv,
        int starBook,
        int starWeb,
        int wishMov,
        int wishTv,
        int wishBook,
        int wishWeb

) {
    public static UserResponse myPageFrom(UserDto dto, int starMov, int starTv, int starBook, int starWeb,
                                     int wishMov, int wishTv, int wishBook, int wishWeb){
        return new UserResponse(
                dto.userIdx(), dto.userName(),starMov,starTv,starBook,starWeb,wishMov,wishTv,wishBook,wishWeb
        );
    }
}
