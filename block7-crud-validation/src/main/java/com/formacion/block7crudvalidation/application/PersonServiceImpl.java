package com.formacion.block7crudvalidation.application;

import com.formacion.block7crudvalidation.controllers.dto.PersonInputDto;
import com.formacion.block7crudvalidation.controllers.dto.PersonOutputDto;
import com.formacion.block7crudvalidation.domain.Person;
import com.formacion.block7crudvalidation.repository.PersonRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

@Service
public class PersonServiceImpl implements PersonService{
    @Autowired
    PersonRepository personRepository;

    @Override
    public PersonOutputDto addPerson(PersonInputDto person) throws Exception {
        if(person.getUsername() == null || !(person.getUsername().length()>=6) || !(person.getUsername().length()<=10)) {
            throw new Exception("Username no puede ser nulo y debe contener entre 6 y 10 caracteres");
        }
        if(person.getPassword() == null) {
            throw new Exception("Password no puede ser nulo");
        }
        if(person.getName() == null) {
            throw new Exception("Name no puede ser nulo");
        }
        if(person.getCompany_email() == null) {
            throw new Exception("Company email no puede ser nulo");
        }
        if(person.getPersonal_email() == null) {
            throw new Exception("Personal email no puede ser nulo");
        }
        if(person.getCity() == null) {
            throw new Exception("City no puede ser nulo");
        }
        if(person.getActive() == null) {
            throw new Exception("Active no puede ser nulo");
        }
        if(person.getCreated_date() == null) {
            throw new Exception("Created date no puede ser nulo");
        }
        return personRepository.save(new Person(person))
                .personToPersonOutputDto();
    }

    @Override
    public PersonOutputDto getPersonById(int id) {
        return personRepository.findById(id).orElseThrow()
                .personToPersonOutputDto();
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
    public void deletePerson(int id) {
        personRepository.findById(id).orElseThrow();
        personRepository.deleteById(id);
    }

    @Override
    public PersonOutputDto updatePerson(PersonInputDto person, int id) {
        Person personUpdated = personRepository.findById(id).orElseThrow();
        if(person.getUsername() != null && person.getUsername().length()>=6 && person.getUsername().length()<=10) {
            personUpdated.setUsername(person.getUsername());
        }
        if(person.getPassword() != null) {
            personUpdated.setPassword(person.getPassword());
        }
        if(person.getName() != null) {
            personUpdated.setName(person.getName());
        }
        if(person.getCompany_email() != null) {
            personUpdated.setCompany_email(person.getCompany_email());
        }
        if(person.getPersonal_email() != null) {
            personUpdated.setPersonal_email(person.getPersonal_email());
        }
        if(person.getCity() != null) {
            personUpdated.setCity(person.getCity());
        }
        if(person.getActive() != null) {
            personUpdated.setActive(person.getActive());
        }
        if(person.getCreated_date() != null) {
            personUpdated.setCreated_date(person.getCreated_date());
        }
        personUpdated.setSurname(person.getSurname());
        personUpdated.setImage_url(person.getImage_url());
        personUpdated.setTermination_date(person.getTermination_date());
        return personRepository.save(personUpdated)
                .personToPersonOutputDto();
    }
}