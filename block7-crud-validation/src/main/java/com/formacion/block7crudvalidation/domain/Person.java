package com.formacion.block7crudvalidation.domain;

import com.formacion.block7crudvalidation.controllers.dto.PersonInputDto;
import com.formacion.block7crudvalidation.controllers.dto.PersonOutputDto;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Person {
    @Id
    @GeneratedValue
    private int id;
    private String username; //NOT NULL max-length: 10 min-length: 6
    private String password; //NOT NULL
    private String name; //NOT NULL
    private String surname;
    private String company_email; //NOT NULL
    private String personal_email; //NOT NULL
    private String city; //NOT NULL
    private Boolean active; //NOT NULL
    private Date created_date; //NOT NULL
    private String image_url;
    private Date termination_date;

    public Person(PersonInputDto personInputDto) {
        this.id = personInputDto.getId();
        this.username = personInputDto.getUsername();
        this.password = personInputDto.getPassword();
        this.name = personInputDto.getName();
        this.surname = personInputDto.getSurname();
        this.company_email = personInputDto.getCompany_email();
        this.personal_email = personInputDto.getPersonal_email();
        this.city = personInputDto.getCity();
        this.active = personInputDto.getActive();
        this.created_date = personInputDto.getCreated_date();
        this.image_url = personInputDto.getImage_url();
        this.termination_date = personInputDto.getTermination_date();
    }

    public PersonOutputDto personToPersonOutputDto() {
        return new PersonOutputDto(
                this.id,
                this.username,
                this.password,
                this.name,
                this.surname,
                this.company_email,
                this.personal_email,
                this.city,
                this.active,
                this.created_date,
                this.image_url,
                this.termination_date
        );
    }
}