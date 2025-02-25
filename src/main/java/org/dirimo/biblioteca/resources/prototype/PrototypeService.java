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

    public String compile(Prototype prototype, Map<String, Object> model) {
        try {
            Template template = velocityEngine.getTemplate(prototype.getName());
            VelocityContext context = new VelocityContext();
            model.forEach(context::put);

            StringWriter writer = new StringWriter();
            template.merge(context, writer);
            return writer.toString();
        } catch (Exception e) {
            throw new RuntimeException("Errore nella compilazione del template: " + prototype.getName(), e);
        }
    }

    public Prototype create(Prototype prototype) {
        return prototypeRepository.save(prototype);
    }

    public List<Prototype> getAll() {
        return prototypeRepository.findAll();
    }

    public Optional<Prototype> getById(Long id) {
        return prototypeRepository.findById(id);
    }

    public Prototype update(Long id, Prototype updatedPrototype) {
        Prototype prototype = prototypeRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Prototype con ID: " + id + " non trovato"));

        prototype.setName(updatedPrototype.getName());
        prototype.setType(updatedPrototype.getType());
        prototype.setBodyFromString(updatedPrototype.getBodyString());

        return prototypeRepository.save(prototype);
    }

    public void delete(Long id) {
        prototypeRepository.deleteById(id);
    }
}

