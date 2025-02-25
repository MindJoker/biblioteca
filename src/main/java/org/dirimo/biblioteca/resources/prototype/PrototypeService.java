package org.dirimo.biblioteca.resources.prototype;

import lombok.RequiredArgsConstructor;

import org.apache.velocity.Template;
import org.apache.velocity.VelocityContext;
import org.apache.velocity.app.VelocityEngine;
import org.springframework.stereotype.Service;

import java.io.StringWriter;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class PrototypeService {

    private final PrototypeRepository prototypeRepository;

    private final VelocityEngine velocityEngine;

    public String compile(Prototype prototypeName, Map<String, Object> model) {
        Template template = velocityEngine.getTemplate(prototypeName.getName());
        VelocityContext context = new VelocityContext();

        model.forEach(context::put);

        StringWriter writer = new StringWriter();
        template.merge(context, writer);

        return writer.toString();
    }

    // Get all prototypes
    public List<Prototype> getAll() {
        return prototypeRepository.findAll();
    }

    // Get a prototype by ID
    public Optional<Prototype> getById(Long id) {
        return prototypeRepository.findById(id);
    }

    // Add a new prototype
    public Prototype create(Prototype prototype) {
        return prototypeRepository.save(prototype);
    }

    //Update a prototype
    public Prototype update(Long id, Prototype prototype) {
        getById(id)
                .orElseThrow(() -> new RuntimeException("Template con ID: " + id + " non trovato"));
        return prototypeRepository.save(prototype);
    }

    // Delete a prototype
    public void delete(Long id) {
        prototypeRepository.deleteById(id);
    }
}

