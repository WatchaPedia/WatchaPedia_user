package com.watchapedia.watchpedia_user.service;

import com.watchapedia.watchpedia_user.model.dto.PersonDto;
import com.watchapedia.watchpedia_user.model.dto.PersonLikeDto;
import com.watchapedia.watchpedia_user.model.entity.Person;
import com.watchapedia.watchpedia_user.model.entity.PersonLike;
import com.watchapedia.watchpedia_user.model.network.request.UserRequestDto;
import com.watchapedia.watchpedia_user.model.network.response.PersonResponse;
import com.watchapedia.watchpedia_user.model.repository.PersonLikeRepository;
import com.watchapedia.watchpedia_user.model.repository.PersonRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class PersonService {
    final PersonRepository personRepository;
    private final PersonLikeRepository personLikeRepository;


    @Transactional(readOnly = true)
    public List<PersonResponse> personList(List<String> peopleList){
        List<PersonResponse> personList = peopleList.stream().map(
                per->{
                    PersonDto dto = PersonDto.from(personRepository.getReferenceById(Long.valueOf(per.split(",")[0])));
                    return PersonResponse.from(dto, per.split(",")[1]);
                }
        ).toList();
        return personList;
    }

    //사용자-인물페이지--------------------------------------------------------------------------------------------------------
    @Transactional(readOnly = true)
    public PersonResponse personView(Long perIdx){
        PersonDto per = personRepository.findById(perIdx).map(PersonDto::from).get();
        return PersonResponse.fromis(per);
    }

    //인물좋아요갯수
    public Long likePersonCnt(Long perIdx){
        Long cnt = Long.valueOf(personLikeRepository.findByPerIdx(perIdx).size());
        return cnt;
    }

    //인물좋아요
    public void likePerson(Long userIdx,Long perIdx){
        personLikeRepository.save(PersonLikeDto.toEntity(userIdx, perIdx));
    }

    //인물좋아요취소
    public void delLikePerson(Long userIdx,Long perIdx){
        System.out.println("del 서비스 진입 - useridx : "+ userIdx + " perIdx:"+ perIdx );
        PersonLikeDto personLikeDto = personLikeRepository.findByUserIdxAndPerIdx(userIdx, perIdx);
        System.out.println("personLikeDto" + personLikeDto);
        personLikeRepository.delete(PersonLikeDto.toEntity(personLikeDto));
    }

    //인물좋아요여부
    public boolean isLikePerson(Long userIdx,Long perIdx){

        System.out.println("personservice진입");
        System.out.println("서비스 단 userIdx" + userIdx);
        System.out.println("서비스 단 perIdx" + perIdx);

        PersonLikeDto personLikeDto = personLikeRepository.findByUserIdxAndPerIdx(userIdx, perIdx);
        System.out.println("personLikeDto는 " + personLikeDto);

        if(personLikeDto == null){
            return false;
        }else{
            return true;
        }
    }
}
