package com.formacion.backend.domain;

import com.formacion.backend.controller.dto.output.ClientOutputDto;
import com.formacion.backend.controller.dto.input.TripInputDto;
import com.formacion.backend.controller.dto.output.TripOutputDto;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Entity
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

    @OneToMany(mappedBy = "trip")
    List<Client> passenger = new ArrayList<>();

    private Boolean status;


    public Trip(TripInputDto tripInputDto) {
        this.idTrip = tripInputDto.getIdTrip();
        this.origin = tripInputDto.getOrigin();
        this.destination = tripInputDto.getDestination();
        this.departureDate = tripInputDto.getDepartureDate();
        this.arrivalDate = tripInputDto.getArrivalDate();
        this.status = tripInputDto.getStatus();
    }

    public TripOutputDto tripToTripOutputDto() {
        List<ClientOutputDto> passengers = new ArrayList<>();
        for(Client c : passenger)
            passengers.add(c.clientToClientOutputDto());
        List<ClientOutputDto> clientOutputDtoList = new ArrayList<>(passengers);
        return new TripOutputDto(
                this.idTrip,
                this.origin,
                this.destination,
                this.departureDate,
                this.arrivalDate,
                clientOutputDtoList,
                this.status
        );
    }
}
