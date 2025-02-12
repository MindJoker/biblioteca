package org.dirimo.biblioteca.resources.shelf;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.dirimo.biblioteca.resources.area.Area;
import org.dirimo.biblioteca.resources.book.Book;

import java.util.List;

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

    @OneToMany(mappedBy = "shelf")
    @JsonIgnore
    private List<Book> book;
}