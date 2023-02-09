package com.watchapedia.watchpedia_user.config;

import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;

import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

@Converter
public class PasswordConverter implements AttributeConverter<String, String> {

    //메시지 다이제스트(Message Digest)란, 메시지를 해시(Hash)하는 것을 의미한다.
    //임의의 길이를 가진 메시지(여기서는 사용자 패스워드)를 MD함수에 넣으면 일정한 길이를 가진 데이터를 얻는다.
    //이 데이터를 비교해 위/변조 되었는지 쉽게 알 수 있다.
    //MessageDigest 클래스에는 update() 메소드가 있는데, 이 메소드를 호출할때마다 객체 내에 저장된 digest 값이 계속해서 갱신이된다.
    //최종적으로 digest() 메서드를 호출하면 그 값을 가져올 수 있다.
    public static String encode(String password){
        try{
            MessageDigest md = MessageDigest.getInstance("SHA-256"); //sha-256 메시지 다이제스트 오브젝트를 작성
            md.update(password.getBytes());
            return String.format("%0128x", new BigInteger(1, md.digest()));
        }catch(NoSuchAlgorithmException e){
            throw new RuntimeException(e.getMessage());
        }
    }

    @Override
    public String convertToDatabaseColumn(String raw) {
        return encode(raw);
    }

    @Override
    public String convertToEntityAttribute(String encoded) {
        return encoded;
    }
}