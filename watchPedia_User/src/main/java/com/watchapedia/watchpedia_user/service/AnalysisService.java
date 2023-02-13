package com.watchapedia.watchpedia_user.service;

import com.watchapedia.watchpedia_user.model.entity.Person;
import com.watchapedia.watchpedia_user.model.repository.PersonRepository;
import com.watchapedia.watchpedia_user.model.repository.content.MovieRepository;
import com.watchapedia.watchpedia_user.model.repository.content.ajax.StarRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class AnalysisService {
    private final StarRepository starRepository;
    private final MovieRepository movieRepository;
    private final PersonRepository personRepository;

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

    public List<Map.Entry<String, Integer>> countryCnt(Long userIdx){
        Map<String, Integer> countryMap = new HashMap<>();
        List<Long> movIdxs = starRepository.findStarMovie(userIdx);
        List<String> countryCnt = List.of(movieRepository.findCountryCnt(movIdxs).toString()
                .split("\\[")[1].split("]")[0].split(", "));
        for(String str: countryCnt){
            String trim = str;
            String country = trim.split(",[0-999]")[0];
            String countrySum = str.split(country+",")[1];
            if(country.contains(",")){
                for(String cnty : country.split(",")){
                    if(countryMap.get(cnty)!=null){
                        countryMap.put(cnty, Integer.valueOf(countrySum) + countryMap.get(cnty));
                    }else{
                        countryMap.put(cnty, Integer.valueOf(countrySum));
                    }
                }
            }else{
                countryMap.put(country, Integer.valueOf(countrySum));
            }
        }
        List<Map.Entry<String, Integer>> entryList = new LinkedList<>(countryMap.entrySet());
        entryList.sort(((o1, o2) -> o2.getValue() - o1.getValue()));
        return entryList;
    }

    public List<Map.Entry<String, Integer>> genreCnt(Long userIdx){
        Map<String, Integer> genreMap = new HashMap<>();
        List<Long> movIdxs = starRepository.findStarMovie(userIdx);
        List<String> genreCnt = List.of(movieRepository.findGenreCnt(movIdxs).toString()
                .split("\\[")[1].split("]")[0].split(", "));
        for(String str: genreCnt){
            String trim = str;
            String genre = trim.split(",[0-999]")[0];
            String genreSum = str.split(genre+",")[1];
            if(genre.contains("/")){
                for(String cnty : genre.split("/")){
                    if(genreMap.get(cnty)!=null){
                        genreMap.put(cnty, Integer.valueOf(genreSum) + genreMap.get(cnty));
                    }else{
                        genreMap.put(cnty, Integer.valueOf(genreSum));
                    }
                }
            }else{
                genreMap.put(genre, Integer.valueOf(genreSum));
            }
        }
        List<Map.Entry<String, Integer>> entryList = new LinkedList<>(genreMap.entrySet());
        entryList.sort(((o1, o2) -> o2.getValue() - o1.getValue()));
        return entryList;
    }

    public List<Map.Entry<String, String>> peopleCnt(Long userIdx){
        Map<Long, Integer> peopleMap = new HashMap<>();
        List<Long> movIdxs = starRepository.findStarMovie(userIdx);
        List<String> peopleList = List.of(movieRepository.findPeopleList(movIdxs).toString()
                .split("\\[")[1].split("]")[0].split(", "));
        for(String str: peopleList){
            String[] personList = str.split(",");
            for(String per : personList){
                if(per.contains("주연")){
                    Long personIdx = 0L;
                    try {
                        if (per.contains("(")) {
                            personIdx = Long.valueOf(per.split("\\(")[0]);
                        } else personIdx = Long.valueOf(per);

                        if (peopleMap.get(personIdx) != null) {
                            peopleMap.put(personIdx, 1 + peopleMap.get(personIdx));
                        } else {
                            peopleMap.put(personIdx, 1);
                        }
                    }catch(NumberFormatException e){}
                }
            }
        }
        List<Map.Entry<Long, Integer>> entryList = new LinkedList<>(peopleMap.entrySet());
        entryList.sort(((o1, o2) -> o2.getValue() - o1.getValue()));

        Pageable pageable = PageRequest.of(0, 1);
        Map<String, String> resultMap = new HashMap<String, String>();
        for(int i=0; i<=5; i++){
            try{
                Person person = personRepository.findById(entryList.get(i).getKey()).get();
                resultMap.put((person.getPerPhoto()!=null?person.getPerPhoto()+"|":"")+person.getPerName()+","+person.getPerIdx()
                        ,entryList.get(i).getValue()+","+movieRepository.actorMovie("%,"+person.getPerIdx()+"(주연%",pageable).get(0));
            }catch (IndexOutOfBoundsException e){
                System.out.println("인물 부족");
            }
        }
        List<Map.Entry<String, String>> result = new LinkedList<>(resultMap.entrySet());
        result.sort(((o1, o2) -> Integer.parseInt(o2.getValue().split(",")[0]) - Integer.parseInt(o1.getValue().split(",")[0])));
        return result;
    }

    public int watchTime(Long userIdx){
        int sum = 0;
        List<Long> movIdxs = starRepository.findStarMovie(userIdx);
        List<String> timeList = List.of(movieRepository.timeList(movIdxs).toString()
                .split("\\[")[1].split("]")[0].split(", "));
        for(String time : timeList){
            if(time.contains("시간")){
                int hour = Integer.parseInt(time.split("시간")[0]);
                sum += hour*60;
                if(time.contains("분")){
                    int minute = Integer.parseInt(time.split("시간")[1].split("분")[0].trim());
                    sum += minute;
                }
            }else{
                if(time.contains("분")){
                    int minute = Integer.parseInt(time.split("분")[0]);
                    sum += minute;
                }
            }
        }

        return sum;
    }
}
