package org.dirimo.biblioteca.resources.reservation;

import com.fasterxml.jackson.annotation.JsonIgnore;
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
import java.util.List;

@Entity
@Table(name = "Reservations")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Reservation extends BaseEntity {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

//    @Column(nullable = false)
//    private String username;
//
//    @Column(nullable = false)
//    private String email;

    @Column(name = "data_start", nullable = false)
    private LocalDate resStartDate;

    @Column(name = "data_end")
    private LocalDate resEndDate;

    @Column(name = "status")
    private ReservationStatus status;


    @Column(columnDefinition = "TEXT")
    private String notes;

    @ManyToOne
    @JoinColumn(name = "book_id", referencedColumnName = "id")
    private Book book;

    @ManyToOne
    @JoinColumn(name = "customer_id", referencedColumnName = "id", unique = true)
    private Customer customer;

    @Override
    protected void onCreate() {
        super.onCreate();
        status = ReservationStatus.ACTIVE;
        resEndDate = resStartDate.plusDays(1); //14
    }

}
