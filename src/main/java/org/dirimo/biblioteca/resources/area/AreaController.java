package org.dirimo.biblioteca.resources.area;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("Area")
public class AreaController {

    private final AreaService areaService;

    // Get all areas
    @GetMapping("/")
    public List<Area> getAll() {
        return areaService.getAll();
    }

    // Get area by id
    @GetMapping("/{id}")
    public Area getById(@PathVariable Long id) {
        return areaService.getById(id)
                .orElseThrow(() -> new RuntimeException("Area con Id " + id + " non trovata."));
    }

    // Create a area
    @PostMapping("/")
    @ResponseStatus(HttpStatus.CREATED)
    public Area create(@RequestBody Area area) {
        return areaService.create(area);
    }

    // Update a area
    @PutMapping("/{id}")
    public Area update(@PathVariable Long id, @RequestBody Area area) {
        return areaService.update(id, area);
    }

    // Delete a area
    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) {
        areaService.delete(id);
    }
}