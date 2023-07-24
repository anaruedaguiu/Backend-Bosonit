package com.formacion.block12mongodb.application;

import com.formacion.block12mongodb.controller.dto.input.PersonInputDto;
import com.formacion.block12mongodb.controller.dto.output.PersonOutputDto;
import com.formacion.block12mongodb.exceptions.EntityNotFoundException;
import com.formacion.block12mongodb.exceptions.UnprocessableEntityException;

import java.util.List;

public interface PersonService {
    PersonOutputDto addPerson(PersonInputDto personInputDto) throws UnprocessableEntityException;
    PersonOutputDto getPersonById(String id);
    List<PersonOutputDto> getAll(int pageNumber, int pageSize);
    String deletePerson(String id);
    PersonOutputDto updatePerson(String id, PersonInputDto personInputDto) throws UnprocessableEntityException;
}
