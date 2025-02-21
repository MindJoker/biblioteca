package org.dirimo.biblioteca.mail;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
@Slf4j
@Service
@RequiredArgsConstructor
public class MailService {

    private final JavaMailSender mailSender;

    public void sendEmail(MailProperties mail) {
        try {
            MimeMessage message = mailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(message, true);

            log.info("Mail service email = " + mail.getTo());

            helper.setTo(mail.getTo());
            helper.setSubject(mail.getSubject());
            helper.setText(mail.getBody(), true); // true = abilita HTML
            helper.setFrom("8b9b893d0f4a3e@sandbox.smtp.mailtrap.io");

            mailSender.send(message);
            System.out.println("Email inviata a " + mail.getTo());
        } catch (MessagingException e) {
            System.err.println("Errore nell'invio dell'email: " + e.getMessage());
            throw new RuntimeException("Errore durante l'invio dell'email", e);
        }
    }
}
