package org.dirimo.biblioteca.resources.shelf;

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
@RequestMapping("Shelf")
public class ShelfController {

    private final ShelfService shelfService;

    // Get all shelves
    @GetMapping("/")
    public List<Shelf> getAll() {
        return shelfService.getAll();
    }

    // Get shelf by ID
    @GetMapping("/{id}")
    public Shelf getBookById(@PathVariable Long id) {
        return shelfService.getShelfById(id)
                .orElseThrow(() -> new RuntimeException("Scaffale con id " + id + " non trovato."));
    }

    // Get shelves by area ID
    @GetMapping("/area")
    public List<Shelf> getByAreaId(@RequestParam Long areaId) {
        return shelfService.getByAreaId(areaId);
    }

    // Add a new shelf
    @PostMapping("/")
    @ResponseStatus(HttpStatus.CREATED)
    public Shelf create(@RequestBody Shelf shelf) {
        return shelfService.create(shelf);
    }

    // Update a shelf
    @PutMapping("/{id}")
    public Shelf update(@PathVariable Long id, @RequestBody Shelf shelf) {
        return shelfService.update(id, shelf);
    }

    // Delete a shelf
    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) {
        shelfService.delete(id);
    }
}