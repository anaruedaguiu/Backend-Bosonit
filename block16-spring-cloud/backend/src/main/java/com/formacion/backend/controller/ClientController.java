package com.formacion.backend.controller;

import com.formacion.backend.application.ClientService;
import com.formacion.backend.controller.dto.input.ClientInputDto;
import com.formacion.backend.controller.dto.output.ClientOutputDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;

@RestController
@RequestMapping("client")
public class ClientController {
    @Autowired
    ClientService clientService;
    @PostMapping
    public ClientOutputDto createClient(@RequestBody ClientInputDto clientInputDto) throws Exception {
            URI location =  URI.create("/client");
            return clientService.addClient(clientInputDto);
    }

    @GetMapping("/{idClient}")
    public ClientOutputDto getClientById(@PathVariable Integer idClient) throws Exception {
        return clientService.getClientById(idClient);
    }

    @GetMapping
    public Iterable<ClientOutputDto> getAllClient(@RequestParam(required = false, defaultValue = "0") int pageNumber,
                                                         @RequestParam(required = false, defaultValue = "4") int pageSize) {
        return clientService.getAllClients(pageNumber, pageSize);
    }

    @DeleteMapping("/{idClient}")
    public String deleteClientById(@PathVariable Integer idClient) throws Exception {
        clientService.deleteClientById(idClient);
        return "Client with id: " + idClient + " has been deleted successfully";
    }

    @PutMapping("/{idClient}")
    public ClientOutputDto updateClientById(@RequestBody ClientInputDto clientInputDto, @PathVariable Integer idClient) throws Exception {
        return clientService.updateClientById(clientInputDto, idClient);
    }
}
