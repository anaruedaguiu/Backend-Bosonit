package com.formacion.examen_JPA_cascada.controllers.input;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ClienteInputDto {

    public int tiempo_en_empresa;
    public String nombre; // nombre del cliente
    public String poblacion;
    public String direccion;
}
