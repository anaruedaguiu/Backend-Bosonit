package com.formacion.examen_JPA_cascada.application.impl;

import com.formacion.examen_JPA_cascada.exceptions.EntityNotFoundException;
import com.formacion.examen_JPA_cascada.application.CabeceraFraService;
import com.formacion.examen_JPA_cascada.controllers.input.CabeceraFraInputDto;
import com.formacion.examen_JPA_cascada.controllers.input.LineasFraInputDto;
import com.formacion.examen_JPA_cascada.controllers.output.CabeceraFraOutputDto;
import com.formacion.examen_JPA_cascada.domain.CabeceraFra;
import com.formacion.examen_JPA_cascada.domain.Cliente;
import com.formacion.examen_JPA_cascada.domain.LineasFra;
import com.formacion.examen_JPA_cascada.repository.CabeceraFraRepository;
import com.formacion.examen_JPA_cascada.repository.ClienteRepository;
import com.formacion.examen_JPA_cascada.repository.LineasFraRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class CabeceraFraServiceImpl implements CabeceraFraService {
    @Autowired
    CabeceraFraRepository cabeceraFraRepository;
    @Autowired
    ClienteRepository clienteRepository;
    @Autowired
    LineasFraRepository lineasFraRepository;
    @Override
    public Optional<CabeceraFraOutputDto> addFra(CabeceraFraInputDto cabeceraFraInputDto) {
        Cliente cliente = clienteRepository.findById(cabeceraFraInputDto.getIdCliente()).orElseThrow();
        CabeceraFra cabeceraFra = new CabeceraFra(cabeceraFraInputDto);
        cabeceraFra.setCliente(cliente);
        CabeceraFra cabeceraFra1 = cabeceraFraRepository.save(cabeceraFra);
        return Optional.of(cabeceraFra1.cabeceraToCabeceraFraOutputDto());

        //return cabeceraFraRepository.save(new CabeceraFra(cabeceraFraInputDto)).cabeceraToCabeceraFraOutputDto();
    }

    @Override
    public CabeceraFraOutputDto getFraById(int idFra) {
        if(cabeceraFraRepository.findById(idFra).isEmpty()) {
            throw new EntityNotFoundException();
        } else {
            return cabeceraFraRepository.findById(idFra).orElseThrow()
                    .cabeceraToCabeceraFraOutputDto();
        }
    }

    @Override
    public List<CabeceraFraOutputDto> getAll() {

        List<CabeceraFra> facturaList = cabeceraFraRepository.findAll();
        List<CabeceraFraOutputDto> facturaOutputDtoList = new ArrayList<>();
        return facturaOutputDtoList = facturaList
                .stream()
                .map(CabeceraFra::cabeceraToCabeceraFraOutputDto)
                .collect(Collectors.toList());
    }

    @Override
    public String deleteFra(int idFra) {

        Optional<CabeceraFra> cabeceraFraOptional = cabeceraFraRepository.findById(idFra);
        if(cabeceraFraOptional.isEmpty()) {
            throw new EntityNotFoundException();
        } else {
            cabeceraFraRepository.deleteById(idFra);
            return "La factura con id " + idFra + " se ha borrado correctamente.";
        }
    }

    @Override
    public CabeceraFraOutputDto addLineaFra(int idFra, LineasFraInputDto lineasFraInputDto) {

        LineasFra lineasFra = lineasFraInputDto.toLineasFra(); // transformo inputDto en entidad
        CabeceraFra cabeceraFra =  cabeceraFraRepository.findById(idFra).orElseThrow();

        cabeceraFra.getLineasFraList().add(lineasFra); // añado la línea (entidad) a la columna de líneas de la fra
        lineasFra.setCabeceraFra(cabeceraFra); // seteo la fra al campo del idFra

        lineasFraRepository.save(lineasFra);

        return new CabeceraFraOutputDto(cabeceraFra);
    }

    /*@Override
    public CabeceraFraOutputDto addLineaFra(int idFra, List<Integer> id_List) {
        CabeceraFra cabeceraFra = cabeceraFraRepository.findById(idFra).orElseThrow();

        List<LineasFra> lineasFraList = new ArrayList<>();
        for(Integer idLineasFra : id_List) {
            LineasFra lineasFra = lineasFraRepository.findById(idLineasFra).orElseThrow();
            lineasFra.setCabeceraFra(cabeceraFra);
            lineasFraList.add(lineasFra);
        }

        lineasFraRepository.saveAll(lineasFraList);

        cabeceraFra.setLineasFraList(lineasFraList);
        cabeceraFraRepository.save(cabeceraFra);

        return cabeceraFra.cabeceraToCabeceraFraOutputDto();
    }*/
}
