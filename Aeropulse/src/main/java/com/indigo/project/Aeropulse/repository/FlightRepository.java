package com.indigo.project.Aeropulse.repository;

import com.indigo.project.Aeropulse.model.FlightEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FlightRepository extends JpaRepository<FlightEntity, String> {
}
