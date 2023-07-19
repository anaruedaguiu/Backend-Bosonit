package com.formacion.examen_JPA_cascada.application;

import com.formacion.examen_JPA_cascada.controllers.input.CabeceraFraInputDto;
import com.formacion.examen_JPA_cascada.controllers.input.LineasFraInputDto;
import com.formacion.examen_JPA_cascada.controllers.output.CabeceraFraOutputDto;
import com.formacion.examen_JPA_cascada.controllers.output.LineasFraOutputDto;
import com.formacion.examen_JPA_cascada.exceptions.EntityNotFoundException;

import java.util.List;
import java.util.Optional;

public interface CabeceraFraService {
    Optional<CabeceraFraOutputDto> addFra(CabeceraFraInputDto cabeceraFraInputDto);
    CabeceraFraOutputDto getFraById(int idFra);
    List<CabeceraFraOutputDto> getAll();
    String deleteFra(int idFra) throws EntityNotFoundException;
    CabeceraFraOutputDto addLineaFra(int idFra, LineasFraInputDto lineasFraInputDto);

    //CabeceraFraOutputDto addLineaFra(int idFra, List<Integer> id_List); //Añade lista de líneas que ya existen previamente

}
