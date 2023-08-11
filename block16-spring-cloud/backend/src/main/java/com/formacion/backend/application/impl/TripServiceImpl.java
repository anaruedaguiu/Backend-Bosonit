package com.formacion.backend.application.impl;

import com.formacion.backend.application.TripService;
import com.formacion.backend.controller.dto.input.TripInputDto;
import com.formacion.backend.controller.dto.output.TripOutputDto;
import com.formacion.backend.domain.Client;
import com.formacion.backend.domain.Trip;
import com.formacion.backend.exceptions.EntityNotFoundException;
import com.formacion.backend.exceptions.UnprocessableEntityException;
import com.formacion.backend.repository.ClientRepository;
import com.formacion.backend.repository.TripRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.Objects;

@Service
public class TripServiceImpl implements TripService {
    @Autowired
    TripRepository tripRepository;
    @Autowired
    ClientRepository clientRepository;

    @Override
    public TripOutputDto addTrip(TripInputDto tripInputDto) throws UnprocessableEntityException {
        if (tripInputDto.getOrigin() == null)
            throw new UnprocessableEntityException("Origin field can't be null", 422);
        if (tripInputDto.getDestination() == null)
            throw new UnprocessableEntityException("Destination field can't be null", 422);
        if (tripInputDto.getDepartureDate() == null)
            throw new UnprocessableEntityException("DepartureDate field can't be null", 422);
        if (tripInputDto.getArrivalDate() == null)
            throw new UnprocessableEntityException("ArrivalDate field can't be null", 422);
        if (tripInputDto.getStatus() == null)
            throw new UnprocessableEntityException("Status field can't be null", 422);
        return tripRepository.save(new Trip(tripInputDto))
                .tripToTripOutputDto();
    }

    @Override
    public TripOutputDto getTripById(Integer idTrip) throws EntityNotFoundException {
        if (tripRepository.findById(idTrip).isEmpty()) {
            throw new EntityNotFoundException("Trip with id: " + idTrip + " does not exist", 404);
        } else {
            return tripRepository.findById(idTrip).orElseThrow()
                    .tripToTripOutputDto();
        }
    }

    @Override
    public Iterable<TripOutputDto> getAllTrips(int pageNumber, int pageSize) {
        PageRequest pageRequest = PageRequest.of(pageNumber, pageSize);
        return tripRepository.findAll(pageRequest).getContent()
                .stream()
                .map(Trip::tripToTripOutputDto)
                .toList();
    }

    @Override
    public String deleteTripById(Integer idTrip) throws EntityNotFoundException {
        tripRepository.findById(idTrip).orElseThrow(() -> new EntityNotFoundException("Trip with id: " + idTrip + " does not exist", 404));
        tripRepository.deleteById(idTrip);
        return "Trip with id: " + idTrip + " has been deleted successfully";
    }

    @Override
    public TripOutputDto updateTripById(TripInputDto tripInputDto, Integer idTrip) throws EntityNotFoundException, UnprocessableEntityException {
        if (tripRepository.findById(idTrip).isEmpty()) {
            throw new EntityNotFoundException("Trip with id: " + idTrip + " does not exist", 404);
        } else {
            Trip tripUpdated = tripRepository.findById(idTrip).orElseThrow();
            if (Objects.isNull(tripInputDto.getOrigin()) || Objects.isNull(tripInputDto.getDestination()) || Objects.isNull(tripInputDto.getDepartureDate()) ||
                    Objects.isNull(tripInputDto.getArrivalDate()) || Objects.isNull(tripInputDto.getStatus())) {
                throw new UnprocessableEntityException("Fields must meet validations", 422);
            } else {
                tripUpdated.setOrigin(tripInputDto.getOrigin());
                tripUpdated.setDestination(tripInputDto.getDestination());
                tripUpdated.setDepartureDate(tripInputDto.getDepartureDate());
                tripUpdated.setArrivalDate(tripInputDto.getArrivalDate());
                tripUpdated.setStatus(tripInputDto.getStatus());
            }
            return tripRepository.save(tripUpdated)
                    .tripToTripOutputDto();
        }
    }

    @Override
    public String addPassengerToTrip(Integer idClient, Integer idTrip) throws UnprocessableEntityException {
        Client client = clientRepository.findById(idClient).orElseThrow();
        Trip trip = tripRepository.findById(idTrip).orElseThrow();

        client.setTrip(trip);
        trip.getPassenger().add(client);
        if(trip.getPassenger().size() >= 40)
            throw new UnprocessableEntityException("There are no more seats available", 422);
        clientRepository.save(client);
        tripRepository.save(trip);

        return "Passenger with id: " + idClient + " was correctly assigned to the Trip with id: " + idTrip;
    }

    @Override
    public Integer countPassengers(Integer idTrip) {
        Trip trip = tripRepository.findById(idTrip).orElseThrow();

        if(Objects.nonNull(trip.getPassenger())) {
            return trip.getPassenger().size();
        } else {
            return 0;
        }
    }

    @Override
    public TripOutputDto changeTripStatus(Integer idTrip, Boolean status) throws EntityNotFoundException {
        if(tripRepository.findById(idTrip).isEmpty())
            throw new EntityNotFoundException("Trip with id: " + idTrip + " does not exist", 404);

        Trip trip = tripRepository.findById(idTrip).orElseThrow();
        trip.setStatus(status);
        tripRepository.save(trip);

        return trip.tripToTripOutputDto();

    }

    @Override
    public Boolean tripAvailability(Integer idTrip) {
        Trip trip = tripRepository.findById(idTrip).orElseThrow();

        if(Objects.nonNull(trip.getStatus())) {
            return trip.getStatus();
        } else {
            return null;
        }
    }
}
