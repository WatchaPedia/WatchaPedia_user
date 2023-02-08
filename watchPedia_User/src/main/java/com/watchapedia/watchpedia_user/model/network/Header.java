package com.watchapedia.watchpedia_user.model.network;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Header<T> {
    private LocalDateTime transactionTime;
    private String resultCode;
    private T data;
    private String description;

    public static <T> Header<T> OK(){
        return (Header<T>)Header.builder().transactionTime(LocalDateTime.now())
                .resultCode("OK").description("정상").build();
    }

    public static <T> Header<T> OK(T data){
        return (Header<T>)Header.builder().transactionTime(LocalDateTime.now())
                .resultCode("OK").description("정상").data(data).build();
    }

    public static <T> Header<T> ERROR(String description){
        return (Header<T>)Header.builder().transactionTime(LocalDateTime.now())
                .resultCode("ERROR").description(description).build();
    }

}
