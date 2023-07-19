package com.formacion.examen_JPA_cascada.controllers.output;

import com.formacion.examen_JPA_cascada.domain.LineasFra;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class LineasFraOutputDto {

    public int idLineasFra;
    public String ProNomb;
    public double cantidad;
    public double precio;


    public LineasFraOutputDto(LineasFra lineasFra) { // Método para pasar de entidad a outputDto
        setIdLineasFra(lineasFra.getIdLineasFra());
        setProNomb(lineasFra.getProNomb());
        setCantidad(lineasFra.getCantidad());
        setPrecio(lineasFra.getPrecio());
    }
}
