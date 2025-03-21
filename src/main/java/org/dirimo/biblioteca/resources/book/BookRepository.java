package org.dirimo.biblioteca.resources.book;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface BookRepository extends JpaRepository<Book, Long> {
    Book findByIsbn(String isbn);
    Book findByTitle(String title);
    Book findByAuthorAndYear(String author, Integer year);
    List<Book> findByShelfId(Long shelfId);
}
