package org.dirimo.biblioteca.resources.book;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BookRepository extends JpaRepository<BookEntity, Long> {
    BookEntity findByIsbn(String isbn);
    BookEntity findByTitle(String title);
    BookEntity findByAuthorAndYear(String author, Integer year);
}
