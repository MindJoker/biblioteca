package org.dirimo.biblioteca.resources.reservation;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.dirimo.biblioteca.jms.JMSService;
import org.dirimo.biblioteca.mail.MailService;
import org.dirimo.biblioteca.resources.reservation.dto.ReservationDTO;
import org.dirimo.biblioteca.resources.template.Template;
import org.dirimo.biblioteca.resources.template.TemplateRepository;
import org.dirimo.biblioteca.resources.template.TemplateService;
import org.dirimo.biblioteca.resources.book.Book;
import org.dirimo.biblioteca.resources.book.BookRepository;
import org.dirimo.biblioteca.resources.customer.Customer;
import org.dirimo.biblioteca.resources.customer.CustomerRepository;
import org.dirimo.biblioteca.resources.reservation.action.CloseReservationAction;
import org.dirimo.biblioteca.resources.reservation.action.OpenReservationAction;
import org.dirimo.biblioteca.mail.MailProperties;
import org.dirimo.biblioteca.resources.reservation.enumerated.ReservationStatus;
import org.dirimo.biblioteca.resources.stock.Stock;
import org.dirimo.biblioteca.resources.stock.StockService;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
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
    private final TemplateService templateService;
    private final MailService mailService;
    private final TemplateRepository templateRepository;
    private final JMSService jmsService;

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

        Book book = bookRepository.findByIdBookDetails(reservation.getBook().getId())
                .orElseThrow(() ->
                        new RuntimeException("Libro con id: " + reservation.getBook().getId() + " non trovato."));

        Customer customer = customerRepository.findByIdCustomerDetails(reservation.getCustomer().getId())
                .orElseThrow(() ->
                        new RuntimeException("Customer con id: " + reservation.getCustomer().getId() + " non trovato."));

        Optional<Stock> stockOptional = stockService.getByBook(book);
        Stock stock = stockOptional.orElseThrow(() ->
                new RuntimeException("Stock non trovato per il libro con id: " + book.getId())
        );

        if (stock.getAvailableCopies() <= 0) {
            throw new RuntimeException("Non ci sono copie disponibili per il libro con id: " + book.getId());
        }

        stock.handleQuantity(-1);

        reservation.setBook(book);
        reservation.setCustomer(customer);
        reservation.setResStartDate(date);

        Reservation savedReservation = reservationRepository.save(reservation);

        ReservationDTO reservationDTO = ReservationDTO.fromReservation(savedReservation);


        try {
            ObjectMapper objectMapper = new ObjectMapper();
            objectMapper.registerModule(new JavaTimeModule());
            String reservationJson = objectMapper.writeValueAsString(reservationDTO);
//            System.out.println(reservationJson);
            jmsService.sendMessage(reservationJson);
        } catch (Exception e) {
            e.printStackTrace();
        }

//        applicationEventPublisher.publishEvent(new OpenReservationEvent(this, savedReservation));

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


//        applicationEventPublisher.publishEvent(new ClosedReservationEvent(this, reservation));


        return reservation;
    }


    public void sendOpenReservationMail(Reservation reservation) {
        MailProperties mail = new MailProperties();

        Book book = bookRepository.findById(reservation.getBook().getId())
                .orElseThrow(() -> new RuntimeException("Libro con id: " + reservation.getBook().getId() + " non trovato."));

        Customer customer = customerRepository.findById(reservation.getCustomer().getId())
                .orElseThrow(() -> new RuntimeException("Cliente con id: " + reservation.getCustomer().getId() + " non trovato."));

        Template template = templateRepository.findByName("OpenReservationMailTemplate")
                .orElseThrow(() -> new RuntimeException("Template email non trovato: OpenReservationMailTemplate"));



        //possibile exception se uno dei campi NULL
        Map<String, Object> model = Map.of(
                "customerName", customer.getFirstName(),
                "bookTitle", book.getTitle(),
                "resEndDate", reservation.getResEndDate()
        );

        String body = templateService.compile(template, model);


        mail.setTo(customer.getEmail());
        mail.setSubject("Conferma prenotazione del libro " + book.getTitle());
        mail.setBody(body);

        mailService.sendEmail(mail);

        log.info("Email di conferma prenotazione inviata a: " + mail.getTo());

    }

    public void sendCloseReservationMail(Reservation reservation) {
        MailProperties mail = new MailProperties();

        Book book = reservation.getBook();

        Template template = templateRepository.findByName("CloseReservationMailTemplate")
                .orElseThrow(() -> new RuntimeException("Template email non trovato: CloseReservationMailTemplate"));


        Map<String, Object> model = Map.of(
                "customerName", reservation.getCustomer().getFirstName(),
                "bookTitle", book.getTitle(),
                "resEndDate", reservation.getResEndDate()
        );

        String body = templateService.compile(template, model);

        mail.setTo(reservation.getCustomer().getEmail());
        mail.setBody(body);
        mail.setSubject("Conferma chiusura prenotazione del libro " + book.getTitle());

        mailService.sendEmail(mail);

        log.info("Email di chiusura prenotazione inviata a: " + mail.getTo());

    }



    public void sendReminderEmails() {
        System.out.println("Esecuzione del metodo sendReminderEmails() - " + LocalDate.now());
        MailProperties mail = new MailProperties();
        LocalDate tomorrow = LocalDate.now().plusDays(1);

        // Recupera le prenotazioni che scadono domani
        List<Reservation> expiringReservations = reservationRepository.findByResEndDate(tomorrow);

        Template template = templateRepository.findByName("ReminderReservationMailTemplate")
                .orElseThrow(() -> new RuntimeException("Template email non trovato: ReminderReservationMailTemplate"));

        for (Reservation reservation : expiringReservations) {
            System.out.println("Esecuzione di for con Reservation reservation : expiringReservations - " + LocalDate.now());

            try {

                String userEmail = reservation.getCustomer().getEmail();

                String subject = "Promemoria Prenotazione libro" + reservation.getBook().getTitle() + "- Biblioteca";


                Map<String, Object> model = Map.of(
                        "customerName", reservation.getCustomer().getFirstName(),
                        "bookTitle", reservation.getBook().getTitle(),
                        "resEndDate", reservation.getResEndDate()
                );

                String body = templateService.compile(template, model);

                mail.setTo(userEmail);
                mail.setSubject(subject);
                mail.setBody(body);

                mailService.sendEmail(mail);

            } catch (Exception e) {
                System.err.println("Errore nell'invio dell'email per la prenotazione ID: "
                        + reservation.getId() + " - " + e.getMessage());
            }

        }
    }
}