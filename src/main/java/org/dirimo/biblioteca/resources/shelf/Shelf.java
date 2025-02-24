package org.dirimo.biblioteca.resources.shelf;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.dirimo.biblioteca.resources.area.Area;
import org.dirimo.biblioteca.resources.book.Book;
import org.dirimo.biblioteca.common.BaseEntity;

import java.util.List;

@Entity
@Table(name = "Shelfs")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Shelf extends BaseEntity {

    @Id
    @Column(name = "ID")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "NAME", nullable = false, length = 100)
    private String name;

    @ManyToOne
    @JoinColumn(name = "AREA_ID")
    private Area area;

    @OneToMany(mappedBy = "shelf")
    @JsonIgnore
    private List<Book> book;
}