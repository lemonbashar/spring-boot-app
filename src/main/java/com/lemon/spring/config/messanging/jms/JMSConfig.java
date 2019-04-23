package com.lemon.spring.config.messanging.jms;

import com.lemon.framework.properties.spring.ApplicationProperties;
import com.lemon.spring.config.Constants;
import org.apache.activemq.ActiveMQConnectionFactory;
import org.apache.activemq.command.ActiveMQQueue;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.jms.support.converter.MappingJackson2MessageConverter;
import org.springframework.jms.support.converter.MessageConverter;

import javax.inject.Inject;
import javax.jms.ConnectionFactory;
import javax.jms.Destination;

@SuppressWarnings("UnnecessaryLocalVariable")
@Profile({Constants.PROFILE_JMS, Constants.PROFILE_MESSANGING})
@Configuration
public class JMSConfig {
    @Inject
    private ApplicationProperties applicationProperties;

    private final String brokerUrl;

    public JMSConfig() {
        this.brokerUrl="tcp://localhost:"+applicationProperties.settings.messenging.jms.port;
    }

    @Bean
    public JmsTemplate jmsTemplate(ConnectionFactory connectionFactory,Destination defaultQueue) {
        JmsTemplate jmsTemplate=new JmsTemplate(connectionFactory);
        jmsTemplate.setDefaultDestination(defaultQueue);
        return jmsTemplate;
    }

    @Bean
    public MessageConverter messageConverter() {
        return new MappingJackson2MessageConverter();
    }

    @Bean
    public ActiveMQConnectionFactory jmsBroker() {
        ActiveMQConnectionFactory connectionFactory = new ActiveMQConnectionFactory(brokerUrl);

        return connectionFactory;
    }

    @Bean(name = "defaultQueue")
    public Destination defaultQueue() {
        return new ActiveMQQueue(applicationProperties.settings.messenging.jms.defaultQueueName);
    }


    @Bean(name = "defaultTopic")
    public Destination defaultTopic() {
        return new ActiveMQQueue(applicationProperties.settings.messenging.jms.defaultTopicName);
    }
}
