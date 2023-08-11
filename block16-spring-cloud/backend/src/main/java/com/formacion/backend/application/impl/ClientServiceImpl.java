package com.formacion.backend.application.impl;

import com.formacion.backend.controller.dto.input.ClientInputDto;
import com.formacion.backend.controller.dto.output.ClientOutputDto;
import com.formacion.backend.application.ClientService;
import com.formacion.backend.domain.Client;
import com.formacion.backend.exceptions.EntityNotFoundException;
import com.formacion.backend.exceptions.UnprocessableEntityException;
import com.formacion.backend.repository.ClientRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.Objects;
import java.util.Optional;

@Service
public class ClientServiceImpl implements ClientService {
    @Autowired
    ClientRepository clientRepository;

    @Override
    public ClientOutputDto addClient(ClientInputDto clientInputDto) throws Exception {
        if(clientInputDto.getName() == null)
            throw new UnprocessableEntityException("Name field can't be null", 422);
        if(clientInputDto.getSurname() == null)
            throw new UnprocessableEntityException("Surname field can't be null", 422);
        if(clientInputDto.getAge() == null)
            throw new UnprocessableEntityException("Age field can't be null", 422);
        if(clientInputDto.getEmail() == null)
            throw new UnprocessableEntityException("Email field can't be null", 422);
        if(clientInputDto.getPhone() == null)
            throw new UnprocessableEntityException("Phone field can't be null", 422);
        return clientRepository.save(new Client(clientInputDto))
                .clientToClientOutputDto();
    }

    @Override
    public ClientOutputDto getClientById(Integer idClient) throws Exception {
        if(clientRepository.findById(idClient).isEmpty()) {
            throw new EntityNotFoundException("Client with id: " + idClient + " does not exist", 404);
        } else {
            return clientRepository.findById(idClient).orElseThrow()
                    .clientToClientOutputDto();
        }
    }

    @Override
    public Iterable<ClientOutputDto> getAllClients(int pageNumber, int pageSize) {
        PageRequest pageRequest = PageRequest.of(pageNumber, pageSize);
        return clientRepository.findAll(pageRequest).getContent()
                .stream()
                .map(Client::clientToClientOutputDto)
                .toList();
    }

    @Override
    public String deleteClientById(Integer idClient) throws Exception {
        Optional<Client> clientOptional = clientRepository.findById(idClient);
        if(clientOptional.isEmpty()) {
            throw new EntityNotFoundException("Client with id: " + idClient + " does not exist", 404);
        } else {
            clientRepository.deleteById(idClient);
            return "Client with id: " + idClient + " has been deleted successfully";
        }
    }

    @Override
    public ClientOutputDto updateClientById(ClientInputDto clientInputDto, Integer idClient) throws Exception {
        if(clientRepository.findById(idClient).isEmpty()) {
            throw new EntityNotFoundException("Client with id: " + idClient + " does not exist", 404);
        } else {
            Client clientUpdated = clientRepository.findById(idClient).orElseThrow();
            if(Objects.isNull(clientInputDto.getName()) || Objects.isNull(clientInputDto.getSurname()) || Objects.isNull(clientInputDto.getAge()) ||
                    Objects.isNull(clientInputDto.getEmail()) || Objects.isNull(clientInputDto.getPhone())) {
                throw new UnprocessableEntityException("Fields must meet validations", 422);
            } else {
                clientUpdated.setName(clientInputDto.getName());
                clientUpdated.setSurname(clientInputDto.getSurname());
                clientUpdated.setAge(clientInputDto.getAge());
                clientUpdated.setEmail(clientInputDto.getEmail());
                clientUpdated.setPhone(clientInputDto.getPhone());
            }
            return clientRepository.save(clientUpdated)
                    .clientToClientOutputDto();
        }
    }
}
