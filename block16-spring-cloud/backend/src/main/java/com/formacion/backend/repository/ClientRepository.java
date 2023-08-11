package com.formacion.backend.repository;

import com.formacion.backend.domain.Client;
import org.springframework.data.jpa.repository.JpaRepository;


public interface ClientRepository extends JpaRepository<Client, Integer> {
}
