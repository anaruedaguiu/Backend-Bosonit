package com.formacion.block7crudvalidation.application;

import com.formacion.block7crudvalidation.controllers.dto.PersonInputDto;
import com.formacion.block7crudvalidation.controllers.dto.PersonOutputDto;
import com.formacion.block7crudvalidation.exceptions.EntityNotFoundException;
import com.formacion.block7crudvalidation.exceptions.UnprocessableEntityException;

public interface PersonService {
    PersonOutputDto addPerson(PersonInputDto person) throws UnprocessableEntityException;
    PersonOutputDto getPersonById(int id) throws EntityNotFoundException;
    PersonOutputDto getPersonByUsername(String username);
    Iterable<PersonOutputDto> getAllPersons(int pageNumber, int pageSize);
    void deletePerson(int id) throws EntityNotFoundException;
    PersonOutputDto updatePerson(PersonInputDto person, int id) throws EntityNotFoundException, UnprocessableEntityException;
}