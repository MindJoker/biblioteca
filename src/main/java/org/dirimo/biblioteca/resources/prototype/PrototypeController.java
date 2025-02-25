package org.dirimo.biblioteca.resources.prototype;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@Transactional
@RestController
@RequiredArgsConstructor
@RequestMapping("Prototype")
public class PrototypeController {

    private final PrototypeService prototypeService;

    // Get all prototypes
    @GetMapping("/")
    public List<Prototype> getAll() {
        return prototypeService.getAll();
    }

    // Get prototype by ID
    @GetMapping("/{id}")
    public Prototype getById(@PathVariable Long id) {
        return prototypeService.getById(id)
                .orElseThrow(() -> new RuntimeException("Prototype con id " + id + " non trovato."));
    }

    // Create prototype
    @PostMapping("/")
    public Prototype create(@RequestBody Prototype prototype) {
        return prototypeService.create(prototype);
    }

    // Update a prototype
    @PutMapping("/{id}")
    public Prototype update(@PathVariable Long id, @RequestBody Prototype prototype) {
        return prototypeService.update(id, prototype);
    }

    // Delete a shelf
    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) {
        prototypeService.delete(id);
    }
}
