package com.indigo.project.Aeropulse.service.service;

import com.indigo.project.Aeropulse.configuration.TwilioConfig;
import com.indigo.project.Aeropulse.exception.NotificationException;
import com.indigo.project.Aeropulse.model.NotificationEntity;
import com.twilio.Twilio;

import com.twilio.rest.api.v2010.account.Message;
import com.twilio.type.PhoneNumber;
import jakarta.annotation.PostConstruct;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class SMSService {
    private static final Logger logger = LoggerFactory.getLogger(SMSService.class);

    @Autowired
    private final TwilioConfig twilioConfig;

    public SMSService(TwilioConfig twilioConfig) {
        this.twilioConfig = twilioConfig;
    }

    @PostConstruct
    public void init() {
        Twilio.init(twilioConfig.getAccountSid(), twilioConfig.getAuthToken());
    }
        public void send(NotificationEntity notification) {
            try {
                logger.info("Sending SMS to {} with message: {}", notification.getRecipient(), notification.getMessage());
                Message message = Message.creator(
                                new PhoneNumber(notification.getRecipient()),
                                new PhoneNumber(twilioConfig.getFromPhoneNumber()),
                                notification.getMessage())
                        .create();
                System.out.println("SMS sent successfully: " + message.getSid());
            } catch (Exception e) {
                System.err.println("Failed to send SMS to " + notification.getRecipient() + ": " + e.getMessage());
                throw new NotificationException("Failed to send SMS", e);
            }
        }
}
