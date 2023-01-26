package com.watcha.watchapedia.service;

import com.watcha.watchapedia.model.entity.Tv;
import com.watcha.watchapedia.model.repository.TvRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Slf4j
@Transactional
@Service
@RequiredArgsConstructor
public class TvService {

    final TvRepository tvRepository;

    @Transactional(readOnly = true) //데이터를 불러오기만 할 때(수정X)
    public List<Tv> searchTvs() {
        return tvRepository.findAll();
    }

}