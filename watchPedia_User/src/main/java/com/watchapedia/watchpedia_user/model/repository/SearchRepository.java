package com.watchapedia.watchpedia_user.model.repository;

import com.watchapedia.watchpedia_user.model.entity.Search;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import java.util.List;

@RepositoryRestResource
public interface SearchRepository extends JpaRepository<Search, Long> {

    //본인 계정의 최근 검색어 3개를 가져오는 find문
    List<Search> findTop3ByUser_UserIdxOrderBySearchRegDateDesc(Long idx);

    List<Search> findByUser(Long userIdx);

    //검색어 별로 그룹을 하고 count를 내는 find문
    @Query(value = "select search_idx, search_user, search_content, search_reg_date,count(search_content) as search_duplicate from tb_search group by search_content order by search_duplicate DESC", nativeQuery = true)
    List<Search> findBySearchContent();

    //받아온 String 검색어를 포함하는 검색어 8개
    @Query(value = "select * from tb_search group by search_content", nativeQuery = true)
    List<Search> findBySearchContentGroupBy();
}
