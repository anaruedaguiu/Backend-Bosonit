package com.formacion.block7crudvalidation.controllers.dto.output;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;
import java.util.List;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class SubjectsOutputDto {
    private Integer id_subject;
    private Integer id_teacher;
    private String subject;
    private String comments;
    private Date initial_date;
    private Date finish_date;
}
