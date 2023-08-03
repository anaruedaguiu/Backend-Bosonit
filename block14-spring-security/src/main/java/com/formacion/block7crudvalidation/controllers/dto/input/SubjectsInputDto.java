package com.formacion.block7crudvalidation.controllers.dto.input;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class SubjectsInputDto {
    private Integer id_teacher;
    private String subject;
    private String comments;
    private Date initial_date;
    private Date finish_date;
}
