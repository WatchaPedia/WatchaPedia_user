package com.watchapedia.watchpedia_user.model.repository;

import com.watchapedia.watchpedia_user.model.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository  extends JpaRepository<User, Long> {
}
