package com.indigo.project.Aeropulse.model;

import jakarta.persistence.*;

@Entity
@Table(name = "passengers")
public class PassengerEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "passenger_id")
    private Long passengerId;

    @Column(name = "flight_id")
    private String flightId;

    @Column(name = "method")
    private String method;

    @Column(name = "recipient")
    private String recipient;

    public Long getPassengerId() {
        return passengerId;
    }

    public void setNotificationId(Long passengerId) {
        this.passengerId = passengerId;
    }

    public String getFlightId() {
        return flightId;
    }

    public void setFlightId(String flightId) {
        this.flightId = flightId;
    }

    public String getMethod() {
        return method;
    }

    public void setMethod(String method) {
        this.method = method;
    }

    public String getRecipient() {
        return recipient;
    }

    public void setRecipient(String recipient) {
        this.recipient = recipient;
    }
}
