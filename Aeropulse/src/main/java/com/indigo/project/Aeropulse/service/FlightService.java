package com.indigo.project.Aeropulse.service;

import com.indigo.project.Aeropulse.model.FlightEntity;
import com.indigo.project.Aeropulse.repository.FlightRepository;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.stereotype.Service;


@Service
public class FlightService {
    @Autowired
    private FlightRepository flightRepository;

    @Autowired
    private NotificationService notificationService;

    public FlightEntity updateFlight(String id, FlightEntity flightDetails) {
        FlightEntity flight = flightRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Flight not found"));

        flight.setStatus(flightDetails.getStatus());
        flight.setActualArrival(flightDetails.getActualArrival());
        flight.setActualDeparture(flightDetails.getActualDeparture());
        flight.setArrivalGate(flightDetails.getArrivalGate());
        flight.setDepartureGate(flightDetails.getDepartureGate());
        flight.setScheduledArrival(flightDetails.getScheduledArrival());
        flight.setScheduledDeparture(flightDetails.getScheduledDeparture());

        FlightEntity updatedFlight = flightRepository.save(flight);

        // Notify passengers of the updates
        notificationService.createAndSendNotifications(flight.getFlightId());

        return updatedFlight;
    }


}
