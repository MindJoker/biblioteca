package org.dirimo.biblioteca.resources.prototype;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PrototypeRepository extends JpaRepository<Prototype, Long> {
}
