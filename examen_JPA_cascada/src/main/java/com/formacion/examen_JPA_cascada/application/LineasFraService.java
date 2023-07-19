package com.formacion.examen_JPA_cascada.application;

import com.formacion.examen_JPA_cascada.controllers.input.LineasFraInputDto;
import com.formacion.examen_JPA_cascada.controllers.output.LineasFraOutputDto;

public interface LineasFraService {
    LineasFraOutputDto addLinea(LineasFraInputDto lineasFraInputDto);
}
