package com.formacion.block12mongodb.domain;

import com.formacion.block12mongodb.controller.dto.input.PersonInputDto;
import com.formacion.block12mongodb.controller.dto.output.PersonOutputDto;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Date;

@Document(collection = "Person")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Person {
    @Id
    private String idPerson;
    private String username;
    private String password;
    private String name;
    private String surname;
    private String company_email;
    private String personal_email;
    private String city;
    private Boolean active;
    private Date created_date;
    private String image_url;
    private Date termination_date;

    public Person(PersonInputDto personInputDto) {
        this.idPerson = personInputDto.getIdPerson();
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
                this.idPerson,
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
