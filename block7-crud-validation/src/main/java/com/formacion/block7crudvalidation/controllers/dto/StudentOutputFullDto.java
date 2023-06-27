package com.formacion.block7crudvalidation.controllers.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class StudentOutputFullDto {
    private Integer id;
    private Integer num_hours_week;
    private String comments;
    private String branch;
    private PersonOutputDto personOutputDto;
}
