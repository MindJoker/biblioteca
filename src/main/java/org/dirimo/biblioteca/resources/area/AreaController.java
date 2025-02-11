package org.dirimo.biblioteca.resources.area;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/area")
public class AreaController {
    private final AreaService areaService;

    @GetMapping
    public List<Area> getAllAreas() {
        return areaService.getAllAreas();
    }

    @GetMapping("/{id}")
    public Area getAreaById(@PathVariable Long id) {
        return areaService
                .getAreaById(id)
                .orElseThrow(()
                        -> new RuntimeException("Area non trovata"));
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Area addArea(@RequestBody Area area) {
        return areaService.addArea(area);
    }

    @PutMapping("/{id}")
    public Area updateArea(@PathVariable Long id, @RequestBody Area area) {
        return areaService.updateArea(id, area);
    }
    @DeleteMapping("/{id}")
    public void deleteArea(@PathVariable Long id) {
        areaService.deleteArea(id);
    }
}
