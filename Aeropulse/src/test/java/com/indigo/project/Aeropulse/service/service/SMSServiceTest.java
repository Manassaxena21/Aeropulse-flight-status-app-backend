package com.indigo.project.Aeropulse.service.service;

import com.indigo.project.Aeropulse.configuration.TwilioConfig;
import com.indigo.project.Aeropulse.exception.NotificationException;
import com.indigo.project.Aeropulse.model.NotificationEntity;
import com.twilio.rest.api.v2010.account.Message;
import com.twilio.rest.api.v2010.account.MessageCreator;
import com.twilio.type.PhoneNumber;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class SMSServiceTest {

    @Mock
    private TwilioConfig twilioConfig;

    @InjectMocks
    private SMSService smsService;

    @BeforeEach
    public void setUp() {
        when(twilioConfig.getAccountSid()).thenReturn("testAccountSid");
        when(twilioConfig.getAuthToken()).thenReturn("testAuthToken");
        when(twilioConfig.getFromPhoneNumber()).thenReturn("+1234567890");

        smsService.init();
    }

    @Test
    public void testSendSMSSuccess() {
        NotificationEntity notification = new NotificationEntity();
        notification.setRecipient("+0987654321");
        notification.setMessage("Test SMS message");

        // Create a spy to monitor the static method behavior
        MessageCreator messageCreator = mock(MessageCreator.class);
        Message message = mock(Message.class);
        when(message.getSid()).thenReturn("testSid");

        // Mock the static Message.creator method
        try (var mockedStatic = mockStatic(Message.class)) {
            mockedStatic.when(() -> Message.creator(
                            any(PhoneNumber.class),
                            any(PhoneNumber.class),
                            any(String.class)))
                    .thenReturn(messageCreator);
            when(messageCreator.create()).thenReturn(message);

            smsService.send(notification);

            verify(messageCreator).create();
            verify(message).getSid();
        }
    }

    @Test
    public void testSendSMSFailure() {
        NotificationEntity notification = new NotificationEntity();
        notification.setRecipient("+0987654321");
        notification.setMessage("Test SMS message");

        // Mock the static Message.creator method to throw an exception
        try (var mockedStatic = mockStatic(Message.class)) {
            mockedStatic.when(() -> Message.creator(
                            any(PhoneNumber.class),
                            any(PhoneNumber.class),
                            any(String.class)))
                    .thenThrow(new RuntimeException("SMS sending failed"));

            NotificationException exception = assertThrows(NotificationException.class, () -> {
                smsService.send(notification);
            });

            assertEquals("Failed to send SMS", exception.getMessage());
        }
    }
}
