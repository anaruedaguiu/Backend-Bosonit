package com.formacion.block7crud.application;

import com.formacion.block7crud.controllers.dto.PersonInputDto;
import com.formacion.block7crud.controllers.dto.PersonOutputDto;

import java.util.List;

public interface PersonService {
    PersonOutputDto addPerson(PersonInputDto person);
    PersonOutputDto getPersonById(int id);
    void deletePersonById(int id);
    Iterable<PersonOutputDto> getAllPersons(int pageNumber, int pageSize);
    PersonOutputDto updatePerson(PersonInputDto person, int id);
    List<PersonOutputDto> getPersonsByName(String name);
}