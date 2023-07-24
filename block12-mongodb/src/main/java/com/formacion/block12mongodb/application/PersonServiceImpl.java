package com.formacion.block12mongodb.application;

import com.formacion.block12mongodb.controller.dto.input.PersonInputDto;
import com.formacion.block12mongodb.controller.dto.output.PersonOutputDto;
import com.formacion.block12mongodb.domain.Person;
import com.formacion.block12mongodb.exceptions.EntityNotFoundException;
import com.formacion.block12mongodb.exceptions.UnprocessableEntityException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PersonServiceImpl implements PersonService {
    @Autowired
    MongoTemplate mongoTemplate;
    @Override
    public PersonOutputDto addPerson(PersonInputDto personInputDto) throws UnprocessableEntityException {
        if(personInputDto.getUsername() == null || !(personInputDto.getUsername().length()>=6) || !(personInputDto.getUsername().length()<=10)) {
            throw new UnprocessableEntityException();
        }
        Person person = new Person(personInputDto);
        mongoTemplate.save(person);
        return person.personToPersonOutputDto();
    }

    @Override
    public PersonOutputDto getPersonById(String id) {
        Person person = mongoTemplate.findById(id, Person.class);
        return person.personToPersonOutputDto();
    }

    @Override
    public List<PersonOutputDto> getAll(int pageNumber, int pageSize) {
        PageRequest pageRequest = PageRequest.of(pageNumber, pageSize);
        Query query = new Query();
        query.with(pageRequest);
        return mongoTemplate.find(query, Person.class)
                .stream()
                .map(Person::personToPersonOutputDto)
                .toList();
    }

    @Override
    public String deletePerson(String id) {
        Query query = new Query();
        query.addCriteria(Criteria.where("idPerson").is(id));
        mongoTemplate.remove(query, Person.class);
            return "Se ha borrado correctamente.";
    }

    @Override
    public PersonOutputDto updatePerson(String id, PersonInputDto personInputDto) {
        Person person = mongoTemplate.findById(id, Person.class);
        person.setIdPerson(id);
        if(personInputDto.getUsername() != null && personInputDto.getUsername().length()>=6 && personInputDto.getUsername().length()<=10) {
            person.setUsername(personInputDto.getUsername());
        } else {
            throw new UnprocessableEntityException();
        }
        mongoTemplate.save(person);
        return person.personToPersonOutputDto();
    }
}
