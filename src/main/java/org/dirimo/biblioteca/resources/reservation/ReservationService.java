package org.dirimo.biblioteca.resources.reservation;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.dirimo.biblioteca.resources.stock.Stock;
import org.dirimo.biblioteca.resources.stock.StockService;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor

public class ReservationService {


    private final ReservationRepository reservationRepository;
    private final StockService stockService;

    public List<Reservation> getAll() {

        return reservationRepository.findAll();
    }

    public Optional<Reservation> getById(Long id)
    {

        return reservationRepository.findById(id);
    }

    public Reservation create(Reservation reservation)
    {
        Long bookId = reservation.getBook().getId();

        Optional<Stock> stockOptional = stockService.getByBookId(bookId);

        Stock stock = stockOptional.orElseThrow(() ->
                new RuntimeException("Libro con id: " + bookId + " non trovato.")
        );

        if (stock.getAvailable_copies() <= 0) {
            throw new RuntimeException("Non ci sono copie del libro " + bookId + " disponibili al momento.");
        } else {
            stock.setAvailable_copies(stock.getAvailable_copies() - 1);
            return reservationRepository.save(reservation);
        }
    }

    public Reservation update(Long id, Reservation reservation) {
        reservationRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Prenotazione" + id + "non trovata"));

        return reservationRepository.save(reservation);
    }

    public void delete(Long id) {

        reservationRepository.deleteById(id);
    }

    public Reservation close(Long id, LocalDate date) {
        Reservation reservation = reservationRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Prenotazione con id: " + id + " non trovata"));
        reservation.setStatus(ReservationStatus.CLOSED);
        reservation.setResEndDate(date);
        int diff = (int) ChronoUnit.DAYS.between(reservation.getResStartDate(), reservation.getResEndDate());
        log.debug("Hai riportato indietro il libro in " + diff + " giorni.");
        return reservationRepository.save(reservation);
    }
}

