package org.dirimo.biblioteca.resources.shelf;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ShelfService {

    private final ShelfRepository shelfRepository;

    public List<Shelf> getAllShelf() {
        return shelfRepository.findAll();
    }

    public Optional<Shelf> getShelfById(Long id) {
        return shelfRepository.findById(id);
    }

    public Shelf addShelf(Shelf shelf) {
        return shelfRepository.save(shelf);
    }

    public Shelf updateShelf(Long id, Shelf shelf) {
        shelfRepository.findById(id).orElseThrow(() -> new RuntimeException("Scaffale Id " + id + " non trovato"));
        shelf.setId(id);
        return shelfRepository.save(shelf);
    }

    public void deleteShelf(Long id) {
        shelfRepository.deleteById(id);
    }
}
