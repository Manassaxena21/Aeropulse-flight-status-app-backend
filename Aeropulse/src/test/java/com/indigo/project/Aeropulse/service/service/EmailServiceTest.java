package com.indigo.project.Aeropulse.service.service;

import com.indigo.project.Aeropulse.exception.NotificationException;
import jakarta.mail.internet.MimeMessage;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class EmailServiceTest {

    @Mock
    private JavaMailSender javaMailSender;

    @InjectMocks
    private EmailService emailService;

    @BeforeEach
    public void setUp() {
        emailService = new EmailService();
        emailService.javaMailSender = javaMailSender;
        emailService.fromEmail = "test@example.com";
    }

    @Test
    public void testSendEmailSuccess() throws Exception {
        MimeMessage mimeMessage = mock(MimeMessage.class);
        when(javaMailSender.createMimeMessage()).thenReturn(mimeMessage);

        emailService.send("test@recipient.com", "Test message");

        verify(javaMailSender, times(1)).send(mimeMessage);
    }

    @Test
    public void testSendEmailFailure() throws Exception {
        MimeMessage mimeMessage = mock(MimeMessage.class);
        when(javaMailSender.createMimeMessage()).thenReturn(mimeMessage);

        doAnswer(invocation -> {
            throw new jakarta.mail.MessagingException("Email sending failed");
        }).when(javaMailSender).send(any(MimeMessage.class));

        NotificationException exception = assertThrows(NotificationException.class, () -> {
            emailService.send("test@recipient.com", "Test message");
        });

        assertEquals("Failed to send email", exception.getMessage());
    }

}
