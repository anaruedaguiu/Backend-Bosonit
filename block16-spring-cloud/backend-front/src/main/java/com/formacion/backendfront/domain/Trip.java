package com.formacion.backendfront.domain;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Trip {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Integer idTrip;
    private String origin;
    private String destination;
    private LocalDate departureDate;
    private LocalDate arrivalDate;
    List<Client> passenger = new ArrayList<>();
    private Boolean status;
}
