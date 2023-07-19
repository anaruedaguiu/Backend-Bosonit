package com.formacion.examen_JPA_cascada.controllers.output;

import com.formacion.examen_JPA_cascada.domain.CabeceraFra;
import lombok.*;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CabeceraFraOutputDto {

    public int idFra;
    public double importeFra;
    public ClienteOutputDto cliente;
    public List<LineasFraOutputDto> lineasFraList;

    public CabeceraFraOutputDto(CabeceraFra cabeceraFra) { // Método para pasar de entidad a outputDto
        setIdFra(cabeceraFra.getIdFra());
        setImporteFra(cabeceraFra.getImporteFra());
        setCliente(new ClienteOutputDto(cabeceraFra.getCliente())); // Llama al método para pasar cliente de entidad a output
        setLineasFraList(cabeceraFra.getLineasFraList().stream().map(LineasFraOutputDto::new).toList());
    }
}
