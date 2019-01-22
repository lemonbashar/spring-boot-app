package com.lemon.spring.config.service;

import com.lemon.spring.Application;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.test.context.junit4.SpringRunner;

import javax.inject.Inject;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = Application.class)
public class MailTest {
    @Inject
    private JavaMailSender mailSender;

    @Test
    public void mailSend() {
        SimpleMailMessage simpleMailMessage=new SimpleMailMessage();
        simpleMailMessage.setSubject("Test Purpose");
        simpleMailMessage.setText("No Text Found");
        simpleMailMessage.setCc("lemon.bashar@gmail.com");
        simpleMailMessage.setTo("simpletest@yopmail.com");
        //simpleMailMessage.setFrom("khirulbasherbsmrstu@gmail.com");
        mailSender.send(simpleMailMessage);
    }
}
