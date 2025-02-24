package org.dirimo.biblioteca.resources.reservation;

import lombok.RequiredArgsConstructor;
import org.dirimo.biblioteca.mail.EmailTemplateService;
import org.dirimo.biblioteca.mail.MailProperties;
import org.dirimo.biblioteca.mail.MailService;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class ReservationScheduler {
//solo job
    private final MailService mailService;
    private final ReservationRepository reservationRepository;
    private final EmailTemplateService emailTemplateService;

    @Scheduled(cron = "0 41 9 * * ?")
    public void sendReminderEmails() {
        System.out.println("Esecuzione del metodo sendReminderEmails() - " + LocalDate.now());
        MailProperties mail = new MailProperties();
        LocalDate tomorrow = LocalDate.now().plusDays(1);

        // Recupera le prenotazioni che scadono domani
        List<Reservation> expiringReservations = reservationRepository.findByResEndDate(tomorrow);

        for (Reservation reservation : expiringReservations) {
            System.out.println("Esecuzione di for con Reservation reservation : expiringReservations - " + LocalDate.now());
            try {

                String userEmail = reservation.getCustomer().getEmail();

                String subject = "Promemoria Prenotazione libro" + reservation.getBook().getTitle() + "- Biblioteca";


                Map<String, Object> model = Map.of(
                        "customerName", reservation.getCustomer().getFirstName(),
                        "bookTitle", reservation.getBook().getTitle(),
                        "resEndDate", reservation.getResEndDate()
                );

                String body = emailTemplateService.generateEmail("ReminderReservationMailTemplate", model);

                mail.setTo(userEmail);
                mail.setSubject(subject);
                mail.setBody(body);

                mailService.sendEmail(mail);

            } catch (Exception e) {
                System.err.println("Errore nell'invio dell'email per la prenotazione ID: "
                        + reservation.getId() + " - " + e.getMessage());
            }
        }
    }
}