package com.formacion.block7crudvalidation.controllers.dto.output;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class TeacherOutputFullDto {
    private Integer id;
    private String comments;
    private String branch;
    private PersonOutputDto personOutputDto;
}
