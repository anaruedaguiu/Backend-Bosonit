package com.formacion.backendfront.application;

import com.formacion.backendfront.controller.dto.TicketOutputDto;

public interface TicketService {
    TicketOutputDto createTicket(Integer idClient, Integer idTrip);
}
