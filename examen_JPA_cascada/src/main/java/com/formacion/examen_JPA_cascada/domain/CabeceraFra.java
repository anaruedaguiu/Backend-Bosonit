package com.formacion.examen_JPA_cascada.domain;

import com.formacion.examen_JPA_cascada.controllers.input.CabeceraFraInputDto;
import com.formacion.examen_JPA_cascada.controllers.output.CabeceraFraOutputDto;
import com.formacion.examen_JPA_cascada.controllers.output.LineasFraOutputDto;
import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CabeceraFra {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    public int idFra;

    public double importeFra;

    @ManyToOne
    @JoinColumn(name="cli_codi") // Código cliente
    public Cliente cliente;

    @OneToMany(mappedBy = "cabeceraFra", cascade = CascadeType.ALL)
    List<LineasFra> lineasFraList = new ArrayList<>();

    public CabeceraFra(CabeceraFraInputDto cabeceraFraInputDto) {
        this.importeFra = cabeceraFraInputDto.getImporteFra();
        this.cliente = new Cliente();
        this.cliente.setIdCliente(cabeceraFraInputDto.getIdCliente());
    }

    public CabeceraFraOutputDto cabeceraToCabeceraFraOutputDto() {
        return new CabeceraFraOutputDto(
                this.idFra,
                this.importeFra,
                this.cliente.clienteToClienteOutputDto(),
                this.lineasFraList
                        .stream()
                        .map(LineasFraOutputDto::new)
                        .toList()
        );
    }
}
