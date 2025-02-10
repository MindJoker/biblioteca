package org.dirimo.biblioteca.resources.book;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/books")
public class BookController {

    private final BookService bookService;

    @GetMapping
    public List<BookEntity> getAllBooks() {
        return bookService.getAllBooks();
    }

    @GetMapping("/{id}")
    public BookEntity getBookById(@PathVariable Long id) {
        return bookService.getBookById(id)
                .orElseThrow(() -> new RuntimeException("Libro non trovato"));
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public BookEntity addBook(@RequestBody BookEntity book) {
        return bookService.addBook(book);
    }

//    @PostMapping
//    @ResponseStatus(HttpStatus.CREATED)
//    public BookService createBook(@RequestBody BookService book) {
//        if (book.getStock() == null || book.getStock() <= 0) {
//            book.setStock(1);  // Imposta almeno 1 se il valore è null o inferiore a 1
//        }
//        return bookRepository.save(book);
//    }

    @PutMapping("/{id}")
    public BookEntity updateBook(@PathVariable Long id, @RequestBody BookEntity book) {
        return bookService.updateBook(id, book);
    }

    @DeleteMapping("/{id}")
    public void deleteBook(@PathVariable Long id) {
        bookService.deleteBook(id);
    }
}

