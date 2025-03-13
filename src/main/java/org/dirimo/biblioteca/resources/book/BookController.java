package org.dirimo.biblioteca.resources.book;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@Slf4j
@Transactional
@RestController
@RequiredArgsConstructor
@RequestMapping("Book")
public class BookController {

    private final BookService bookService;

    @GetMapping("/")
    public List<Book> getAll() {
        return bookService.getAll();
    }

    @GetMapping("/{id}")
    public Book getById(@PathVariable Long id) {
        return bookService.getById(id)
                .orElseThrow(() -> new RuntimeException("Libro non trovato"));
    }

    @GetMapping("/shelf")
    public List<Book> getByShelfId(@RequestParam(required = true) Long shelfId) {
        return bookService.getByShelfId(shelfId);
    }

    @PostMapping("/")
    @ResponseStatus(HttpStatus.CREATED)
    public Book create(@RequestBody Book book) {
        return bookService.create(book);
    }

    @PostMapping("/bulk")
    @ResponseStatus(HttpStatus.CREATED)
    public List<Book> createBulk(@RequestBody List<Book> books) {
        return bookService.createBulk(books);
    }

    @PutMapping("/{id}")
    public Book update(@PathVariable Long id, @RequestBody Book book) {
        return bookService.update(id, book);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) {
        bookService.delete(id);
    }
}

