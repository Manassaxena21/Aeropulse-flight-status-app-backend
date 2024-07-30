package com.indigo.project.Aeropulse.controller;

import com.indigo.project.Aeropulse.model.FlightEntity;
import com.indigo.project.Aeropulse.model.NotificationEntity;
import com.indigo.project.Aeropulse.service.FlightService;
import com.indigo.project.Aeropulse.repository.FlightRepository;
import com.indigo.project.Aeropulse.repository.NotificationRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;

import java.util.Collections;
import java.util.Optional;

import static org.mockito.Mockito.when;

@WebMvcTest(FlightController.class)
public class FlightControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private FlightRepository flightRepository;

    @MockBean
    private NotificationRepository notificationRepository;

    @MockBean
    private FlightService flightService;

    private FlightEntity flight;
    private NotificationEntity notification;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        flight = new FlightEntity();
        flight.setFlightId("AB123");
        flight.setStatus("On Time");

        notification = new NotificationEntity();
        notification.setNotificationId(1L);
        notification.setMessage("Test Notification");
        notification.setRecipient("test@example.com");
    }

    @Test
    public void testGetAllFlights() throws Exception {
        when(flightRepository.findAll()).thenReturn(Collections.singletonList(flight));

        mockMvc.perform(MockMvcRequestBuilders.get("/api/flights")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].flightId").value("AB123"))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].status").value("On Time"))
                .andDo(MockMvcResultHandlers.print());
    }

    @Test
    public void testGetFlightById() throws Exception {
        when(flightRepository.findById("AB123")).thenReturn(Optional.of(flight));

        mockMvc.perform(MockMvcRequestBuilders.get("/api/flights/AB123")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.flightId").value("AB123"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.status").value("On Time"))
                .andDo(MockMvcResultHandlers.print());
    }

    @Test
    public void testUpdateFlight() throws Exception {
        FlightEntity updatedFlight = new FlightEntity();
        updatedFlight.setFlightId("AB123");
        updatedFlight.setStatus("Delayed");

        when(flightService.updateFlight("AB123", updatedFlight)).thenReturn(updatedFlight);

        mockMvc.perform(MockMvcRequestBuilders.put("/api/flights/changeFlight/AB123")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"flightId\":\"AB123\",\"status\":\"Delayed\"}"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andDo(MockMvcResultHandlers.print());
    }

    @Test
    public void testGetAllNotifications() throws Exception {
        when(notificationRepository.findAll()).thenReturn(Collections.singletonList(notification));

        mockMvc.perform(MockMvcRequestBuilders.get("/api/flights/notifications")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].notificationId").value(1))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].message").value("Test Notification"))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].recipient").value("test@example.com"))
                .andDo(MockMvcResultHandlers.print());
    }
}
