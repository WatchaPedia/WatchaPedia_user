package com.watchapedia.watchpedia_user.service;

import com.watchapedia.watchpedia_user.model.dto.WatchDto;
import com.watchapedia.watchpedia_user.model.dto.WishDto;
import com.watchapedia.watchpedia_user.model.entity.Watch;
import com.watchapedia.watchpedia_user.model.entity.Wish;
import com.watchapedia.watchpedia_user.model.network.request.WatchRequest;
import com.watchapedia.watchpedia_user.model.network.request.WishRequest;
import com.watchapedia.watchpedia_user.model.repository.UserRepository;
import com.watchapedia.watchpedia_user.model.repository.WatchRepository;
import com.watchapedia.watchpedia_user.model.repository.WishRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class WatchService {
    final WatchRepository watchRepository;
    final WishRepository wishRepository;
    final UserRepository userRepository;
    public boolean findWatch(String contentType, Long contentIdx, Long userIdx){
        Watch watch = watchRepository.findByWatchContentTypeAndWatchContentIdxAndWatchUserIdx(
                contentType,contentIdx,userRepository.getReferenceById(userIdx)
        );
        if(watch != null) return true;
        return false;
    }

    public boolean watchSave(WatchRequest request){
        Wish wish = wishRepository.findByWishContentTypeAndWishContentIdxAndWishUserIdx(
                request.contentType(), request.contentIdx(), userRepository.findById(request.userIdx()).get()
        );
        Watch watch = watchRepository.findByWatchContentTypeAndWatchContentIdxAndWatchUserIdx(
                request.contentType(), request.contentIdx(),userRepository.findById(request.userIdx()).get()
        );
        if(watch == null){
            if(wish != null){
                wishRepository.delete(wish);
            }
            WatchDto dto = WatchDto.of(userRepository.findById(request.userIdx()).get(), request.contentType(), request.contentIdx());
            watchRepository.save(Watch.of(dto.user(), dto.contentType(),dto.contentIdx()));
            return true;
        }else{
            watchDelete(watch);
            return false;
        }
    }
    public void watchDelete(Watch watch){
        watchRepository.delete(watch);
    }
}
