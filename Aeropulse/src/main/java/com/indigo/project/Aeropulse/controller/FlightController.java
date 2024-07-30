package com.indigo.project.Aeropulse.controller;

import com.indigo.project.Aeropulse.model.FlightEntity;
import com.indigo.project.Aeropulse.model.NotificationEntity;
import com.indigo.project.Aeropulse.repository.FlightRepository;
import com.indigo.project.Aeropulse.repository.NotificationRepository;
import com.indigo.project.Aeropulse.service.FlightService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
@RestController
@RequestMapping("/api/flights")
@CrossOrigin("*")
public class FlightController {

    @Autowired
    private FlightRepository flightRepository;

    @Autowired
    private NotificationRepository notificationRepository;

    @Autowired
    private FlightService flightService;

    @GetMapping
    public ResponseEntity<List<FlightEntity>> getAllFlights() {
        List<FlightEntity> flights = flightRepository.findAll();
        return ResponseEntity.ok(flights);
    }

    @GetMapping("/{id}")
    public ResponseEntity<FlightEntity> getFlightById(@PathVariable String id) {
        FlightEntity flight = flightRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Flight not found"));
        return ResponseEntity.ok(flight);
    }

    @PutMapping("/changeFlight/{id}")
    public ResponseEntity<FlightEntity> updateFlight(@PathVariable String id, @RequestBody FlightEntity flightDetails) {

        FlightEntity updatedFlight = flightService.updateFlight(id, flightDetails);
        return ResponseEntity.ok(updatedFlight);
    }

    @GetMapping("/notifications")
    public ResponseEntity<List<NotificationEntity>> getAllNotifications(){
        List<NotificationEntity> notifications = notificationRepository.findAll();
        return ResponseEntity.ok(notifications);
    }
}
