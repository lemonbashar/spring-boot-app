package com.lemon.spring.service.account.impl;

import com.lemon.framework.protocolservice.auth.AccountService;
import com.lemon.spring.Application;
import com.lemon.spring.domain.Authority;
import com.lemon.spring.domain.User;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Arrays;
import java.util.HashSet;

import static org.springframework.boot.test.context.SpringBootTest.WebEnvironment.MOCK;

@RunWith(SpringRunner.class)
@SpringBootTest(
        webEnvironment = MOCK,
        classes = Application.class)
@AutoConfigureMockMvc
public class AccountServiceImplTest {
    @MockBean
    private AccountService accountService;

    @Test
    public void currentUsername() {
    }

    @Test
    public void login() {
    }

    @Test
    public void register() {
        User user=new User();
        user.setUsername("lemon");
        user.setAuthorities(new HashSet<>(Arrays.asList(new Authority("ROLE_USER"),new Authority("ROLE_REST_TEST"))));
        user.setPassword("rest-test-123");
        user.setEmail("resttest@mail.com");
        user.setFullName("Rest Test Full Name");

        accountService.register(user);
    }

    @Test
    public void authorities() {
    }
}