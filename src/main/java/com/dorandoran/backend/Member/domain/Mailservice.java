package com.dorandoran.backend.Member.domain;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class Mailservice {


    private final JavaMailSender mailSender;

    public String sendResetPasswordEmail(String email) {
        String uuid = UUID.randomUUID().toString();
        String resetPasswordLink = "https://yourdomain.com/reset-password/" + uuid;

        try {
            MimeMessage message = mailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(message, true, "UTF-8");

            helper.setTo(email);
            helper.setSubject(EmailContent.RESET_PASSWORD.getSubject());
            helper.setText(EmailContent.RESET_PASSWORD.getBody(resetPasswordLink), true);

            mailSender.send(message);
        } catch (MessagingException e) {
            throw new RuntimeException("Failed to send email", e);
        }

        return uuid;
    }
}
