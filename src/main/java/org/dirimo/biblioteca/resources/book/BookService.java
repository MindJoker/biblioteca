package org.dirimo.biblioteca.resources.book;

import lombok.RequiredArgsConstructor;
import org.dirimo.biblioteca.resources.stock.Stock;
import org.dirimo.biblioteca.resources.stock.StockService;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class BookService {

    private final BookRepository bookRepository;
    private final StockService stockService;


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
        List<Book> savedBooks = bookRepository.saveAll(books); // Salva prima i libri

        for (Book book : savedBooks) {
            Stock stock = new Stock();
            stock.setBook(book);
            stock.setTotalCopies(5);
            stock.setAvailableCopies(5);

            stockService.create(stock); //
        }

        return savedBooks;
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
