package org.dirimo.biblioteca.resources.shelf;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.dirimo.biblioteca.resources.area.AreaEntity;
import org.dirimo.biblioteca.resources.book.BookEntity;

@Entity
@Table(name = "shelf")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ShelfEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 100)
    private String name;

    @ManyToOne
    @JoinColumn(name = "area_id")
    private AreaEntity area;

    @OneToOne
    @JoinColumn(name = "book_id", referencedColumnName = "id", unique = true)
    private BookEntity book;
}