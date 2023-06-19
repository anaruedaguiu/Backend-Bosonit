package com.formacion.block7crud.controllers.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PersonInputDto {
    int id;
    String name;
    String age;
    String population;
}
