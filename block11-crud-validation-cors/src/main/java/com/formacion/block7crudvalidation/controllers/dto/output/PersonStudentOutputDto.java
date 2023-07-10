package com.formacion.block7crudvalidation.controllers.dto.output;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class PersonStudentOutputDto {
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
    private String role;
    private StudentOutputSimpleDto student;
}
