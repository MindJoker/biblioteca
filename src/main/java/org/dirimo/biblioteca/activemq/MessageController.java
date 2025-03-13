package org.dirimo.biblioteca.activemq;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class MessageController {

    private final MessageService messageService;

    @PostMapping("/send")
    public String sendMessage(@RequestBody String message) {
        messageService.sendMessage(message);
        return "Message sent" + message;
    }
}
