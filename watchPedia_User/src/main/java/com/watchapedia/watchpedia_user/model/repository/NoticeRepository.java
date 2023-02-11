package com.watchapedia.watchpedia_user.model.repository;

import com.watchapedia.watchpedia_user.model.entity.Notice;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface NoticeRepository extends JpaRepository<Notice,Long> {
    List<Notice> findByNtcStatusOrderByNtcIdxDesc(String status);
}
