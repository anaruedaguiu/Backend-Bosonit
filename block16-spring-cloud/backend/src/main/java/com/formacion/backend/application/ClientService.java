package com.formacion.backend.application;

import com.formacion.backend.controller.dto.input.ClientInputDto;
import com.formacion.backend.controller.dto.output.ClientOutputDto;

public interface ClientService {
    ClientOutputDto addClient(ClientInputDto clientInputDto) throws Exception;
    ClientOutputDto getClientById(Integer idClient) throws Exception;
    Iterable<ClientOutputDto> getAllClients(int pageNumber, int pageSize);
    String deleteClientById(Integer idClient) throws Exception;
    ClientOutputDto updateClientById(ClientInputDto clientInputDto, Integer idClient) throws Exception;
}
