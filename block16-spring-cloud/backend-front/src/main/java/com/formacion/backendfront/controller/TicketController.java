package com.formacion.backendfront.controller;

import com.formacion.backendfront.application.TicketService;
import com.formacion.backendfront.controller.dto.TicketOutputDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/ticket")
public class TicketController {
    @Autowired
    TicketService ticketService;
    @PostMapping("/generateTicket/{idClient}/{idTrip}")
    public TicketOutputDto createTicket(@PathVariable Integer idClient, @PathVariable Integer idTrip) {
        return ticketService.createTicket(idClient, idTrip);
    }
}
