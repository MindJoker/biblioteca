package org.dirimo.biblioteca.resources.book;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class BookService {

    private final BookRepository bookRepository;


    public List<Book> getAll() {
        return bookRepository.findAll();
    }

    public Optional<Book> getById(Long id) {
        return bookRepository.findById(id);
    }

    public Book create(Book book) {
        return bookRepository.save(book);
    }

    public List<Book> createBulk(List<Book> books) {
        return bookRepository.saveAll(books);
    }


    public Book update(Long id, Book book) {
        bookRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Libro con ID " + id + " non trovato"));
        return bookRepository.save(book);
    }

    public void delete(Long id) {

        bookRepository.deleteById(id);
    }

    public List<Book> getByShelfId(Long shelfId) {

        return bookRepository.findByShelfId(shelfId);
    }

}
