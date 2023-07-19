package com.formacion.examen_JPA_cascada.domain;

import com.formacion.examen_JPA_cascada.controllers.input.ClienteInputDto;
import com.formacion.examen_JPA_cascada.controllers.output.ClienteOutputDto;
import jakarta.persistence.*;
import lombok.*;
import org.antlr.v4.runtime.misc.NotNull;


import java.util.List;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Cliente {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    public int idCliente;

    public int tiempo_en_empresa;

    @OneToMany(mappedBy = "cliente", cascade = CascadeType.ALL)
    public List<CabeceraFra> cabeceraFra;

    @NotNull
    public String nombre; // Nombre del cliente
    public String poblacion;
    public String direccion;


    public Cliente (ClienteInputDto clienteInputDto) {
        this.tiempo_en_empresa = clienteInputDto.getTiempo_en_empresa();
        this.nombre = clienteInputDto.getNombre();
        this.poblacion = clienteInputDto.getPoblacion();
        this.direccion = clienteInputDto.getDireccion();
    }

    public ClienteOutputDto clienteToClienteOutputDto() {
        return new ClienteOutputDto(
                this.idCliente,
                this.tiempo_en_empresa,
                this.nombre,
                this.poblacion,
                this.direccion
        );
    }
}
