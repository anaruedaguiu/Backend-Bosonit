package com.formacion.block7crud.application;

import com.formacion.block7crud.controllers.dto.PersonInputDto;
import com.formacion.block7crud.controllers.dto.PersonOutputDto;
import com.formacion.block7crud.domain.Person;
import com.formacion.block7crud.repository.PersonRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class PersonServiceImpl implements PersonService{
    @Autowired
    PersonRepository personRepository;

    @Override
    public PersonOutputDto addPerson(PersonInputDto person) {
        return personRepository.save(new Person(person))
                .personToPersonOutputDto();
    }

    @Override
    public PersonOutputDto getPersonById(int id) {
        return personRepository.findById(id).orElseThrow()
                .personToPersonOutputDto();
    }

    @Override
    public void deletePersonById(int id) {
        personRepository.findById(id).orElseThrow();
        personRepository.deleteById(id);
    }

    @Override
    public Iterable<PersonOutputDto> getAllPersons(int pageNumber, int pageSize) {
        PageRequest pageRequest = PageRequest.of(pageNumber, pageSize);
        return personRepository.findAll(pageRequest).getContent()
                .stream()
                .map(Person::personToPersonOutputDto).toList();
    }

    @Override
    public PersonOutputDto updatePerson(PersonInputDto person) {
        personRepository.findById(person.getId()).orElseThrow();
        return personRepository.save(new Person(person))
                .personToPersonOutputDto();
    }

    @Override
    public List<PersonOutputDto> getPersonsByName(String name) {
        List<Person> persons = personRepository.findByName(name);
        return persons.stream()
                .map(Person::personToPersonOutputDto)
                .toList();
    }
}