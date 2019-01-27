package com.lemon.spring.config.messanging.jms;

import com.lemon.spring.config.Constants;
import org.apache.activemq.ActiveMQConnectionFactory;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

@SuppressWarnings("UnnecessaryLocalVariable")
@Profile({Constants.PROFILE_JMS,Constants.PROFILE_MESSANGING})
@Configuration
public class JMSConfig {
    private static final String brockerUrl="tcp://localhost:6754";

    public ActiveMQConnectionFactory jmsBrocker() {
        ActiveMQConnectionFactory connectionFactory=new ActiveMQConnectionFactory(brockerUrl);

        return connectionFactory;
    }
}