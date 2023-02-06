package com.watchapedia.watchpedia_user.model.repository;

import com.watchapedia.watchpedia_user.model.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository  extends JpaRepository<User, Long> {
    User findByUserEmail(String userEmail);

    User findByUserSsn1AndUserSsn2(long userSsn1, long userSsn2);

    Optional<User> findByUserEmailAndUserStatusAndUserPw(String userEmail, String userStatus, String userPw);
}
