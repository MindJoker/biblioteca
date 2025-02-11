package org.dirimo.biblioteca.resources.area;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;


import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class AreaService {
    private final AreaRepository areaRepository;

    public List<Area> getAllAreas()
    {
        return areaRepository.findAll();
    }

    public Optional<Area> getAreaById(Long id){
        return areaRepository.findById(id);
    }

    public Area addArea(Area area){
        return areaRepository.save(area);
    }

    public Area updateArea(Long id, Area updatedArea){
        Area area = areaRepository.findById(id).orElseThrow(() -> new RuntimeException("Area con Id" + id + " non esiste"));
        area.setName(updatedArea.getName());
        area.setDescription(updatedArea.getDescription());
        return areaRepository.save(area);
    }

    public void deleteArea(Long id){
        areaRepository.deleteById(id);
    }
}
