package com.watchapedia.watchpedia_user.service.content.ajax;

import com.watchapedia.watchpedia_user.model.dto.content.ajax.WishDto;
import com.watchapedia.watchpedia_user.model.entity.content.ajax.Watch;
import com.watchapedia.watchpedia_user.model.entity.content.ajax.Wish;
import com.watchapedia.watchpedia_user.model.network.request.ajax.WishRequest;
import com.watchapedia.watchpedia_user.model.repository.UserRepository;
import com.watchapedia.watchpedia_user.model.repository.content.ajax.WatchRepository;
import com.watchapedia.watchpedia_user.model.repository.content.ajax.WishRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class WishService {
    final WishRepository wishRepository;
    final WatchRepository watchRepository;
    final UserRepository userRepository;

    public boolean wishSave(WishRequest request){
        Wish wish = wishRepository.findByWishContentTypeAndWishContentIdxAndWishUserIdx(
                request.contentType(), request.contentIdx(), request.userIdx()
        );
        Watch watch = watchRepository.findByWatchContentTypeAndWatchContentIdxAndWatchUserIdx(
                request.contentType(), request.contentIdx(),request.userIdx()
        );
        if(wish == null){
            if(watch != null){
                watchRepository.delete(watch);
            }
            WishDto dto = WishDto.of(request.contentType(), request.contentIdx(), request.userIdx());
            wishRepository.save(Wish.of(dto.contentType(),dto.contentIdx(),dto.userIdx()));
            return true;
        }else{
            wishDelete(wish);
            return false;
        }
    }
    public void wishDelete(Wish wish){
        wishRepository.delete(wish);
    }

    public boolean findWish(String contentType, Long contentIdx, Long userIdx){
        Wish wish = wishRepository.findByWishContentTypeAndWishContentIdxAndWishUserIdx(
                contentType,contentIdx,userIdx
        );
        if(wish != null){
            return true;
        }
        return false;
    }
}
