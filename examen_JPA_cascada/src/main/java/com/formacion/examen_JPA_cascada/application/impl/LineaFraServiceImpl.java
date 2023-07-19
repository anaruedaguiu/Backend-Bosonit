package com.formacion.examen_JPA_cascada.application.impl;

import com.formacion.examen_JPA_cascada.application.LineasFraService;
import com.formacion.examen_JPA_cascada.controllers.input.LineasFraInputDto;
import com.formacion.examen_JPA_cascada.controllers.output.LineasFraOutputDto;
import com.formacion.examen_JPA_cascada.domain.LineasFra;
import com.formacion.examen_JPA_cascada.repository.LineasFraRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class LineaFraServiceImpl implements LineasFraService {
    @Autowired
    LineasFraRepository lineasFraRepository;
    @Override
    public LineasFraOutputDto addLinea(LineasFraInputDto lineasFraInputDto) {
        return lineasFraRepository.save(new LineasFra(lineasFraInputDto)).lineasToLineasFraOutputDto();
    }
}
