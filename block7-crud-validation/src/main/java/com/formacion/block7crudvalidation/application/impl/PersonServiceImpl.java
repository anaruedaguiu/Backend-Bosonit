package com.formacion.block7crudvalidation.application.impl;

import com.formacion.block7crudvalidation.application.PersonService;
import com.formacion.block7crudvalidation.controllers.dto.PersonInputDto;
import com.formacion.block7crudvalidation.controllers.dto.PersonOutputDto;
import com.formacion.block7crudvalidation.domain.Person;
import com.formacion.block7crudvalidation.exceptions.EntityNotFoundException;
import com.formacion.block7crudvalidation.exceptions.UnprocessableEntityException;
import com.formacion.block7crudvalidation.repository.PersonRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

@Service
public class PersonServiceImpl implements PersonService {
    @Autowired
    PersonRepository personRepository;

    @Override
    public PersonOutputDto addPerson(PersonInputDto person) throws UnprocessableEntityException {
        if(person.getUsername() == null || !(person.getUsername().length()>=6) || !(person.getUsername().length()<=10)) {
            throw new UnprocessableEntityException();
        }
        if(person.getPassword() == null || person.getName() == null || person.getCompany_email() == null || person.getPersonal_email() == null
        || person.getCity() == null || person.getActive() == null || person.getCreated_date() == null) {
            throw new UnprocessableEntityException();
        }
        return personRepository.save(new Person(person))
                .personToPersonOutputDto();
    }

    @Override
    public PersonOutputDto getPersonById(int id) throws EntityNotFoundException {
        if(personRepository.findById(id).isEmpty()) {
            throw new EntityNotFoundException();
        } else {
        return personRepository.findById(id).orElseThrow()
                .personToPersonOutputDto();
        }
    }

    @Override
    public PersonOutputDto getPersonByUsername(String username) {
        return personRepository.findByUsername(username).orElseThrow()
                .personToPersonOutputDto();
    }

    @Override
    public Iterable<PersonOutputDto> getAllPersons(int pageNumber, int pageSize) {
        PageRequest pageRequest = PageRequest.of(pageNumber, pageSize);
        return personRepository.findAll(pageRequest).getContent()
                .stream()
                .map(Person::personToPersonOutputDto).toList();
    }

    @Override
    public void deletePerson(int id) throws EntityNotFoundException {
        if (personRepository.findById(id).isEmpty()) {
            throw new EntityNotFoundException();
        } else {
            personRepository.deleteById(id);
        }
    }

    @Override
    public PersonOutputDto updatePerson(PersonInputDto person, int id) throws EntityNotFoundException, UnprocessableEntityException {
        if (personRepository.findById(id).isEmpty()) {
            throw new EntityNotFoundException();
        } else {
            Person personUpdated = personRepository.findById(id).orElseThrow();
            if(person.getSurname() != null) {
                personUpdated.setSurname(person.getSurname());
            }
            if(person.getImage_url() != null) {
                personUpdated.setImage_url(person.getImage_url());
            }
            if(person.getTermination_date() != null ){
                personUpdated.setTermination_date(person.getTermination_date());
            }
            if(person.getUsername() != null && person.getUsername().length()>=6 && person.getUsername().length()<=10) {
                personUpdated.setUsername(person.getUsername());
            } else {
                throw new UnprocessableEntityException();
            }
            if(person.getPassword() == null || person.getName() == null || person.getCompany_email() == null || person.getPersonal_email() == null
                    || person.getCity() == null || person.getActive() == null || person.getCreated_date() == null) {
                throw new UnprocessableEntityException();
            } else {
                personUpdated.setPassword(person.getPassword());
                personUpdated.setName(person.getName());
                personUpdated.setCompany_email(person.getCompany_email());
                personUpdated.setPersonal_email(person.getPersonal_email());
                personUpdated.setCity(person.getCity());
                personUpdated.setActive(person.getActive());
                personUpdated.setCreated_date(person.getCreated_date());
            }
            return personRepository.save(personUpdated)
                    .personToPersonOutputDto();
        }
    }
}