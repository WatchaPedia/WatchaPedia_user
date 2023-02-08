package com.watchapedia.watchpedia_user.model.dto;

import com.watchapedia.watchpedia_user.model.entity.Search;
import com.watchapedia.watchpedia_user.model.entity.User;

import java.time.LocalDateTime;

public record SearchDto(
        Long searchIdx,
        User user,
        String searchContent,
        LocalDateTime searchRegDate,
        Long searchDuplicate
) {
    //기본 Full of
    public static SearchDto of(Long searchIdx, User user, String searchContent, Long searchDuplicate){
        return new SearchDto(
                searchIdx,
                user,
                searchContent,
                LocalDateTime.now(),
                searchDuplicate
        );
    }

    //id를 제외한 of (검색어 create용)
    public static SearchDto create(User user, String searchContent){
        return new SearchDto(
                null,
                user,
                searchContent,
                LocalDateTime.now(),
                1L
        );
    }

    public static SearchDto from(Search search){
        return new SearchDto(
                search.getSearchIdx(),
                search.getUser(),
                search.getSearchContent(),
                search.getSearchRegDate(),
                search.getSearchDuplicate()
        );
    }

    public Search toEntity(SearchDto dto){
        return new Search(
          dto.user(),
                dto.searchContent(),
                dto.searchRegDate(),
                dto.searchDuplicate()
        );
    }

}
