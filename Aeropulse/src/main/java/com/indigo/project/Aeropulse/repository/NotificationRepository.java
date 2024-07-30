package com.indigo.project.Aeropulse.repository;

import com.indigo.project.Aeropulse.model.NotificationEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface NotificationRepository extends JpaRepository<NotificationEntity,String> {
}
