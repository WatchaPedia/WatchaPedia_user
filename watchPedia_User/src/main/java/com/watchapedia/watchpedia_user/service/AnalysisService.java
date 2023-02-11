package com.watchapedia.watchpedia_user.service;

import com.watchapedia.watchpedia_user.model.repository.content.ajax.StarRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class AnalysisService {
    private final StarRepository starRepository;

    public Map<String,Integer> starSum(Long userIdx){
        int movieSum = starRepository.findByStarContentTypeAndStarUserIdx("movie",userIdx).size();
        int tvSum = starRepository.findByStarContentTypeAndStarUserIdx("tv",userIdx).size();
        int bookSum = starRepository.findByStarContentTypeAndStarUserIdx("book",userIdx).size();
        int webSum = starRepository.findByStarContentTypeAndStarUserIdx("webtoon",userIdx).size();
        Map<String,Integer> map = new HashMap<>();
        map.put("movie",movieSum);
        map.put("tv",tvSum);
        map.put("book",bookSum);
        map.put("webtoon",webSum);
        return map;
    }
}
