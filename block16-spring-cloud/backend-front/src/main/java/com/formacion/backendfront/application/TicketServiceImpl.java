package com.formacion.backendfront.application;

import com.formacion.backendfront.controller.dto.ClientOutputDto;
import com.formacion.backendfront.controller.dto.TicketOutputDto;
import com.formacion.backendfront.controller.dto.TripOutputDto;
import com.formacion.backendfront.domain.Ticket;
import com.formacion.backendfront.feign.ClientFeignService;
import com.formacion.backendfront.feign.TripFeignService;
import com.formacion.backendfront.repository.TicketRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class TicketServiceImpl implements TicketService {
    @Autowired
    ClientFeignService clientFeignService;
    @Autowired
    TripFeignService tripFeignService;
    @Autowired
    TicketRepository ticketRepository;
    @Override
    public TicketOutputDto createTicket(Integer idClient, Integer idTrip) {

        ClientOutputDto clientOutputDto = clientFeignService.getClientById(idClient);
        TripOutputDto tripOutputDto = tripFeignService.getTripById(idTrip);

        Ticket ticket = new Ticket();
        ticket.setIdClient(clientOutputDto.getIdClient());
        ticket.setClientName(clientOutputDto.getName());
        ticket.setClientSurname(clientOutputDto.getSurname());
        ticket.setClientEmail(clientOutputDto.getEmail());
        ticket.setTripOrigin(tripOutputDto.getOrigin());
        ticket.setTripDestination(tripOutputDto.getDestination());
        ticket.setDepartureDate(tripOutputDto.getDepartureDate());
        ticket.setArrivalDate(tripOutputDto.getArrivalDate());

        return ticketRepository.save(ticket).ticketToTicketOutputDto();
    }
}
