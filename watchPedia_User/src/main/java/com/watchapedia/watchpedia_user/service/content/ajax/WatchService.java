package com.watchapedia.watchpedia_user.service.content.ajax;

import com.watchapedia.watchpedia_user.model.dto.content.ajax.WatchDto;
import com.watchapedia.watchpedia_user.model.entity.content.ajax.Watch;
import com.watchapedia.watchpedia_user.model.entity.content.ajax.Wish;
import com.watchapedia.watchpedia_user.model.network.request.ajax.WatchRequest;
import com.watchapedia.watchpedia_user.model.repository.UserRepository;
import com.watchapedia.watchpedia_user.model.repository.content.ajax.WatchRepository;
import com.watchapedia.watchpedia_user.model.repository.content.ajax.WishRepository;
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
                contentType,contentIdx,userIdx
        );
        if(watch != null) return true;
        return false;
    }

    public boolean watchSave(WatchRequest request){
        Wish wish = wishRepository.findByWishContentTypeAndWishContentIdxAndWishUserIdx(
                request.contentType(), request.contentIdx(), request.userIdx()
        );
        Watch watch = watchRepository.findByWatchContentTypeAndWatchContentIdxAndWatchUserIdx(
                request.contentType(), request.contentIdx(),request.userIdx()
        );
        if(watch == null){
            if(wish != null){
                wishRepository.delete(wish);
            }
            WatchDto dto = WatchDto.of(request.userIdx(), request.contentType(), request.contentIdx());
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
