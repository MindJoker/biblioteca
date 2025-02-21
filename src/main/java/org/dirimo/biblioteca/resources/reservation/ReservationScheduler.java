package org.dirimo.biblioteca.resources.reservation;

import lombok.RequiredArgsConstructor;
import org.dirimo.biblioteca.resources.reservation.event.ClosedReservationEvent;
import org.dirimo.biblioteca.resources.reservation.event.OpenReservationEvent;
import org.dirimo.biblioteca.resources.reservation.mail.MailProperties;
import org.dirimo.biblioteca.resources.reservation.mail.MailService;
import org.springframework.context.event.EventListener;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
@RequiredArgsConstructor

public class ReservationScheduler {

    private final MailService mailService;
    private final ReservationRepository reservationRepository;
    private final ReservationService reservationService;

    @Async
    @Scheduled(cron = "0 13 12 * * ?")
    public void sendReminderEmails() {
        MailProperties mail = new MailProperties();
        LocalDate tomorrow = LocalDate.now().plusDays(1);

        // Recupera le prenotazioni che scadono domani
        List<Reservation> expiringReservations = reservationRepository.findByResEndDate(tomorrow);

        for (Reservation reservation : expiringReservations) {
            try {
                //String userEmail = "8b9b893d0f4a3e@sandbox.smtp.mailtrap.io";

                String userEmail = reservation.getEmail();

                String subject = "Promemoria Prenotazione - Biblioteca";
                String body = "<div style='font-family: Arial, sans-serif; font-size: 16px; color: #333;'>" +
                        "<p>Ciao <strong style='color: #007bff;'>" + reservation.getUsername() + "</strong>,</p>" +
                        "<p>📚 La tua prenotazione per il libro <strong style='color: #28a745;'>"
                        + reservation.getBook().getTitle() + "</strong> " +
                        "scadrà domani (<strong style='color: #dc3545;'>" + reservation.getResEndDate() + "</strong>).</p>" +
                        "<p style='font-size: 14px; color: #666;'>Ti ricordiamo di restituire il libro in tempo per evitare penalità.</p>" +
                        "<br>" +
                        "<p>Grazie,</p>" +
                        "<p><strong>La tua Biblioteca 📚</strong></p>" +
                        "</div>";

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

    @Async
    @EventListener
    public void onOpenReservationEvent(OpenReservationEvent event) {
        MailProperties  mail = reservationService.buildOpenReservationMail(event.getReservation());
        mailService.sendEmail(mail);
    }

    @Async
    @EventListener
    public void onCloseReservationEvent(ClosedReservationEvent event) {
        MailProperties  mail = reservationService.buildClosedReservationMail(event.getReservation());
        mailService.sendEmail(mail);
    }
}
