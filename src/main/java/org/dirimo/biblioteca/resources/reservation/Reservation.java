package org.dirimo.biblioteca.resources.reservation;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.dirimo.biblioteca.resources.book.Book;
import org.dirimo.biblioteca.resources.common.BaseEntity;
import org.dirimo.biblioteca.resources.reservation.enumerated.ReservationStatus;

import java.time.LocalDate;

@Entity
@Table(name = "Reservations")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Reservation extends BaseEntity {


    @Column(nullable = false)
    private String username;

    @Column(nullable = false)
    private String email;

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

    //@PreUpdate
    @PrePersist
    protected void onCreate() {
        status = ReservationStatus.ACTIVE;
        resStartDate = LocalDate.now();
        resEndDate = resStartDate.plusDays(1); //14
    }

}
