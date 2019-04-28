package com.lemon.spring.component.messanging.impl;

import com.lemon.spring.component.messanging.Messenger;
import org.springframework.jms.core.JmsOperations;
import org.springframework.jms.core.MessageCreator;
import org.springframework.stereotype.Component;

import javax.inject.Inject;
import javax.jms.Destination;

@Component
public class JmsOperationMessengers implements Messenger {
    @Inject
    private JmsOperations jmsOperations;

    @Override
    public void send(Object message) {
        jmsOperations.convertAndSend(message);
    }

    @Override
    public void send(Destination destination, MessageCreator messageCreator) {
        jmsOperations.send(destination,messageCreator);
    }

    @Override
    public void send(String destination, MessageCreator messageCreator) {
        jmsOperations.send(destination,messageCreator);
    }
}
