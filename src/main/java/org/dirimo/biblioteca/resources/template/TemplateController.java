package org.dirimo.biblioteca.resources.template;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@Transactional
@RestController
@RequiredArgsConstructor
@RequestMapping("Template")
public class TemplateController {

    private final TemplateService templateService;

    // Get all prototypes
    @GetMapping("/")
    public List<Template> getAll() {
        return templateService.getAll();
    }

    // Get template by ID
    @GetMapping("/{id}")
    public Template getById(@PathVariable Long id) {
        return templateService.getById(id)
                .orElseThrow(() -> new RuntimeException("Template con id " + id + " non trovato."));
    }

    // Create template
    @PostMapping("/")
    @ResponseStatus(HttpStatus.CREATED)
    public Template create(@RequestBody Template template) {
        return templateService.create(template);
    }

    // Update a template
    @PutMapping("/{id}")
    public Template update(@PathVariable Long id, @RequestBody Template template) {
        return templateService.update(id, template);
    }

    // Delete a shelf
    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) {
        templateService.delete(id);
    }
}
