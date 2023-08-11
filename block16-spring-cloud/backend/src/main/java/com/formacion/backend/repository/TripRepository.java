package com.formacion.backend.repository;

import com.formacion.backend.domain.Trip;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TripRepository extends JpaRepository<Trip, Integer> {
}
