package com.formacion.backend.application;

import com.formacion.backend.controller.dto.input.TripInputDto;
import com.formacion.backend.controller.dto.output.TripOutputDto;
import com.formacion.backend.exceptions.EntityNotFoundException;
import com.formacion.backend.exceptions.UnprocessableEntityException;

public interface TripService {
    TripOutputDto addTrip(TripInputDto tripInputDto) throws UnprocessableEntityException;
    TripOutputDto getTripById(Integer idTrip) throws EntityNotFoundException;
    Iterable<TripOutputDto> getAllTrips(int pageNumber, int pageSize);
    String deleteTripById(Integer idTrip) throws EntityNotFoundException;
    TripOutputDto updateTripById(TripInputDto tripInputDto, Integer idTrip) throws EntityNotFoundException, UnprocessableEntityException;
    String addPassengerToTrip(Integer idClient, Integer idTrip) throws UnprocessableEntityException; // Añadir pasajero a viaje - revisar void
    Integer countPassengers(Integer idTrip); // Cuenta nº pasajeros de cada viaje
    TripOutputDto changeTripStatus(Integer idTrip, Boolean status) throws EntityNotFoundException;
    Boolean tripAvailability(Integer idTrip); // Indica disponibilidad del viaje
}
