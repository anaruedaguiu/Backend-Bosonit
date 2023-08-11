package com.formacion.backend.controller;

import com.formacion.backend.application.ClientService;
import com.formacion.backend.application.TripService;
import com.formacion.backend.controller.dto.input.TripInputDto;
import com.formacion.backend.controller.dto.output.TripOutputDto;
import com.formacion.backend.exceptions.EntityNotFoundException;
import com.formacion.backend.exceptions.UnprocessableEntityException;
import org.hibernate.dialect.unique.CreateTableUniqueDelegate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;

@RestController
@RequestMapping("/trip")
public class TripController {
    @Autowired
    TripService tripService;

    @PostMapping
    public ResponseEntity<TripOutputDto> createTrip(@RequestBody TripInputDto tripInputDto) throws UnprocessableEntityException {
        URI location = URI.create("/trip");
        return ResponseEntity.created(location).body(tripService.addTrip(tripInputDto));
    }

    @GetMapping("/{idTrip}")
    public TripOutputDto getTripById(@PathVariable Integer idTrip) throws EntityNotFoundException {
        return tripService.getTripById(idTrip);
    }

    @GetMapping
    public Iterable<TripOutputDto> getAllTrip(@RequestParam(defaultValue = "0", required = false) int pageNumber,
                                               @RequestParam(defaultValue = "4", required = false) int pageSize) {
        return tripService.getAllTrips(pageNumber, pageSize);
    }

    @DeleteMapping("/{idTrip}")
    public String deleteTripById(@PathVariable Integer idTrip) throws EntityNotFoundException {
        tripService.deleteTripById(idTrip);
        return "Trip with id: " + idTrip + " has been deleted successfully";
    }

    @PutMapping("/{idTrip}")
    public TripOutputDto updateTripById(@RequestBody TripInputDto tripInputDto, @PathVariable Integer idTrip) throws UnprocessableEntityException, EntityNotFoundException {
        return tripService.updateTripById(tripInputDto, idTrip);
    }

    @PutMapping("/{idTrip}/{status}")
    public TripOutputDto changeTripStatus(@PathVariable Integer idTrip, @PathVariable Boolean status) throws EntityNotFoundException {
        return tripService.changeTripStatus(idTrip, status);
    }

    @GetMapping("/verify/{idTrip}")
    public Boolean getTripAvailability(@PathVariable Integer idTrip) {
        return tripService.tripAvailability(idTrip);
    }
}
