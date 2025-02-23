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

    @Scheduled(cron = "0 41 22 * * ?")
    public void sendReminderEmails() {
        System.out.println("Esecuzione del metodo sendReminderEmails() - " + LocalDate.now());
        MailProperties mail = new MailProperties();
        LocalDate tomorrow = LocalDate.now().plusDays(1);

        // Recupera le prenotazioni che scadono domani
        List<Reservation> expiringReservations = reservationRepository.findByResEndDate(tomorrow);

        for (Reservation reservation : expiringReservations) {
            try {

                String userEmail = reservation.getCustomer().getEmail();

                String subject = "Promemoria Prenotazione libro" + reservation.getBook().getTitle() + "- Biblioteca";

                Map<String, Object> model = new HashMap<>();

                model.put("customerName", reservation.getCustomer().getFirstName());
                model.put("bookTitle", reservation.getBook().getTitle());
                model.put("resEndDate", reservation.getResEndDate());

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