package com.formacion.backend.controller.dto.output;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ClientOutputDto {
    private Integer idClient;
    private String name;
    private String surname;
    private Integer age;
    private String email;
    private Integer phone;
}
