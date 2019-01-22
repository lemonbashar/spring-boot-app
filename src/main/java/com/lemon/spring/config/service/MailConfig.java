package com.lemon.spring.config.service;

import com.lemon.framework.properties.ApplicationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.mail.MailSender;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;

import javax.inject.Inject;
import java.util.Properties;

@Configuration
public class MailConfig {
    @Inject
    private ApplicationProperties properties;

    @Bean
    public JavaMailSender mailSender() {
        JavaMailSenderImpl mailSender=new JavaMailSenderImpl();
        mailSender.setHost(properties.settings.mail.host);
        mailSender.setPort(properties.settings.mail.port);
        mailSender.setUsername(properties.settings.mail.username);
        mailSender.setPassword(properties.settings.mail.password);
        Properties props=mailSender.getJavaMailProperties();
        props.put("mail.debug", ""+properties.settings.mail.debug);

        if(properties.settings.mail.protocol!=null && properties.settings.mail.protocol.equalsIgnoreCase("smtp")) {
            props.put("mail.transport.protocol", ""+properties.settings.mail.protocol);
            props.put("mail.smtp.auth", ""+properties.settings.mail.smtp.auth);
            props.put("mail.smtp.starttls.enable", ""+properties.settings.mail.smtp.starttlsEnable);
            props.put("mail.smtp.ssl",properties.settings.mail.smtp.ssl?"yes":"no");
            props.put("mail.smtp.ssl.trust",""+properties.settings.mail.smtp.sslTrust);
        }
        //mailSender.setJavaMailProperties(props);
        return mailSender;
    }

}
