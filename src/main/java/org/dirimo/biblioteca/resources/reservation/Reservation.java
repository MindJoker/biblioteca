package org.dirimo.biblioteca.resources.reservation;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.dirimo.biblioteca.resources.book.Book;
import org.dirimo.biblioteca.resources.reservation.reservEnum.ReservationStatus;

import java.time.LocalDate;

@Entity
@Table(name = "Reservations")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Reservation {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 255)
    private String username;


    @Column(name ="data_start", nullable = false)
    private LocalDate resStartDate;

    @Column(name="data_end")
    private LocalDate resEndDate;

    @Column(name = "status")
    private ReservationStatus status;


    @Column(columnDefinition = "TEXT")
    private String notes;

    @ManyToOne
    @JoinColumn(name = "book_id", referencedColumnName = "id", unique = true)
    private Book book;

    //reservation - stock
}

//classe embedded da vedere