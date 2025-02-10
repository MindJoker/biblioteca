package org.dirimo.biblioteca.resources.book;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class BookService {

    private final BookRepository bookRepository;

    public List<BookEntity> getAllBooks() {
        return bookRepository.findAll();
    }

    public Optional<BookEntity> getBookById(Long id) {
        return bookRepository.findById(id);
    }

    public BookEntity addBook(BookEntity book) {
        return bookRepository.save(book);
    }



//    public BookEntity addBook(BookEntity book) {
//        if (book.getStock() == null || book.getStock().getTotCopies() <= 0) {
//            StockEntity stock = new StockEntity();
//            stock.setTotCopies(1);
//            stock.setAvCopies(1);
//            stock.setBook(book);
//            book.setStock(stock);
//        }
//        return bookRepository.save(book);
//    }

//    public BookDTO convertToDTO(BookEntity book) {
//        StockDTO stockDTO = null;
//        if(book.getStock() != null) {
//            stockDTO = new StockDTO(
//            book.getStock().getId(),
//            book.getStock().getTotCopies(),
//            book.getStock().getAvCopies()
//            );
//        }
//
//        return new BookDTO(
//                book.getId(),
//                book.getIsbn(),
//                book.getTitle(),
//                book.getAuthor(),
//                book.getYear(),
//                book.getGenre(),
//                book.getPublisher(),
//                book.getLanguage(),
//                stockDTO
//        );
//    }
//
//    public List<BookDTO> getAllBooks() {
//        return bookRepository.findAll()
//                .stream()
//                .map(this::convertToDTO)
//                .toList();
//    }




    public BookEntity updateBook(Long id, BookEntity updatedBook) {
        BookEntity book = bookRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Libro con ID " + id + " non trovato"));

        book.setIsbn(updatedBook.getIsbn());
        book.setTitle(updatedBook.getTitle());
        book.setAuthor(updatedBook.getAuthor());
        book.setYear(updatedBook.getYear());
        book.setGenre(updatedBook.getGenre());
        book.setPublisher(updatedBook.getPublisher());
        book.setLanguage(updatedBook.getLanguage());

        return bookRepository.save(book);
    }

    public void deleteBook(Long id) {
        bookRepository.deleteById(id);
    }
}
