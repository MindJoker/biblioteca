package org.dirimo.biblioteca.resources.reservation;


import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.dirimo.biblioteca.resources.reservation.close.CloseResAction;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.Optional;

@Slf4j
@Transactional
@RestController
@RequiredArgsConstructor
@RequestMapping("Reservation")
public class ReservationController {

    private final ReservationService reservationService;

    @GetMapping
    public List<Reservation> getAll() {

        return reservationService.getAll();
    }

    @GetMapping("/{id}")
    public Reservation getById(@PathVariable Long id) {
        return reservationService.getById(id)
                .orElseThrow(() -> new RuntimeException("Prenotazione con Id " + id + " non trovata."));
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Reservation create(@RequestBody Reservation reservation) {
        return reservationService.create(reservation);
    }

    @PutMapping("/{id}")
    public Reservation update(@PathVariable Long id, @RequestBody Reservation reservation) {
        return reservationService.update(id, reservation);
    }

    public void delete(@PathVariable Long id) {
        reservationService.delete(id);
    }

    @PostMapping("/close/{id}")
    public Reservation close(@PathVariable Long id, @RequestBody CloseResAction action) {
        return reservationService.close(id, action.getDate());
    }
}
