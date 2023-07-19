package com.formacion.examen_JPA_cascada.application.impl;

import com.formacion.examen_JPA_cascada.application.ClienteService;
import com.formacion.examen_JPA_cascada.controllers.input.ClienteInputDto;
import com.formacion.examen_JPA_cascada.controllers.output.ClienteOutputDto;
import com.formacion.examen_JPA_cascada.domain.Cliente;
import com.formacion.examen_JPA_cascada.repository.ClienteRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class ClienteServiceImpl implements ClienteService {
    @Autowired
    ClienteRepository clienteRepository;

    @Override
    public ClienteOutputDto addCliente(ClienteInputDto clienteInputDto) {
        return clienteRepository.save(new Cliente(clienteInputDto)).clienteToClienteOutputDto();
    }

    @Override
    public ClienteOutputDto getClienteById(int idCliente) throws Exception {
        if(clienteRepository.findById(idCliente).isEmpty()) {
            throw new Exception();
        } else {
            return clienteRepository.findById(idCliente).orElseThrow()
                    .clienteToClienteOutputDto();
        }
    }

    @Override
    public Iterable<ClienteOutputDto> getAllClientes(int pageNumber, int pageSize) {
        PageRequest pageRequest = PageRequest.of(pageNumber, pageSize);
        return clienteRepository.findAll(pageRequest).getContent()
                .stream()
                .map(Cliente::clienteToClienteOutputDto)
                .toList();
    }

    @Override
    public String deleteCliente(int idCliente) throws EntityNotFoundException {
        Optional<Cliente> clienteOptional = clienteRepository.findById(idCliente);

        if(clienteOptional.isEmpty()) {
            throw new EntityNotFoundException("No existe el cliente con id: " +idCliente);
        }

        clienteRepository.deleteById(idCliente);
        return "El cliente con id: " + idCliente + " ha sido borrado satisfactoriamente.";
    }

    @Override
    public ClienteOutputDto updateCliente(int idCliente, ClienteInputDto clienteInputDto) throws EntityNotFoundException {
        if(clienteRepository.findById(idCliente).isEmpty()) {
            throw new EntityNotFoundException("No existe el cliente con id: " +idCliente);
        } else {
            Cliente clienteUpdated = clienteRepository.findById(idCliente).orElseThrow();
            clienteUpdated.setNombre(clienteInputDto.getNombre());
            clienteUpdated.setDireccion(clienteInputDto.getDireccion());
            clienteUpdated.setPoblacion(clienteInputDto.getPoblacion());
            clienteUpdated.setTiempo_en_empresa(clienteInputDto.getTiempo_en_empresa());
            return clienteRepository.save(clienteUpdated)
                    .clienteToClienteOutputDto();
        }
    }
}
