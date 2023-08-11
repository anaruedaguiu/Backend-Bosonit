package com.formacion.backendfront.controller.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class TripOutputDto {
    private Integer idTrip;
    private String origin;
    private String destination;
    private LocalDate departureDate;
    private LocalDate arrivalDate;
    private List<ClientOutputDto> passengers;
    private Boolean status;
}
