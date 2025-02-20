package org.dirimo.biblioteca.resources.area;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
public class AreaService {

    private final AreaRepository areaRepository;

    // Get all areas
    public List<Area> getAll() {
        return areaRepository.findAll();
    }

    // Get area by ID
    public Optional<Area> getById(Long id) {
        return areaRepository.findById(id);
    }

    // Add new area
    public Area create(Area area) {
        return areaRepository.save(area);
    }

    // Update area
    public Area update(Long id, Area area) {
        areaRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Zona con id: " + id + " non trovata."));
        return areaRepository.save(area);
    }

    //Delete area
    public void delete(Long id) {
        areaRepository.deleteById(id);
    }

}