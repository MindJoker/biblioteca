package org.dirimo.biblioteca.resources.reservation;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.dirimo.biblioteca.resources.book.Book;
import org.dirimo.biblioteca.common.BaseEntity;
import org.dirimo.biblioteca.resources.customer.Customer;
import org.dirimo.biblioteca.resources.reservation.enumerated.ReservationStatus;

import java.time.LocalDate;


@Entity
@Table(name = "Reservations")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Reservation extends BaseEntity {


    @Id
    @Column(name = "ID")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "DATA_START", nullable = false)
    private LocalDate resStartDate;

    @Column(name = "DATA_END")
    private LocalDate resEndDate;

    @Column(name = "STATUS")
    private ReservationStatus status;


    @Column(name = "NOTES", columnDefinition = "TEXT")
    private String notes;

    @ManyToOne
    @JoinColumn(name = "BOOK_ID", referencedColumnName = "id")
    private Book book;

    @ManyToOne
    @JoinColumn(name = "CUSTOMER_ID", referencedColumnName = "id")
    private Customer customer;

    @Override
    protected void onCreate() {
        super.onCreate();
        status = ReservationStatus.ACTIVE;
        resEndDate = resStartDate.plusDays(1); //14
    }

}
