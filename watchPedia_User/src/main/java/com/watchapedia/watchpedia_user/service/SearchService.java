package com.watchapedia.watchpedia_user.service;

import com.watchapedia.watchpedia_user.model.dto.content.MovieDto;
import com.watchapedia.watchpedia_user.model.entity.Person;
import com.watchapedia.watchpedia_user.model.entity.Search;
import com.watchapedia.watchpedia_user.model.entity.content.Movie;
import com.watchapedia.watchpedia_user.model.entity.content.ajax.Star;
import com.watchapedia.watchpedia_user.model.network.response.PersonAppear;
import com.watchapedia.watchpedia_user.model.repository.SearchRepository;
import com.watchapedia.watchpedia_user.model.repository.content.BookRepository;
import com.watchapedia.watchpedia_user.model.repository.content.MovieRepository;
import com.watchapedia.watchpedia_user.model.repository.content.TvRepository;
import com.watchapedia.watchpedia_user.model.repository.content.WebtoonRepository;
import com.watchapedia.watchpedia_user.model.repository.content.ajax.StarRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.ui.ModelMap;

import java.util.*;

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

    // 검색어 레퍼지터리는 이미 있음
    // 영화 레퍼지터리 선언
    private final MovieRepository movieRepository;
    private final TvRepository tvRepository;

    //검색순위 높은 영화 컨텐츠
    public List<MovieDto> searchTop10Movie(){
        //검색 중복수 많은 순으로 검색어 테이블 전체를 받아옴
        List<Search> searchDesc = searchRepository.findBySearchContent();

        //영화 all 가져오기
        List<Movie> movies = movieRepository.findAll();

        Map<Long, Long> map = new HashMap<>();


        for(Movie m : movies){
            for(Search s : searchDesc){
                if(m.getMovTitle().equals(s.getSearchContent())){
                    map.put(m.getMovIdx(), s.getSearchDuplicate());
                }
            }
        }

        List<Map.Entry<Long, Long>> entryList = new LinkedList<>(map.entrySet());
        Comparator<Map.Entry<Long,Long>> cmp= Map.Entry.comparingByValue();
        entryList.sort(cmp.reversed());
        // key : 영화idx , value : 검색중첩수
        System.out.println(" key : 영화idx , value : 검색중첩수 " + entryList);




        // * key 값인 영화idx를 뒤에서부터 10개 한정으로 담기

        //검색순위 높은 영화 10개의 idx 담는 List
        List<Long> top10movieIdxList = new ArrayList<>();

        for(Map.Entry<Long, Long> me : entryList){
            if(top10movieIdxList.size() >= 10);
            top10movieIdxList.add(me.getKey());
        }

        List<MovieDto> searchTop10 = new ArrayList<>();

        for(Long l : top10movieIdxList){
            for(Movie m : movies){
                if(l == m.getMovIdx()){
                    searchTop10.add(MovieDto.from(m));
                }
            }
        }

//        System.out.println("검색순위가 높은 영화 : " + searchTop10);

        return searchTop10;
    }

    private final BookRepository bookRepository;
    private final WebtoonRepository webtoonRepository;

    //Person을 받아서 출연작 List<String>을 리턴
    public List<String> getListTitle(Person person){

        List<String> data = new ArrayList<>();


        if(person.getPerMov() != null){
            //2개 이상일 때,
            if(person.getPerMov().contains(",")){
                for(String s : person.getPerMov().split(",")){
                    String str = movieRepository.getReferenceById(Long.valueOf(s)).getMovTitle();
                    data.add(str);
                }
            }else{
                String str = movieRepository.getReferenceById(Long.valueOf(person.getPerMov())).getMovTitle();
                data.add(str);
            }
        }

        if(person.getPerTv() != null){
            //2개 이상일 때,
            if(person.getPerTv().contains(",")){
                for(String s : person.getPerTv().split(",")){
                    String str = tvRepository.getReferenceById(Long.valueOf(s)).getTvTitle();
                    data.add(str);
                }
            }else{
                String str = tvRepository.getReferenceById(Long.valueOf(person.getPerTv())).getTvTitle();
                data.add(str);
            }
        }

        if(person.getPerBook() != null){
            //2개 이상일 때,
            if(person.getPerBook().contains(",")){
                for(String s : person.getPerBook().split(",")){
                    String str = bookRepository.getReferenceById(Long.valueOf(s)).getBookTitle();
                    data.add(str);
                }
            }else{
                String str = bookRepository.getReferenceById(Long.valueOf(person.getPerBook())).getBookTitle();
                data.add(str);
            }
        }

        if(person.getPerWebtoon() != null){
            //2개 이상일 때,
            if(person.getPerWebtoon().contains(",")){
                for(String s : person.getPerWebtoon().split(",")){
                    String str = webtoonRepository.getReferenceById(Long.valueOf(s)).getWebTitle();
                    data.add(str);
                }
            }else{
                String str = webtoonRepository.getReferenceById(Long.valueOf(person.getPerWebtoon())).getWebTitle();
                data.add(str);
            }
        }

        while(data.size() > 2){
            data.remove(data.size() -1);
        }
        return data;
    }



}
