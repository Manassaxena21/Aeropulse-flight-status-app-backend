package com.indigo.project.Aeropulse.service;

import com.indigo.project.Aeropulse.model.FlightEntity;
import com.indigo.project.Aeropulse.model.NotificationEntity;
import com.indigo.project.Aeropulse.model.PassengerEntity;
import com.indigo.project.Aeropulse.repository.FlightRepository;
import com.indigo.project.Aeropulse.repository.NotificationRepository;
import com.indigo.project.Aeropulse.repository.PassengerRepository;
import com.indigo.project.Aeropulse.service.service.EmailService;
import com.indigo.project.Aeropulse.service.service.SMSService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;

public class NotificationServiceTest {

    @InjectMocks
    private NotificationService notificationService;

    @Mock
    private FlightRepository flightRepository;

    @Mock
    private NotificationRepository notificationRepository;

    @Mock
    private PassengerRepository passengerRepository;

    @Mock
    private EmailService emailService;

    @Mock
    private SMSService smsService;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testCreateAndSendNotifications() {
        // Given
        FlightEntity flight = new FlightEntity();
        flight.setFlightId("FL123");
        flight.setAirline("Indigo");
        flight.setStatus("Delayed");
        flight.setDepartureGate("D1");
        flight.setArrivalGate("A1");
        flight.setScheduledDeparture(LocalDateTime.now().minusHours(1));
        flight.setScheduledArrival(LocalDateTime.now().plusHours(1));

        PassengerEntity passenger = new PassengerEntity();
        passenger.setMethod("EMAIL");
        passenger.setRecipient("test@example.com");

        when(flightRepository.findById("FL123")).thenReturn(Optional.of(flight));
        when(passengerRepository.findByFlightId("FL123")).thenReturn(Collections.singletonList(passenger));

        // Act
        notificationService.createAndSendNotifications("FL123");

        // Assert
        verify(notificationRepository, times(1)).save(any(NotificationEntity.class));
        verify(emailService, times(1)).send(eq("test@example.com"), any(String.class));
        verify(smsService, never()).send(any(NotificationEntity.class));
    }

    @Test
    public void testGenerateNotificationMessage() {
        // Given
        FlightEntity flight = new FlightEntity();
        flight.setFlightId("FL123");
        flight.setAirline("Indigo");
        flight.setStatus("Cancelled");
        flight.setDepartureGate("D1");
        flight.setArrivalGate("A1");
        flight.setScheduledDeparture(LocalDateTime.now().minusHours(1));
        flight.setScheduledArrival(LocalDateTime.now().plusHours(1));

        // Act
        String message = notificationService.generateNotificationMessage(flight);

        // Assert
        assert message.contains("Flight FL123 (Indigo) is now Cancelled.");
        assert message.contains("Please check the new schedule.");
        assert message.contains("Departure Gate: D1.");
        assert message.contains("Arrival Gate: A1.");
        assert message.contains("Scheduled Departure: ");
        assert message.contains("Scheduled Arrival: ");
    }
}
