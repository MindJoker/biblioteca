package org.dirimo.biblioteca.resources.book;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.dirimo.biblioteca.resources.stock.Stock;
import org.dirimo.biblioteca.resources.reservation.Reservation;

import java.util.List;

@Entity
@Table(name = "book")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Book {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true, length = 13)
    private String isbn;

    @Column(nullable = false, length = 255)
    private String title;

    @Column(nullable = false, length = 255)
    private String author;

    @Column(nullable = false)
    private Integer year;

    @Column(nullable = false, length = 100)
    private String genre;

    @Column(nullable = false, length = 255)
    private String publisher;

    @Column(nullable = false, length = 50)
    private String language;

    @OneToMany(mappedBy = "book", cascade = CascadeType.ALL)
    @JsonIgnore
    private List<Stock> stock; //liste in ignore

    @OneToMany(mappedBy = "book")
    @JsonIgnore
    private List <Reservation> reservation;
}
