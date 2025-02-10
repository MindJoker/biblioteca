package org.dirimo.biblioteca.resources.shelf;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ShelfService {

    private final ShelfRepository shelfRepository;

    public List<ShelfEntity> getAllShelf() {
        return shelfRepository.findAll();
    }

    public Optional<ShelfEntity> getShelfById(Long id) {
        return shelfRepository.findById(id);
    }

    public ShelfEntity addShelf(ShelfEntity shelf) {
        return shelfRepository.save(shelf);
    }

    public ShelfEntity updateShelf(Long id, ShelfEntity updatedShelf) {
        ShelfEntity shelf = shelfRepository.findById(id)
                                            .orElseThrow(() -> new RuntimeException("Scaffale Id " + id + " non trovato"));
        shelf.setName(updatedShelf.getName());
        shelf.setArea(updatedShelf.getArea());
        shelf.setBook(updatedShelf.getBook());
        return shelfRepository.save(shelf);
    }

    public void deleteShelf(Long id) {
        shelfRepository.deleteById(id);
    }
}
