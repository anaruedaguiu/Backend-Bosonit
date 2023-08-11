package com.formacion.backend.controller.dto.input;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ClientInputDto {
    private Integer idClient;
    private String name;
    private String surname;
    private Integer age;
    private String email;
    private Integer phone;
}
