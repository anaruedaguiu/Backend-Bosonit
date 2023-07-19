package com.formacion.examen_JPA_cascada.repository;

import com.formacion.examen_JPA_cascada.controllers.output.ClienteOutputDto;
import com.formacion.examen_JPA_cascada.domain.Cliente;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.HashMap;

public interface ClienteRepository extends JpaRepository<Cliente, Integer> {
    Iterable<ClienteOutputDto> getCustomQuery(HashMap<String, Object> data);
}
