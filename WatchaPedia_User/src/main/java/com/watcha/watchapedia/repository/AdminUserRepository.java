package com.watcha.watchapedia.repository;

import com.watcha.watchapedia.model.entity.AdminUser;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface AdminUserRepository extends JpaRepository<AdminUser, Long> {

}
