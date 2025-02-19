package org.dirimo.biblioteca.resources.mail;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import lombok.RequiredArgsConstructor;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class MailService {

    private final JavaMailSender mailSender;

    public void sendEmail(String to, String subject, String body) {
        try {
            MimeMessage message = mailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(message, true);

            helper.setTo(to);
            helper.setSubject(subject);
            helper.setText(body, true); // true = abilita HTML
            helper.setFrom("8b9b893d0f4a3e@sandbox.smtp.mailtrap.io");

            mailSender.send(message);
            System.out.println("Email inviata a " + to);
        } catch (MessagingException e) {
            System.err.println("Errore nell'invio dell'email: " + e.getMessage());
            throw new RuntimeException("Errore durante l'invio dell'email", e);
        }
    }
}
// email builder - pojo  con (to, subj, body)