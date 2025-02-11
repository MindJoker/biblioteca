package org.dirimo.biblioteca.resources.shelf;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.dirimo.biblioteca.resources.area.Area;
import org.dirimo.biblioteca.resources.book.Book;

@Entity
@Table(name = "shelf")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Shelf {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 100)
    private String name;

    @ManyToOne
    @JoinColumn(name = "area_id")
    private Area area;

//    @OneToOne
//    @JoinColumn(name = "book_id", referencedColumnName = "id", unique = true)
//    private Book book;
}