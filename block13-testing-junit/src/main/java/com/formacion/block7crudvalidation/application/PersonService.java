package com.formacion.block7crudvalidation.application;

import com.formacion.block7crudvalidation.controllers.dto.input.PersonInputDto;
import com.formacion.block7crudvalidation.controllers.dto.output.PersonOutputDto;
import com.formacion.block7crudvalidation.exceptions.EntityNotFoundException;
import com.formacion.block7crudvalidation.exceptions.UnprocessableEntityException;

public interface PersonService {
    PersonOutputDto addPerson(PersonInputDto person) throws UnprocessableEntityException;
    PersonOutputDto getPersonById(int id); //Para obtener sólo la persona
    Object getPersonAndRoleById(int id, String outputType) throws EntityNotFoundException; // Para obtener la persona + el rol
    PersonOutputDto getPersonByUsername(String username);
    Iterable<PersonOutputDto> getAllPersons(int pageNumber, int pageSize);
    Iterable<?> getAllPersonsFull(String outputType);
    void deletePerson(int id) throws EntityNotFoundException;
    PersonOutputDto updatePerson(PersonInputDto person, int id) throws EntityNotFoundException, UnprocessableEntityException;
}