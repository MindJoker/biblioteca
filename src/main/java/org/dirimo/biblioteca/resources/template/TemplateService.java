package org.dirimo.biblioteca.resources.template;

import lombok.RequiredArgsConstructor;

import org.apache.velocity.VelocityContext;
import org.apache.velocity.app.VelocityEngine;
import org.springframework.stereotype.Service;
import java.io.StringWriter;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class TemplateService {

    private final TemplateRepository templateRepository;
    private final VelocityEngine velocityEngine;


    public String compile(Template template, Map<String, Object> model) {
        try {
            String templateContent = template.getBodyString();
            VelocityContext context = new VelocityContext();
            model.forEach(context::put);
            StringWriter writer = new StringWriter();
            velocityEngine.evaluate(context, writer, template.getName(), templateContent);
            return writer.toString();
        } catch (Exception e) {
            throw new RuntimeException("Errore nella compilazione del template: " + template.getName(), e);
        }
    }

    public Template create(Template template) {
        return templateRepository.save(template);
    }

    public List<Template> getAll() {
        return templateRepository.findAll();
    }

    public Optional<Template> getById(Long id) {
        return templateRepository.findById(id);
    }

    public Template update(Long id, Template updatedTemplate) {
        Template template = templateRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Template con ID: " + id + " non trovato"));

        template.setName(updatedTemplate.getName());
        template.setType(updatedTemplate.getType());
        template.setBodyFromString(updatedTemplate.getBodyString());

        return templateRepository.save(template);
    }

    public void delete(Long id) {
        templateRepository.deleteById(id);
    }
}

