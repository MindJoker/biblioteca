package org.dirimo.biblioteca.resources.area;


import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.dirimo.biblioteca.common.BaseEntity;
import org.dirimo.biblioteca.resources.shelf.Shelf;

import java.util.List;


@Entity
@Table(name = "Areas")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Area extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 100)
    private String name;

    @Column(columnDefinition = "TEXT")
    private String description;

    @OneToMany(mappedBy = "area")
    @JsonIgnore
    private List<Shelf> shelves;

}