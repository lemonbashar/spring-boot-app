package com.lemon.spring.config.service;

import com.lemon.spring.Application;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.test.context.junit4.SpringRunner;

import javax.inject.Inject;

import static org.springframework.boot.test.context.SpringBootTest.WebEnvironment.MOCK;

/*TODO: Use Only For Standalone Test for Checking just*/


/*@RunWith(SpringRunner.class)
@SpringBootTest(
        webEnvironment = MOCK,
        classes = Application.class)
@AutoConfigureMockMvc
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
}*/
