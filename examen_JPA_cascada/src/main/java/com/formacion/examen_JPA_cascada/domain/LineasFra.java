package com.formacion.examen_JPA_cascada.domain;

import com.formacion.examen_JPA_cascada.controllers.input.LineasFraInputDto;
import com.formacion.examen_JPA_cascada.controllers.output.LineasFraOutputDto;
import jakarta.persistence.*;
import lombok.*;
import org.antlr.v4.runtime.misc.NotNull;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class LineasFra {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    public int idLineasFra;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "idFra")
    public CabeceraFra cabeceraFra;

    @NotNull
    public String ProNomb; // nombre del producto
    public double cantidad;
    public double precio;

    public LineasFra (LineasFraInputDto lineasFraInputDto) {
        this.ProNomb = lineasFraInputDto.getProNomb();
        this.cantidad = lineasFraInputDto.getCantidad();
        this.precio = lineasFraInputDto.getPrecio();
    }

    public LineasFraOutputDto lineasToLineasFraOutputDto() {
        return new LineasFraOutputDto(
                this.idLineasFra,
                this.ProNomb,
                this.cantidad,
                this.precio
        );
    }
}
