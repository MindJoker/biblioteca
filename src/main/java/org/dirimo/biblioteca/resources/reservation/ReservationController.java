package org.dirimo.biblioteca.resources.reservation;


import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.dirimo.biblioteca.resources.reservation.action.CloseReservationAction;
import org.dirimo.biblioteca.resources.reservation.action.OpenReservationAction;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@Transactional
@RestController
@RequiredArgsConstructor
@RequestMapping("Reservation")
public class ReservationController {

    private final ReservationService reservationService;

    @GetMapping("/")
    public List<Reservation> getAll() {

        return reservationService.getAll();
    }

    @GetMapping("/{id}")
    public Reservation getById(@PathVariable Long id) {
        return reservationService.getById(id)
                .orElseThrow(() -> new RuntimeException("Prenotazione con Id " + id + " non trovata."));
    }

    @PostMapping("/")
    @ResponseStatus(HttpStatus.CREATED)
    public Reservation create(@RequestBody Reservation reservation) {
        return reservationService.create(reservation);
    }


    @PutMapping("/{id}")
    public Reservation update(@PathVariable Long id, @RequestBody Reservation reservation) {
        return reservationService.update(id, reservation);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) {

        reservationService.delete(id);
    }


    @PostMapping("/open/")
    @ResponseStatus(HttpStatus.CREATED)
    public Reservation open(@RequestBody OpenReservationAction openReservationAction) {
        return reservationService.open(openReservationAction);
    }

    @PostMapping("/bulk")
    @ResponseStatus(HttpStatus.CREATED)
    public List<Reservation> createBulk(@RequestBody List<OpenReservationAction> openReservationAction) {
        return reservationService.createBulk(openReservationAction);
    }


    @PostMapping("/close/{id}")
    public Reservation close(@PathVariable Long id, @RequestBody CloseReservationAction closeReservationAction) {
        return reservationService.close(id, closeReservationAction);
    }
}
