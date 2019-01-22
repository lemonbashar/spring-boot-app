package com.lemon.spring.service.account.impl;

import com.lemon.framework.protocolservice.auth.AccountService;
import com.lemon.spring.Application;
import com.lemon.spring.domain.Authority;
import com.lemon.spring.domain.User;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.junit4.SpringRunner;

import javax.inject.Inject;
import java.util.Arrays;
import java.util.HashSet;

import static junit.framework.TestCase.assertNotNull;
import static junit.framework.TestCase.assertTrue;
import static org.springframework.boot.test.context.SpringBootTest.WebEnvironment.MOCK;

/*TODO: Use Only For Standalone Test for Checking just*/

/*
@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(
        webEnvironment = MOCK,
        classes = Application.class)
@AutoConfigureMockMvc
public class AccountServiceImplTest {
    @MockBean
    private AccountService accountService;

    @Test
    public void register() {
        User user=new User();
        user.setUsername("lemon123");
        user.setAuthorities(new HashSet<>(Arrays.asList(new Authority("ROLE_USER"),new Authority("ROLE_REST_TEST"))));
        user.setPassword("rest-test-123");
        user.setEmail("resttes123t@mail.com");
        user.setFullName("Rest Test Full Name");

        accountService.register(user);
        assertTrue(true);
    }

    @Test
    public void authorities() {
        assertNotNull(accountService.authorities());
    }
}*/
