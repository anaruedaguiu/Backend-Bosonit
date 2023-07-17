package com.formacion.block7crudvalidation.controllers.dto.output;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PersonOutputDto {
    private int id;
    private String username; //NOT NULL max-length: 10 min-length: 6
    private String password; //NOT NULL
    private String name; //NOT NULL
    private String surname;
    private String company_email; //NOT NULL
    private String personal_email; //NOT NULL
    private String city; //NOT NULL
    private Boolean active; //NOT NULL
    private LocalDate created_date; //NOT NULL
    private String image_url;
    private LocalDate termination_date;
    private String role;
}