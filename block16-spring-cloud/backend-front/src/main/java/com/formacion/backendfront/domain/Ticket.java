package com.formacion.backendfront.domain;

import com.formacion.backendfront.controller.dto.TicketOutputDto;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Ticket {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Integer idTicket;
    private Integer idClient;
    private String clientName;
    private String clientSurname;
    private String clientEmail;
    private String tripOrigin;
    private String tripDestination;
    private LocalDate departureDate;
    private LocalDate arrivalDate;

    public TicketOutputDto ticketToTicketOutputDto () {
        return new TicketOutputDto(
                this.idTicket,
                this.idClient,
                this.clientName,
                this.clientSurname,
                this.clientEmail,
                this.tripOrigin,
                this.tripDestination,
                this.departureDate,
                this.arrivalDate
        );
    }
}
