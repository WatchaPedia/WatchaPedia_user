package com.watchapedia.watchpedia_user.service;

import com.watchapedia.watchpedia_user.model.dto.HateDto;
import com.watchapedia.watchpedia_user.model.entity.Hate;
import com.watchapedia.watchpedia_user.model.entity.User;
import com.watchapedia.watchpedia_user.model.network.request.HateRequest;
import com.watchapedia.watchpedia_user.model.repository.HateRepository;
import com.watchapedia.watchpedia_user.model.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class HateService {
    final HateRepository hateRepository;
    final UserRepository userRepository;
    public boolean hateSave(HateRequest request){
        User user = userRepository.getReferenceById(request.userIdx());
        Hate check = hateRepository.findByHateUserIdxAndHateContentTypeAndHateContentIdx(
                user, request.contentType(), request.contentIdx()
        );
        if(check == null){
            Hate hate = HateDto.of(user, request.contentType(), request.contentIdx()).toEntity();
            hateRepository.save(hate);
            return true;
        }else{
            hateDelete(check);
            return false;
        }
    }

    public void hateDelete(Hate hate){
        hateRepository.delete(hate);
    }

    public boolean findHate(User user, String contentType, Long contentIdx){
        Hate hate = hateRepository.findByHateUserIdxAndHateContentTypeAndHateContentIdx(user,contentType,contentIdx);
        if(hate != null) return true;
        else return false;
    }
}
