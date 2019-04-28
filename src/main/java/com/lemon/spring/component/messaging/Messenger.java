package com.lemon.spring.component.messaging;

import org.springframework.jms.core.MessageCreator;

import javax.jms.Destination;

public interface Messenger {
    void send(Object  message);

    void send(Destination destination, MessageCreator messageCreator);


    void send(String destination, MessageCreator messageCreator);
}
