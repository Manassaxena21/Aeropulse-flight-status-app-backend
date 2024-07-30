package com.indigo.project.Aeropulse.model;

import jakarta.persistence.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "flights")
public class FlightEntity {
    @Id
    @Column(name = "flight_id")
    private String flightId;
    @Column(name = "airline")
    private String airline;
    @Column(name = "status")
    private String status;
    @Column(name = "departure_Gate")
    private String departureGate;
    @Column(name = "arrival_Gate")
    private String arrivalGate;
    @Column(name = "scheduled_Departure")
    private LocalDateTime scheduledDeparture;
    @Column(name = "scheduled_Arrival")
    private LocalDateTime scheduledArrival;
    @Column(name = "actual_Departure")
    private LocalDateTime actualDeparture;
    @Column(name = "actual_arrival")
    private LocalDateTime actualArrival;

    public String getFlightId() {
        return flightId;
    }

    public void setFlightId(String flightId) {
        this.flightId = flightId;
    }

    public String getAirline() {
        return airline;
    }

    public void setAirline(String airline) {
        this.airline = airline;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getDepartureGate() {
        return departureGate;
    }

    public void setDepartureGate(String departureGate) {
        this.departureGate = departureGate;
    }

    public String getArrivalGate() {
        return arrivalGate;
    }

    public void setArrivalGate(String arrivalGate) {
        this.arrivalGate = arrivalGate;
    }

    public LocalDateTime getScheduledDeparture() {
        return scheduledDeparture;
    }

    public void setScheduledDeparture(LocalDateTime scheduledDeparture) {
        this.scheduledDeparture = scheduledDeparture;
    }

    public LocalDateTime getScheduledArrival() {
        return scheduledArrival;
    }

    public void setScheduledArrival(LocalDateTime scheduledArrival) {
        this.scheduledArrival = scheduledArrival;
    }

    public LocalDateTime getActualDeparture() {
        return actualDeparture;
    }

    public void setActualDeparture(LocalDateTime actualDeparture) {
        this.actualDeparture = actualDeparture;
    }

    public LocalDateTime getActualArrival() {
        return actualArrival;
    }

    public void setActualArrival(LocalDateTime actualArrival) {
        this.actualArrival = actualArrival;
    }
}
