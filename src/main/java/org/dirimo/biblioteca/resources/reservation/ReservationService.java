package org.dirimo.biblioteca.resources.reservation;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.dirimo.biblioteca.mail.EmailTemplateService;
import org.dirimo.biblioteca.resources.book.Book;
import org.dirimo.biblioteca.resources.book.BookRepository;
import org.dirimo.biblioteca.resources.customer.Customer;
import org.dirimo.biblioteca.resources.customer.CustomerRepository;
import org.dirimo.biblioteca.resources.reservation.action.CloseReservationAction;
import org.dirimo.biblioteca.resources.reservation.action.OpenReservationAction;
import org.dirimo.biblioteca.resources.reservation.event.OpenReservationEvent;
import org.dirimo.biblioteca.mail.MailProperties;
import org.dirimo.biblioteca.resources.reservation.enumerated.ReservationStatus;
import org.dirimo.biblioteca.resources.reservation.event.ClosedReservationEvent;
import org.dirimo.biblioteca.resources.stock.Stock;
import org.dirimo.biblioteca.resources.stock.StockService;

import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
public class ReservationService {

    private final ReservationRepository reservationRepository;
    private final StockService stockService;
    private final BookRepository bookRepository;
    private final CustomerRepository customerRepository;
    private final ApplicationEventPublisher applicationEventPublisher;
    private final EmailTemplateService emailTemplateService;

    // Get all reservations
    public List<Reservation> getAll() {
        return reservationRepository.findAll();
    }

    // Get a reservation by ID
    public Optional<Reservation> getById(Long id) {
        return reservationRepository.findById(id);
    }

    // Add a new reservation
    public Reservation create(Reservation reservation) {
        return reservationRepository.save(reservation);
    }

    // Update a reservation
    public Reservation update(Long id, Reservation reservation) {
        reservationRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Prenotazione con id: " + id + " non trovata"));
        return reservationRepository.save(reservation);
    }

    // Delete a reservation by ID
    public void delete(Long id) {
        reservationRepository.deleteById(id);
    }


    // Open a new reservation (fill in attributes)
    public Reservation open(OpenReservationAction action) {

        Reservation reservation = action.getReservation();
        LocalDate date = action.getDate();

        Book book = bookRepository.findById(reservation.getBook().getId())
                .orElseThrow(() ->
                        new RuntimeException("Libro con id: " + reservation.getBook().getId() + " non trovato."));

        Optional<Stock> stockOptional = stockService.getByBook(book);
        Stock stock = stockOptional.orElseThrow(() ->
                new RuntimeException("Stock non trovato per il libro con id: " + book.getId())
        );

        if (stock.getAvailableCopies() <= 0) {
            throw new RuntimeException("Non ci sono copie disponibili per il libro con id: " + book.getId());
        }


        reservation.setResStartDate(date);
        stock.handleQuantity(-1);

        Reservation savedReservation = reservationRepository.save(reservation);

        applicationEventPublisher.publishEvent(new OpenReservationEvent(this, savedReservation));

        return savedReservation;
    }

    // Close a reservation
    public Reservation close(Long id, CloseReservationAction closeReservationAction) {

        LocalDate date = closeReservationAction.getDate();

        Reservation reservation = reservationRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Prenotazione con id: " + id + " non trovata"));
        reservation.setStatus(ReservationStatus.CLOSED);
        reservation.setResEndDate(date);

        // Updates stock
        Book book = reservation.getBook();
        Optional<Stock> stockOptional = stockService.getByBook(book);
        Stock stock = stockOptional.orElseThrow(() ->
                new RuntimeException("Libro con id: " + book.getId() + " non trovato.")
        );
        stock.handleQuantity(1);

        int diff = (int) ChronoUnit.DAYS.between(reservation.getResStartDate(), reservation.getResEndDate());
        log.info("Hai riportato indietro il libro in " + diff + " giorni.");


        applicationEventPublisher.publishEvent(new ClosedReservationEvent(this, reservation));


        return reservation;
    }


//    public MailProperties buildOpenReservationMail(Reservation reservation) {
//        MailProperties mail = new MailProperties();
//
//
//        Book book = bookRepository.findById(reservation.getBook().getId())
//                .orElseThrow(() ->
//                        new RuntimeException("Libro con id: " + reservation.getBook().getId() + " non trovato."));
//
//        Customer customer = customerRepository.findById(reservation.getCustomer().getId())
//                .orElseThrow(() ->
//                        new RuntimeException("Cliente con id: " + reservation.getCustomer().getId() + " non trovato."));
//
//        String subject = "Conferma prenotazione del libro " + book.getTitle();
//        String body = "<div style='font-family: Arial, sans-serif; max-width: 600px; padding: 20px; " +
//                "border: 1px solid #ddd; border-radius: 10px; background-color: #f9f9f9;'>" +
//                "<h2 style='color: #007bff;'>📚 Promemoria Prenotazione - Biblioteca</h2>" +
//                "<p>Ciao <strong>" + customer.getFirstName() + "</strong>,</p>" +
//                "<p>La tua prenotazione per il libro <strong style='color: #28a745;'>"
//                + book.getTitle() + "</strong> " +
//                "scadrà domani (<strong>" + reservation.getResEndDate() + "</strong>).</p>" +
//                "<p>Ti ricordiamo di restituire il libro in tempo per evitare penalità.</p>" +
//                "<div style='text-align: center; margin: 20px 0;'>" +
//                "<a href='https://biblioteca.example.com/reservations' " +
//                "style='background-color: #007bff; color: #ffffff; padding: 10px 20px; " +
//                "text-decoration: none; border-radius: 5px; font-size: 16px;'>📖 Visualizza Prenotazione</a>" +
//                "</div>" +
//                "<p style='color: #555;'>Grazie, <br>La tua Biblioteca 📚</p>" +
//                "</div>";
//
//        mail.setTo(customer.getEmail());
//        mail.setSubject(subject);
//        mail.setBody(body);
//
//        log.info("Reservation service email = " + mail.getTo());
//
//        return mail;
//    }

    public MailProperties buildOpenReservationMail(Reservation reservation) {
        MailProperties mail = new MailProperties();

        // Recupera il libro e il cliente
        Book book = bookRepository.findById(reservation.getBook().getId())
                .orElseThrow(() -> new RuntimeException("Libro con id: " + reservation.getBook().getId() + " non trovato."));

        Customer customer = customerRepository.findById(reservation.getCustomer().getId())
                .orElseThrow(() -> new RuntimeException("Cliente con id: " + reservation.getCustomer().getId() + " non trovato."));

        // Creazione della mappa con i dati dinamici
        Map<String, Object> model = new HashMap<>();
        model.put("customerName", customer.getFirstName());
        model.put("bookTitle", book.getTitle());
        model.put("resEndDate", reservation.getResEndDate());

        // Generazione dell'email dal template di conferma prenotazione
        String body = emailTemplateService.generateEmail("OpenReservationMailTemplate", model);

        mail.setTo(customer.getEmail());
        mail.setSubject("Conferma prenotazione del libro " + book.getTitle());
        mail.setBody(body);

        log.info("Email di conferma prenotazione inviata a: " + mail.getTo());

        return mail;
    }



    public MailProperties buildClosedReservationMail(Reservation reservation) {
        MailProperties mail = new MailProperties();

        Book book = reservation.getBook();

        String subject = "Conferma chiusura prenotazione del libro " + book.getTitle();
        String body = "<div style='font-family: Arial, sans-serif; font-size: 16px; color: #333;'>" +
                "<p>Ciao <strong style='color: #007bff;'>" + reservation.getCustomer().getFirstName() + "</strong>,</p>" +
                "<p>✅ Hai riconsegnato il libro <strong style='color: #28a745;'>" + book.getTitle() + "</strong>.</p>" +
                "<p>📅 La tua prenotazione è stata chiusa il <strong style='color: #dc3545;'>" +
                reservation.getResEndDate() + "</strong>.</p>" +
                "<br>" +
                "<p>Grazie per aver utilizzato il nostro servizio!</p>" +
                "<p><strong>La tua Biblioteca 📚</strong></p>" +
                "</div>";

        mail.setTo(reservation.getCustomer().getEmail());
        mail.setSubject(subject);
        mail.setBody(body);

        return mail;
    }

}