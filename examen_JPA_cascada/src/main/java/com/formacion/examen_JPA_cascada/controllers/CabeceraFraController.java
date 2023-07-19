package com.formacion.examen_JPA_cascada.controllers;

import com.formacion.examen_JPA_cascada.application.impl.CabeceraFraServiceImpl;
import com.formacion.examen_JPA_cascada.controllers.input.CabeceraFraInputDto;
import com.formacion.examen_JPA_cascada.controllers.input.LineasFraInputDto;
import com.formacion.examen_JPA_cascada.controllers.output.CabeceraFraOutputDto;
import com.formacion.examen_JPA_cascada.domain.CustomError;
import com.formacion.examen_JPA_cascada.exceptions.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/factura")
public class CabeceraFraController {
    @Autowired
    CabeceraFraServiceImpl cabeceraFraServiceImpl;

    // Obtener todas las facturas
    @GetMapping
    public List<CabeceraFraOutputDto> getAll() {
        return cabeceraFraServiceImpl.getAll();
    }

    // Borra la factura y sus líneas
    @DeleteMapping("/{idFra}")
    public String deleteFra(@PathVariable int idFra) throws EntityNotFoundException { return cabeceraFraServiceImpl.deleteFra(idFra);
    }

    // Añade línea a fra existente
    @PostMapping("/linea/{idFra}")
    public ResponseEntity<CabeceraFraOutputDto> addLineaToFra(@PathVariable int idFra, @RequestBody LineasFraInputDto lineasFraInputDto) {
        try {
            cabeceraFraServiceImpl.addLineaFra(idFra, lineasFraInputDto);
            return ResponseEntity.ok().body(cabeceraFraServiceImpl.getFraById(idFra));
        } catch (Exception e) {
            return ResponseEntity.notFound().build();
        }
    }

    // Añadir línea a factura existente
    /*@PostMapping("/linea/{idFra}")
    public ResponseEntity<CabeceraFraOutputDto> addLineaToFra(@PathVariable int idFra, @RequestParam("id_list") List<Integer> id_list) {
        try {
            cabeceraFraServiceImpl.addLineaFra(idFra, id_list);
            return ResponseEntity.ok().body(cabeceraFraServiceImpl.getFraById(idFra));
        } catch (Exception e) {
            return ResponseEntity.notFound().build();
        }
    }*/

    // Crear factura
    @PostMapping
    public ResponseEntity<?> addFra(@RequestBody CabeceraFraInputDto cabeceraFraInputDto) {
        URI location = URI.create("/factura");
        return ResponseEntity.created(location).body(cabeceraFraServiceImpl.addFra(cabeceraFraInputDto));
    }

    @ExceptionHandler(EntityNotFoundException.class)
    public ResponseEntity<CustomError> handleEntityNotFoundException(EntityNotFoundException ex) {
        CustomError customError = ex.getEntityNotFoundException();
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(customError);
    }
}
