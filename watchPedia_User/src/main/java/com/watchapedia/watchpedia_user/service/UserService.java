package com.watchapedia.watchpedia_user.service;

import com.watchapedia.watchpedia_user.model.entity.User;
import com.watchapedia.watchpedia_user.model.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserService {

    final UserRepository userRepository;
    public User findUser(Long userIdx){
        return userRepository.getReferenceById(userIdx);
    }
}
