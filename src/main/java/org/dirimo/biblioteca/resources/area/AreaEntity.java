package org.dirimo.biblioteca.resources.area;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.dirimo.biblioteca.resources.shelf.ShelfEntity;

import java.util.List;


@Entity
@Table(name = "area")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class AreaEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 100)
    private String name;

    @Column(nullable = true, columnDefinition = "TEXT")
    private String description;

    @OneToMany(mappedBy = "area")
    private List<ShelfEntity> shelves;

}