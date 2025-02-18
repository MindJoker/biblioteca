package org.dirimo.biblioteca.resources.shelf;

import lombok.RequiredArgsConstructor;
import org.dirimo.biblioteca.resources.area.Area;
import org.dirimo.biblioteca.resources.area.AreaRepository;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ShelfService {

    private final ShelfRepository shelfRepository;
    private final AreaRepository areaRepository;

    @Value("${library.maxReservationPerUser}")
    private int maxReservation;

    // Get all shelves
    public List<Shelf> getAll() {
        return shelfRepository.findAll();
    }

    // Get a shelf by ID
    public Optional<Shelf> getShelfById(Long id) {
        return shelfRepository.findById(id);
    }

    // Get shelves by area ID
    public List<Shelf> getByAreaId(Long areaId) {
        return shelfRepository.findByAreaId(areaId);
    }

    // Add a new shelf
    public Shelf create(Shelf shelf) {
        Long areaId = shelf.getArea().getId();
        Area area = areaRepository.findById(areaId)
                .orElseThrow(() -> new RuntimeException("Zona con id: " +areaId+ " non trovata."));
        shelf.setArea(area);
        return shelfRepository.save(shelf);
    }

    // Update a shelf
    public Shelf update(Long id, Shelf shelf) {
        shelfRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Scaffale con id: " + id + " non trovato."));
        shelf.setId(id);
        return shelfRepository.save(shelf);
    }

    // Delete a shelf by ID
    public void delete(Long id) {
        shelfRepository.deleteById(id);
    }
}