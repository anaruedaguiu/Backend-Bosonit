package com.formacion.block12mongodb.controller;

import com.formacion.block12mongodb.application.PersonService;
import com.formacion.block12mongodb.controller.dto.input.PersonInputDto;
import com.formacion.block12mongodb.controller.dto.output.PersonOutputDto;
import com.formacion.block12mongodb.domain.CustomError;
import com.formacion.block12mongodb.exceptions.EntityNotFoundException;
import com.formacion.block12mongodb.exceptions.UnprocessableEntityException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/person")
public class PersonController {
    @Autowired
    PersonService personService;

    @PostMapping
    public ResponseEntity<PersonOutputDto> addPerson(@RequestBody PersonInputDto personInputDto) {
        URI location = URI.create("/person");
        return ResponseEntity.created(location).body(personService.addPerson(personInputDto));
    }

    @GetMapping("/{idPerson}")
    public PersonOutputDto getPersonById(@PathVariable String idPerson) {
        return personService.getPersonById(idPerson);
    }

    @GetMapping
    public List<PersonOutputDto> getAll(@RequestParam(required = false) int pageNumber,
                                        @RequestParam(required = false) int pageSize) {
        return personService.getAll(pageNumber, pageSize);
    }

    @DeleteMapping("/{idPerson}")
    public String deletePerson(@PathVariable String idPerson) {
        personService.getPersonById(idPerson);
        return personService.deletePerson(idPerson);
    }

    @PutMapping("/{idPerson}")
    public PersonOutputDto updatePerson(@PathVariable String idPerson, @RequestBody PersonInputDto personInputDto) {
        personService.getPersonById(personInputDto.getIdPerson());
        return personService.updatePerson(idPerson, personInputDto);
    }

    @ExceptionHandler(UnprocessableEntityException.class)
    public ResponseEntity<CustomError> handleUnprocessableEntityException(UnprocessableEntityException ex) {
        CustomError customError = ex.getUnprocessableEntityException();
        return ResponseEntity.status(HttpStatus.UNPROCESSABLE_ENTITY).body(customError);
    }

    @ExceptionHandler(EntityNotFoundException.class)
    public ResponseEntity<CustomError> handleEntityNotFoundException(EntityNotFoundException ex) {
        CustomError customError = ex.getEntityNotFoundException();
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(customError);
    }
}
