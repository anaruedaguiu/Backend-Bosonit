package com.formacion.examen_JPA_cascada.controllers.input;

import com.formacion.examen_JPA_cascada.domain.LineasFra;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class LineasFraInputDto {

    public String ProNomb; // Nombre del producto
    public double cantidad;
    public double precio;

    public LineasFra toLineasFra() { // Método para crear entidad a partir de inputDto
        LineasFra lineasFra = new LineasFra();
        lineasFra.setProNomb(this.getProNomb());
        lineasFra.setCantidad(this.getCantidad());
        lineasFra.setPrecio(this.getPrecio());
        return lineasFra;
    }
}
