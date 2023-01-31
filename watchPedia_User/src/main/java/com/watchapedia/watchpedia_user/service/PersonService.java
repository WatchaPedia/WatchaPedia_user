package com.watchapedia.watchpedia_user.service;

import com.watchapedia.watchpedia_user.model.dto.PersonDto;
import com.watchapedia.watchpedia_user.model.network.response.PersonResponse;
import com.watchapedia.watchpedia_user.model.repository.PersonRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class PersonService {
    final PersonRepository personRepository;
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
}
