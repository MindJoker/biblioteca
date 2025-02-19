package org.dirimo.biblioteca.resources.reservation;

import lombok.RequiredArgsConstructor;
import org.dirimo.biblioteca.resources.mail.MailService;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ReservationScheduledMails {

    private final MailService mailService;
    private final ReservationRepository reservationRepository;


    @Scheduled(cron = "0 13 12 * * ?")
    public void sendReminderEmails() {
        LocalDate tomorrow = LocalDate.now().plusDays(1);

        // Recupera le prenotazioni che scadono domani
        List<Reservation> expiringReservations = reservationRepository.findByResEndDate(tomorrow);

        for (Reservation reservation : expiringReservations) {
            try {
                String userEmail = "8b9b893d0f4a3e@sandbox.smtp.mailtrap.io";

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


                mailService.sendEmail(userEmail, subject, body);

            } catch (Exception e) {
                System.err.println("Errore nell'invio dell'email per la prenotazione ID: "
                        + reservation.getId() + " - " + e.getMessage());
            }
        }
    }
}
