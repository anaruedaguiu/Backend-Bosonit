package com.formacion.examen_JPA_cascada.controllers;

import com.formacion.examen_JPA_cascada.application.impl.ClienteServiceImpl;
import com.formacion.examen_JPA_cascada.controllers.input.ClienteInputDto;
import com.formacion.examen_JPA_cascada.controllers.output.CabeceraFraOutputDto;
import com.formacion.examen_JPA_cascada.controllers.output.ClienteOutputDto;
import com.formacion.examen_JPA_cascada.repository.ClienteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.HashMap;

@RestController
@RequestMapping("/cliente")
public class ClienteController {
    @Autowired
    ClienteServiceImpl clienteServiceImpl;
    @Autowired
    ClienteRepository clienteRepository;

    @PostMapping
    public ResponseEntity<ClienteOutputDto> addCliente(@RequestBody ClienteInputDto clienteInputDto) {
        URI location = URI.create("/cliente");
        return ResponseEntity.created(location).body(clienteServiceImpl.addCliente(clienteInputDto));
    }

    @GetMapping("/{idCliente}")
    public ResponseEntity<ClienteOutputDto> getCliente(@PathVariable int idCliente) throws Exception {
        return ResponseEntity.ok().body(clienteServiceImpl.getClienteById(idCliente));
    }

    @GetMapping
    public Iterable<ClienteOutputDto> getAllClientes(@RequestParam(defaultValue = "0", required = false) int pageNumber,
                                                     @RequestParam(defaultValue = "4", required = false) int pageSize) {
        return clienteServiceImpl.getAllClientes(pageNumber, pageSize);
    }

    @DeleteMapping
    public ResponseEntity<String> deleteCliente(@RequestParam int idCliente) {
        clienteServiceImpl.deleteCliente(idCliente);
        return ResponseEntity.ok().body("Cliente con id: " + idCliente + " borrado satisfactoriamente.");
    }

    @PutMapping("/{idCliente}")
    public ResponseEntity<ClienteOutputDto> updateCliente(@PathVariable int idCliente, @RequestBody ClienteInputDto clienteInputDto) {
        clienteServiceImpl.updateCliente(idCliente, clienteInputDto);
        return ResponseEntity.ok().body(clienteServiceImpl.updateCliente(idCliente, clienteInputDto));
    }

    // Criteria Builder - muestra clientes filtrando por los campos
    @GetMapping("/filter")
    public Iterable<ClienteOutputDto> findCliente(@RequestParam(required = false) String nombre,
                                                  @RequestParam(required = false) String poblacion,
                                                  @RequestParam(required = false) Integer tiempo_en_empresa) {

        HashMap<String, Object> data = new HashMap<>();

        if(nombre != null) data.put("nombre", nombre);
        if(poblacion != null) data.put("poblacion", poblacion);
        if(tiempo_en_empresa != null) data.put("tiempo_en_empresa", tiempo_en_empresa);

        return clienteRepository.getCustomQuery(data);
    }
}
