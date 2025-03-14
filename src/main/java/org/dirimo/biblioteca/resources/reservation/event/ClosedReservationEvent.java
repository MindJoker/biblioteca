package org.dirimo.biblioteca.resources.reservation.event;

import lombok.Getter;
import org.dirimo.biblioteca.resources.reservation.Reservation;
import org.springframework.context.ApplicationEvent;

@Getter
public class ClosedReservationEvent extends ApplicationEvent {

    private final Reservation reservation;

    public ClosedReservationEvent(Object source , Reservation reservation) {
        super(source);
        this.reservation = reservation;
    }
}
