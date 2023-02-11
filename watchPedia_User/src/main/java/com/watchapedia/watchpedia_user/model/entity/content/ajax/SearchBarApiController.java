package com.watchapedia.watchpedia_user.model.entity.content.ajax;

import com.watchapedia.watchpedia_user.model.dto.SearchDto;
import com.watchapedia.watchpedia_user.model.entity.Search;
import com.watchapedia.watchpedia_user.model.entity.User;
import com.watchapedia.watchpedia_user.model.repository.SearchRepository;
import com.watchapedia.watchpedia_user.model.repository.UserRepository;
import com.watchapedia.watchpedia_user.service.SearchService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("")
@RequiredArgsConstructor
public class SearchBarApiController {

    private final SearchService searchService;
    private final SearchRepository searchRepository;

    @GetMapping("/focusOn")
    public List<List<Search>> focusOn(@RequestParam("userIdx") String userId){

        //로그인 상태에서 '엔터' 검색 기능
        try{
            System.out.println("받아온 RequestBody userId : " + userId);
            Long userIdx = Long.valueOf(userId);
            System.out.println("로그인 상태로 focus 얻었을때, 최근검색어 3개 + 인기검색어만 돌려주면 됨!");

            //최근검색어 3개 가져오는 service
            List<Search> latestTop3 = searchService.latestTop3(userIdx);
            System.out.println(latestTop3);

            //전체 인기검색어 5개 가져오는 service
            List<Search> popularTop5 = searchService.duplicateUpdate();
            System.out.println(popularTop5);

            List<List<Search>> list = new ArrayList<>();
            list.add(latestTop3);
            list.add(popularTop5);

            return list;



        }
        //로그인이 안된 상태에서의 '엔터' 검색 기능
        catch (NumberFormatException e){
            System.out.println("비로그인 상태로 focus 얻었을때, 인기검색어만 돌려주면 됨!");

            //전체 인기검색어 5개 가져오는 service
            List<Search> popularTop5 = searchService.duplicateUpdate();
            System.out.println(popularTop5);

            List<List<Search>> list = new ArrayList<>();
            list.add(null);
            list.add(popularTop5);
            return list;
        }


    }

    @GetMapping("/enter")
    public List<Search> searchEnter(@RequestParam("searchKey") String searchKey, @RequestParam("userIdx") String userId){

        if(searchKey == null) return null;
        System.out.println("api/enter로 잘들어옴!");

        //로그인 상태에서 '엔터' 검색 기능
        try{
            Long userIdx = Long.valueOf(userId);
            System.out.println("apiController에 전달된 2개 매개변수 : " + searchKey + ", " + userIdx);

            //검색어, 검색한 사용자를 담아서 tb_search에 등록!
            User user = userRepository.getReferenceById(userIdx);
            SearchDto searchDto = SearchDto.create(user, searchKey);
            Search search = searchDto.toEntity(searchDto);
            searchRepository.save(search);




        }
        //로그인이 안된 상태에서의 '엔터' 검색 기능
        catch (NumberFormatException e){
            System.out.println("idx가 안넘어온 비로그인 상태로 보여짐!");
            System.out.println("apiController에 전달된 검색어 : " + searchKey);

            //검색어, 사용자명 "비회원"으로 담아서 tb_search에 등록
            // 1. 임시 비회원 User를 만들어냄
            User noneUser = userRepository.getReferenceById(999L);
            SearchDto searchDto = SearchDto.create(noneUser, searchKey);
            Search search = searchDto.toEntity(searchDto);
            searchRepository.save(search);
        }
        return null;
    }
    private final UserRepository userRepository;

    @GetMapping("/change")
    public List<Search> searchChange(@RequestParam("searchKey") String searchKey){

        //검색어를 중복값 없이 전부 가져옴
        List<Search> relative = searchRepository.findBySearchContentGroupBy();

        //검색어가 포함된 것만 골라서 담을 빈 ArrayList 생성
        List<Search> result = new ArrayList<>();

        //검색어가 포함된 것만 골라냄
        for (Search s : relative){
            if(result.size() >= 8) break;
            if(s.getSearchContent().contains(searchKey)){
                result.add(s);
            }
        }
        return result;
    }
}
