package com.formacion.block7crudvalidation.controllers.dto.input;

import com.formacion.block7crudvalidation.domain.Person;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PersonInputDto {
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
}