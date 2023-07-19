package com.formacion.examen_JPA_cascada.application;

import com.formacion.examen_JPA_cascada.controllers.input.ClienteInputDto;
import com.formacion.examen_JPA_cascada.controllers.output.ClienteOutputDto;
import jakarta.persistence.EntityNotFoundException;

public interface ClienteService {
    ClienteOutputDto addCliente(ClienteInputDto clienteInputDto);
    ClienteOutputDto getClienteById(int idCliente) throws Exception;
    Iterable<ClienteOutputDto> getAllClientes(int pageNumber, int pageSize);
    String deleteCliente(int idCliente) throws EntityNotFoundException;
    ClienteOutputDto updateCliente(int idCliente, ClienteInputDto clienteInputDto);
}
