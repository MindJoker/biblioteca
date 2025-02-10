package org.dirimo.biblioteca.resources.stock;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
@Repository
public interface StockRepository extends JpaRepository<StockEntity, Long> {
}
