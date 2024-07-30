package com.indigo.project.Aeropulse.repository;

import com.indigo.project.Aeropulse.model.PassengerEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PassengerRepository extends JpaRepository<PassengerEntity, String> {
    List<PassengerEntity> findByFlightId(String flightId);
}
