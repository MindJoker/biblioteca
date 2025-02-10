package org.dirimo.biblioteca.resources.area;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;


import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class AreaService {
    private final AreaRepository areaRepository;

    public List<AreaEntity> getAllAreas()
    {
        return areaRepository.findAll();
    }

    public Optional<AreaEntity> getAreaById(Long id){
        return areaRepository.findById(id);
    }

    public AreaEntity addArea(AreaEntity area){
        return areaRepository.save(area);
    }

    public AreaEntity updateArea(Long id,AreaEntity updatedArea){
        AreaEntity area = areaRepository.findById(id).orElseThrow(() -> new RuntimeException("Area con Id" + id + " non esiste"));
        area.setName(updatedArea.getName());
        area.setDescription(updatedArea.getDescription());
        return areaRepository.save(area);
    }

    public void deleteArea(Long id){
        areaRepository.deleteById(id);
    }
}
