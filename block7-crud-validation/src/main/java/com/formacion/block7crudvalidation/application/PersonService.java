package com.formacion.block7crudvalidation.application;

import com.formacion.block7crudvalidation.controllers.dto.PersonInputDto;
import com.formacion.block7crudvalidation.controllers.dto.PersonOutputDto;

public interface PersonService {
    PersonOutputDto addPerson(PersonInputDto person) throws Exception;
    PersonOutputDto getPersonById(int id);
    PersonOutputDto getPersonByUsername(String username);
    Iterable<PersonOutputDto> getAllPersons(int pageNumber, int pageSize);
    void deletePerson(int id);
    PersonOutputDto updatePerson(PersonInputDto person, int id);
}