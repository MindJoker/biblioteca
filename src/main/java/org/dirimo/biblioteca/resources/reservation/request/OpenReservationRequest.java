package org.dirimo.biblioteca.resources.reservation.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.dirimo.biblioteca.resources.reservation.Reservation;
import org.dirimo.biblioteca.resources.reservation.action.HandleReservationAction;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor

public class OpenReservationRequest {
    private Reservation reservation;
    private HandleReservationAction action;
}
