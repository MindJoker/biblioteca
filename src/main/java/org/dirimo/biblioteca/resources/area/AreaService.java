package org.dirimo.biblioteca.resources.area;

import lombok.RequiredArgsConstructor;
import org.dirimo.biblioteca.resources.shelf.Shelf;
import org.dirimo.biblioteca.resources.shelf.ShelfService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;


import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class AreaService {
//    @Value("${library.shelfNames}")
//    private List<String> shelfNames;
    private final AreaRepository areaRepository;
//    private final ShelfService shelfService;

    public List<Area> getAllAreas()
    {
        return areaRepository.findAll();
    }

    public Optional<Area> getAreaById(Long id){
        return areaRepository.findById(id);
    }

    public Area addArea(Area area){
//      Chiedere a dario e lara - con request param
//        String areaName = area.getName();
//        for(int i=0; i<shelfNum; i++){
//            Shelf shelf = new Shelf();
//            shelf.setName(areaName + shelfNames.get(i));
//            shelfService.addShelf(shelf);
//        }
        
        return areaRepository.save(area);
    }

    public Area updateArea(Long id, Area area){
        areaRepository.findById(id).orElseThrow(() -> new RuntimeException("Area con Id" + id + " non esiste"));
        area.setId(id);
        return areaRepository.save(area);
    }

    public void deleteArea(Long id){
        areaRepository.deleteById(id);
    }
}
