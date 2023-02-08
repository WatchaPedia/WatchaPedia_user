package com.watchapedia.watchpedia_user.service;

import com.watchapedia.watchpedia_user.model.entity.Search;
import com.watchapedia.watchpedia_user.model.repository.SearchRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class SearchService {
    private final SearchRepository searchRepository;

    public List<Search> duplicateUpdate(){
        System.out.println("서비스에 duplicateUpdate 도착!");

        //중첩이 많은 검색어 순으로 List<Search> 반환됨
        List<Search> searchKeyList = searchRepository.findBySearchContent();

        List<Search> top5List = new ArrayList<>();

        for(int i = 0; i < 5; i++){
            top5List.add(searchKeyList.get(i));
        }

        return top5List;

    }


    public List<Search> latestTop3(Long idx){
        //search reg date DESC
        List<Search> searchList = searchRepository.findTop3ByUser_UserIdxOrderBySearchRegDateDesc(idx);
        return searchList;
    }



}
