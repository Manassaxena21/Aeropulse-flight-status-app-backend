package com.indigo.project.Aeropulse.service;

import com.indigo.project.Aeropulse.model.FlightEntity;
import com.indigo.project.Aeropulse.repository.FlightRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class FlightServiceTest {

    @Mock
    private FlightRepository flightRepository;

    @Mock
    private NotificationService notificationService;

    @InjectMocks
    private FlightService flightService;

    private FlightEntity existingFlight;
    private FlightEntity updatedFlight;

    @BeforeEach
    public void setUp() {
        existingFlight = new FlightEntity();
        existingFlight.setFlightId("1");
        existingFlight.setStatus("On Time");
        // Set other initial values as needed

        updatedFlight = new FlightEntity();
        updatedFlight.setFlightId("1");
        updatedFlight.setStatus("Delayed");
        // Set other updated values as needed
    }

    @Test
    public void testUpdateFlightSuccess() {
        when(flightRepository.findById("1")).thenReturn(Optional.of(existingFlight));
        when(flightRepository.save(any(FlightEntity.class))).thenReturn(updatedFlight);

        FlightEntity result = flightService.updateFlight("1", updatedFlight);

        assertEquals("Delayed", result.getStatus());
        verify(flightRepository).findById("1");
        verify(flightRepository).save(existingFlight);
        verify(notificationService).createAndSendNotifications("1");
    }

    @Test
    public void testUpdateFlightNotFound() {
        when(flightRepository.findById("1")).thenReturn(Optional.empty());

        RuntimeException exception = assertThrows(RuntimeException.class, () -> {
            flightService.updateFlight("1", updatedFlight);
        });

        assertEquals("Flight not found", exception.getMessage());
    }
}
