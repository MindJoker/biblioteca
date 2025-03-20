package org.dirimo.biblioteca.resources.reservation;

import lombok.RequiredArgsConstructor;
import org.dirimo.commonlibrary.event.GenericModuleEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class ReservationListener {

    private final ReservationService reservationService;

    @EventListener
    public void handleReservationEvent(GenericModuleEvent<Reservation> genericModuleEvent) {
        switch (genericModuleEvent.getEventType()) {
            case OPENED -> {
                reservationService.sendOpenReservationJMS(genericModuleEvent.getPayload());
                //reservationService.sendOpenReservationMail(genericModuleEvent.getPayload());
            }
            case CLOSED -> reservationService.sendCloseReservationMail(genericModuleEvent.getPayload());
        }

    }
}
