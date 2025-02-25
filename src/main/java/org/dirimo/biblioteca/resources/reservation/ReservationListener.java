package org.dirimo.biblioteca.resources.reservation;

import lombok.RequiredArgsConstructor;
import org.dirimo.biblioteca.resources.reservation.event.ClosedReservationEvent;
import org.dirimo.biblioteca.resources.reservation.event.OpenReservationEvent;
import org.dirimo.biblioteca.mail.MailProperties;
import org.dirimo.biblioteca.mail.MailService;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class ReservationListener {

    private final ReservationService reservationService;


    @EventListener
    public void onOpenReservationEvent(OpenReservationEvent event) {
//        reservationService.sendOpenReservationMail;
//        MailProperties mail = reservationService.buildOpenReservationMail(event.getReservation());
//        mailService.sendEmail(mail);
    }

    @EventListener
    public void onCloseReservationEvent(ClosedReservationEvent event) {
//        reservationService.sendCloseReservationMail;
//        MailProperties  mail = reservationService.buildClosedReservationMail(event.getReservation());
//        mailService.sendEmail(mail);
    }


}
