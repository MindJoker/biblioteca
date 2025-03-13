package org.dirimo.biblioteca.resources.book;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.dirimo.biblioteca.common.BaseEntity;
import org.dirimo.biblioteca.resources.shelf.Shelf;
import org.dirimo.biblioteca.resources.stock.Stock;
import org.dirimo.biblioteca.resources.reservation.Reservation;

import java.util.List;

@Entity
@Table(name = "Books")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor

public class Book extends BaseEntity {

    @Id
    @Column(name = "ID")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "ISBN", nullable = false, unique = true, length = 17)
    private String isbn;

    @Column(name = "TITLE", nullable = false)
    private String title;

    @Column(name = "AUTHOR", nullable = false)
    private String author;

    @Column(name = "YEAR", nullable = false)
    private Integer year;

    @Column(name = "GENRE", nullable = false, length = 100)
    private String genre;

    @Column(name = "PUBLISHER", nullable = false)
    private String publisher;

    @Column(name = "LANGUAGE", nullable = false, length = 50)
    private String language;

    @Column(name = "DESCRIPTION", columnDefinition = "TEXT")
    private String description;


    @OneToMany(mappedBy = "book")
    @JsonIgnore
    private List<Stock> stock; //liste in ignore

    @OneToMany(mappedBy = "book")
    @JsonIgnore
    private List<Reservation> reservation;

    @ManyToOne
    @JoinColumn(name = "SHELF_ID")
    private Shelf shelf;


}
