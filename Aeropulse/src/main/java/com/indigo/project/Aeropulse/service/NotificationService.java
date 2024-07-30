package com.indigo.project.Aeropulse.service;

import com.indigo.project.Aeropulse.model.FlightEntity;
import com.indigo.project.Aeropulse.model.NotificationEntity;
import com.indigo.project.Aeropulse.model.PassengerEntity;
import com.indigo.project.Aeropulse.repository.FlightRepository;
import com.indigo.project.Aeropulse.repository.NotificationRepository;
import com.indigo.project.Aeropulse.repository.PassengerRepository;
import com.indigo.project.Aeropulse.service.service.EmailService;
import com.indigo.project.Aeropulse.service.service.SMSService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class NotificationService {

    @Autowired
    private FlightRepository flightRepository;

    @Autowired
    private NotificationRepository notificationRepository;

    @Autowired
    private PassengerRepository passengerRepository;

    @Autowired
    private EmailService emailService;

    @Autowired
    private SMSService smsService;

    public void createAndSendNotifications(String flightId) {
        FlightEntity flight = flightRepository.findById(flightId).orElseThrow(() -> new RuntimeException("Flight not found"));

        String message = generateNotificationMessage(flight);
        List<PassengerEntity> passengers = passengerRepository.findByFlightId(flightId);

        if(!passengers.isEmpty()) {
            passengers.forEach(passenger -> {
                NotificationEntity notification = new NotificationEntity();
                notification.setFlightId(flightId);
                notification.setMessage(message);
                notification.setTimestamp(LocalDateTime.now());
                notification.setMethod(passenger.getMethod());
                notification.setRecipient(passenger.getRecipient());
                notificationRepository.save(notification);
                // Send the notification via the preferred method
                sendNotification(notification);
            });
        }
    }

    String generateNotificationMessage(FlightEntity flight) {
        StringBuilder message = new StringBuilder();
        message.append("Flight ").append(flight.getFlightId()).append(" (").append(flight.getAirline()).append(") is now ");
        message.append(flight.getStatus()).append(". ");

        if (flight.getStatus().equalsIgnoreCase("Delayed") || flight.getStatus().equalsIgnoreCase("Cancelled")) {
            message.append("Please check the new schedule. ");
        }

        message.append("Departure Gate: ").append(flight.getDepartureGate()).append(". ");
        message.append("Arrival Gate: ").append(flight.getArrivalGate()).append(". ");
        message.append("Scheduled Departure: ").append(flight.getScheduledDeparture()).append(". ");
        message.append("Scheduled Arrival: ").append(flight.getScheduledArrival()).append(". ");

        if (flight.getActualDeparture() != null) {
            message.append("Actual Departure: ").append(flight.getActualDeparture()).append(". ");
        }

        if (flight.getActualArrival() != null) {
            message.append("Actual Arrival: ").append(flight.getActualArrival()).append(". ");
        }

        return message.toString();
    }

    private void sendNotification(NotificationEntity notification) {
        switch (notification.getMethod().toUpperCase()) {
            case "EMAIL" ->
                    emailService.send(notification.getRecipient(), notification.getMessage()); // Assuming notification.getRecipient() returns the email
            case "SMS" ->
                    smsService.send(notification); // Assuming notification.getRecipient() returns the phone number
            default -> throw new IllegalArgumentException("Unknown notification type: " + notification.getMethod());
        }
    }
}
