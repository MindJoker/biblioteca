package org.dirimo.biblioteca.resources.reservation;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor

public class ReservationService {
    @Value("${library.maxReservationPerUser}")
    private int maxReservation;

    private final ReservationRepository reservationRepository;

    public List<Reservation> getAllReservations() {
        return reservationRepository.findAll();
    }

    public Optional<Reservation> getReservationById(Long id)
    {
        return reservationRepository.findById(id);
    }

    public Reservation addReservation(Reservation reservation)
    {

        return reservationRepository.save(reservation);
    }

    public Reservation updateReservation(Long id, Reservation reservation) {
       reservationRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Prenotazione" + id + "non trovata"));
        reservation.setId(id);
        return reservationRepository.save(reservation);
    }

    public void deleteReservation(Long id) {
        reservationRepository.deleteById(id);
    }
}

//da vedere Master-details
