package org.dirimo.biblioteca.resources.book;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BookRepository extends JpaRepository<Book, Long> {
    Book findByIsbn(String isbn);
    Book findByTitle(String title);
    Book findByAuthorAndYear(String author, Integer year);
    List<Book> findBooksByShelfId(Long shelfId);
}
