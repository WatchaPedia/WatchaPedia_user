package com.watcha.watchapedia.service;

import com.watcha.watchapedia.model.entity.Webtoon;
import com.watcha.watchapedia.model.repository.WebtoonRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
@Slf4j
@Transactional
@Service
@RequiredArgsConstructor
public class WebtoonService {

    final WebtoonRepository webtoonRepository;


    @Transactional(readOnly = true) //데이터를 불러오기만 할 때(수정X)
    public List<Webtoon> searchWebtoons() {
        return webtoonRepository.findAll();
    }
}
