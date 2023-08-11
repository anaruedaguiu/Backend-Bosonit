package com.formacion.backend.controller;

import com.formacion.backend.application.TripService;
import com.formacion.backend.controller.dto.output.TripOutputDto;
import com.formacion.backend.exceptions.UnprocessableEntityException;
import org.apache.catalina.filters.RestCsrfPreventionFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/trip")
public class PassengerController {
    @Autowired
    TripService tripService;
    @PutMapping("/addPassenger/{idClient}/{idTrip}")
    public ResponseEntity<String> addPassengerToTrip(@PathVariable Integer idClient, @PathVariable Integer idTrip) throws UnprocessableEntityException {
        return ResponseEntity.ok().body(tripService.addPassengerToTrip(idClient, idTrip));
    }

    @GetMapping("/passenger/count/{idTrip}")
    public ResponseEntity<Integer> getPassengersNumber(@PathVariable Integer idTrip) {
        return ResponseEntity.ok().body(tripService.countPassengers(idTrip));
    }
}
