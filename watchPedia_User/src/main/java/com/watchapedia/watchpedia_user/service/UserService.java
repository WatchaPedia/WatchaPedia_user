package com.watchapedia.watchpedia_user.service;

import com.watchapedia.watchpedia_user.model.dto.UserDto;
import com.watchapedia.watchpedia_user.model.dto.content.WebtoonDto;
import com.watchapedia.watchpedia_user.model.entity.User;
import com.watchapedia.watchpedia_user.model.network.request.UserRequestDto;
import com.watchapedia.watchpedia_user.model.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;

    public User findUser(Long userIdx){
        return userRepository.getReferenceById(userIdx);
    }

    public User findEmail(String userEmail){
     return userRepository.findByUserEmail(userEmail);
    }

    public UserRequestDto login(UserRequestDto userRequestDto){
        Optional<User> byUserEmail = userRepository.findByUserEmailAndUserStatus(userRequestDto.userEmail(), "활동");
        if (byUserEmail.isPresent()) {
            // 조회 결과가 있다(해당 이메일을 가진 회원 정보가 있다)
            User user = byUserEmail.get();
            if (user.getUserPw().equals(userRequestDto.userPw())) {
                // 비밀번호 일치
                // entity -> dto -> request 변환 후 리턴
                UserRequestDto dto = UserRequestDto.from(UserDto.from(user));
                return dto;
            } else {
                // 비밀번호 불일치(로그인실패)
                return null;
            }
        } else {
            // 조회 결과가 없다(해당 이메일을 가진 회원이 없다)
            return null;
        }

    }

    public String save(UserRequestDto userRequestDto) {
        UserDto userDto = UserDto.requestReceive(userRequestDto);
        User user = User.of(userDto);
        User userEmail_check = this.userRepository.findByUserEmail(userRequestDto.userEmail());
        if (userEmail_check != null) {
            return "USEREMAILALREADYEXIST";
        } else {
            User userSsn_check = this.userRepository.findByUserSsn1AndUserSsn2(userRequestDto.userSsn1(), userRequestDto.userSsn2());
            if (userSsn_check != null) {
                return "USEREMSSNALREADYEXIST";
            } else {
                this.userRepository.save(user);
                return "SUCCESS";
            }
        }
    }

}
