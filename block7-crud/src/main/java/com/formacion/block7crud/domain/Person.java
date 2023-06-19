package com.formacion.block7crud.domain;

import com.formacion.block7crud.controllers.dto.PersonInputDto;
import com.formacion.block7crud.controllers.dto.PersonOutputDto;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Person {
    @Id
    @GeneratedValue
    int id;
    String  name;
    String age;
    String population;

    public Person (PersonInputDto personInputDto) {
        this.id = personInputDto.getId();
        this.name = personInputDto.getName();
        this.age = personInputDto.getAge();
        this.population = personInputDto.getPopulation();
    }
    //Crea instancias de tipo Person a partir de objeto PersonInputDto,
    //copiando los valores de los campos de PersonInputDto a los correspondientes de Person.

    public PersonOutputDto personToPersonOutputDto() {
        return new PersonOutputDto(
                this.id,
                this.name,
                this.age,
                this.population
        );
    }
    //Se obtiene un objeto PersonOutputDto a partir de una instancia de Person,
    //asignando los valores de los campos de la instancia Person a los correspondientes del objeto PersonOutputDto
    //y luego devuelve la instancia de PErsonOutputDto.
}