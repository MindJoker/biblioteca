package org.dirimo.biblioteca.mail;

import org.apache.velocity.Template;
import org.apache.velocity.VelocityContext;
import org.apache.velocity.app.VelocityEngine;
import org.springframework.stereotype.Service;

import java.io.StringWriter;
import java.util.Map;

@Service
public class EmailTemplateService {

    private final VelocityEngine velocityEngine;

    public EmailTemplateService(VelocityEngine velocityEngine) {
        this.velocityEngine = velocityEngine;
    }

    public String generateEmail(String templateName, Map<String, Object> model) {
        Template template = velocityEngine.getTemplate("templates/" + templateName + ".vm");
        VelocityContext context = new VelocityContext();

        // Aggiungiamo i dati al template
        model.forEach(context::put);

        StringWriter writer = new StringWriter();
        template.merge(context, writer);

        return writer.toString();
    }
}

