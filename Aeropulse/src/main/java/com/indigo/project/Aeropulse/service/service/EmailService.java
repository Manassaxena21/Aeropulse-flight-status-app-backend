package com.indigo.project.Aeropulse.service.service;

import com.indigo.project.Aeropulse.exception.NotificationException;
import jakarta.mail.internet.MimeMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

@Service
public class EmailService {
    @Autowired
    JavaMailSender javaMailSender;

    @Value("${spring.mail.username}")
    String fromEmail;

    public void send(String email, String message) {
        MimeMessage mimeMessage = javaMailSender.createMimeMessage();
        MimeMessageHelper helper;

        try {
            helper = new MimeMessageHelper(mimeMessage, true);
            helper.setTo(email);
            helper.setSubject("Flight Status Update");
            helper.setText(message, true);
            helper.setFrom(fromEmail);
            javaMailSender.send(mimeMessage);
        } catch (jakarta.mail.MessagingException e) {
            System.err.println("Failed to send email to " + email + ": " + e.getMessage());
            throw new NotificationException("Failed to send email", e);
        }
    }
}