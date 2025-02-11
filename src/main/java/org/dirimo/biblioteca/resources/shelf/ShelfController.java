package org.dirimo.biblioteca.resources.shelf;


import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.Optional;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/shelf")
public class ShelfController {

    private final ShelfService shelfService;

    @GetMapping
    public List<Shelf> getAllShelf() {
        return shelfService.getAllShelf();
    }

    @GetMapping("/{id}")
    public Optional<Shelf> getShelfById(@PathVariable Long id) {
        return shelfService.getShelfById(id);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Shelf createShelf(@RequestBody Shelf shelf) {
        return shelfService.addShelf(shelf);
    }

    @PutMapping("/{id}")
    public Shelf updateShelf(@PathVariable Long id, @RequestBody Shelf shelf) {
        return shelfService.updateShelf(id, shelf);
    }

    @DeleteMapping("/{id}")
    public void deleteShelf(@PathVariable Long id) {
        shelfService.deleteShelf(id);
    }
}
