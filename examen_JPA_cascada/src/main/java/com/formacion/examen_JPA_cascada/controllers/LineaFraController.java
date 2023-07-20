package com.formacion.examen_JPA_cascada.controllers;

import com.formacion.examen_JPA_cascada.application.LineasFraService;
import com.formacion.examen_JPA_cascada.application.impl.LineaFraServiceImpl;
import com.formacion.examen_JPA_cascada.controllers.input.LineasFraInputDto;
import com.formacion.examen_JPA_cascada.controllers.output.LineasFraOutputDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.net.URI;

@RestController
@RequestMapping("/linea")
public class LineaFraController {
    @Autowired
    LineasFraService lineasFraService;

    @PostMapping
    public ResponseEntity<LineasFraOutputDto> addLinea(@RequestBody LineasFraInputDto lineasFraInputDto) {
        URI location = URI.create("/linea");
        return ResponseEntity.created(location).body(lineasFraService.addLinea(lineasFraInputDto));
    }
}
