package org.dirimo.biblioteca.resources.shelf;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
@Repository
public interface ShelfRepository extends JpaRepository<Shelf, Long> {
}
