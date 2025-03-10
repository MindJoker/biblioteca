package org.dirimo.biblioteca.resources.block;

import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface BlockRepository extends JpaRepository<Block, Long> {
    Block findTopByOrderByIdDesc();

    @Modifying
    @Transactional
    @Query(value = "TRUNCATE TABLE blocks", nativeQuery = true)
    void truncateTable();
}
