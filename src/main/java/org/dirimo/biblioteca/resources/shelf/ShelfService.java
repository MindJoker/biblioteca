package org.dirimo.biblioteca.resources.shelf;
import org.dirimo.biblioteca.resources.area.Area;
import lombok.RequiredArgsConstructor;
import org.dirimo.biblioteca.resources.area.AreaRepository;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ShelfService {

    private final ShelfRepository shelfRepository;
    private final AreaRepository areaRepository;

    public List<Shelf> getAllShelf() {



        return shelfRepository.findAll();
    }

    public Optional<Shelf> getShelfById(Long id) {
        return shelfRepository.findById(id);
    }

    public Shelf addShelf(Shelf shelf) {

        Long areaId = shelf.getArea().getId();
        Area fullArea = areaRepository.findById(areaId).orElseThrow(
                () -> new RuntimeException("Area Id "+ areaId +"not found")
        );



        shelf.setArea(fullArea);

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
