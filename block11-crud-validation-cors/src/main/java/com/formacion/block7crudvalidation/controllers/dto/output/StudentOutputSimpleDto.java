package com.formacion.block7crudvalidation.controllers.dto.output;

import com.formacion.block7crudvalidation.domain.Subjects;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Set;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class StudentOutputSimpleDto {
    private Integer id_student;
    private Integer num_hours_week;
    private String comments;
    private String branch;
    private Set<SubjectsOutputDto> subjects;
}
