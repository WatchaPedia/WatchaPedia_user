package com.watchapedia.watchpedia_user.service.content.ajax;

import com.watchapedia.watchpedia_user.model.dto.content.ajax.HateDto;
import com.watchapedia.watchpedia_user.model.entity.content.ajax.Hate;
import com.watchapedia.watchpedia_user.model.entity.User;
import com.watchapedia.watchpedia_user.model.network.request.ajax.HateRequest;
import com.watchapedia.watchpedia_user.model.repository.content.ajax.HateRepository;
import com.watchapedia.watchpedia_user.model.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class HateService {
    final HateRepository hateRepository;
    final UserRepository userRepository;
    public boolean hateSave(HateRequest request){
        Hate check = hateRepository.findByHateUserIdxAndHateContentTypeAndHateContentIdx(
                request.userIdx(), request.contentType(), request.contentIdx()
        );
        if(check == null){
            Hate hate = HateDto.of(request.userIdx(), request.contentType(), request.contentIdx()).toEntity();
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

    public boolean findHate(Long user, String contentType, Long contentIdx){
        Hate hate = hateRepository.findByHateUserIdxAndHateContentTypeAndHateContentIdx(user,contentType,contentIdx);
        if(hate != null) return true;
        else return false;
    }
}
