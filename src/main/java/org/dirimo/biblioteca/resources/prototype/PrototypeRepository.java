package org.dirimo.biblioteca.resources.prototype;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface PrototypeRepository extends JpaRepository<Prototype, Long> {
    Optional<Prototype> findByName(String name);
}
