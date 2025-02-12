package org.dirimo.biblioteca.resources.book;

import lombok.RequiredArgsConstructor;
import org.dirimo.biblioteca.resources.shelf.Shelf;
import org.dirimo.biblioteca.resources.shelf.ShelfRepository;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class BookService {

    private final BookRepository bookRepository;
    private final ShelfRepository shelfRepository;

    public List<Book> getAllBooks() {
        return bookRepository.findAll();
    }

    public Optional<Book> getBookById(Long id) {
        return bookRepository.findById(id);
    }

    public Book addBook(Book book) {

        Long shelfId = book.getShelf().getId();
        Shelf fullShelf = shelfRepository.findById(shelfId)
                .orElseThrow(() -> new RuntimeException("Shelf Id " + shelfId + " not found"));

        book.setShelf(fullShelf);

        return bookRepository.save(book);
    }



    public Book updateBook(Long id, Book book) {
        bookRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Libro con ID " + id + " non trovato"));
        book.setId(id);
        return bookRepository.save(book);
    }

    public void deleteBook(Long id) {
        bookRepository.deleteById(id);
    }

    public List<Book> getBooksByShelfId(Long shelfId) {

        return bookRepository.findBooksByShelfId(shelfId);
    }

}
