package com.formacion.backendfront.repository;

import com.formacion.backendfront.domain.Ticket;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TicketRepository extends JpaRepository<Ticket, Integer> {
}
