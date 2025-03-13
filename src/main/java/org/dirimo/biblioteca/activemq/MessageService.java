package org.dirimo.biblioteca.activemq;

import lombok.RequiredArgsConstructor;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class MessageService {
    private static final String QUEUE_NAME = "sample-queue";

    private final JmsTemplate jmsTemplate;

    public void sendMessage(String message) {
        jmsTemplate.convertAndSend(QUEUE_NAME, message);
    }
}
