package com.lemon.spring.controller.rest;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.lemon.spring.Application;
import com.lemon.spring.domain.Authority;
import com.lemon.spring.domain.User;
import com.lemon.spring.service.account.AccountService;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import javax.inject.Inject;
import java.util.Arrays;
import java.util.HashSet;

import static org.springframework.boot.test.context.SpringBootTest.WebEnvironment.MOCK;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@SpringBootTest(
        webEnvironment = MOCK,
        classes = Application.class)
@AutoConfigureMockMvc
public class AccountControllerRestTest {
    @Inject
    private MockMvc mockMvc;

    @Inject
    private ObjectMapper objectMapper;

    /*@MockBean
    private AccountService accountService;*/

    @Before
    public void setUp() throws Exception {
        System.out.print("");
    }

    @Test
    public void login() {
    }

    @Test
    public void save() throws Exception {
        User user=new User();
        user.setUsername("lemon");
        user.setAuthorities(new HashSet<>(Arrays.asList(new Authority("ROLE_USER"),new Authority("ROLE_REST_TEST"))));
        user.setPassword("rest-test-123");
        user.setEmail("resttest@mail.com");
        user.setFullName("Rest Test Full Name");
        /*objectMapper.configure(SerializationFeature.WRAP_ROOT_VALUE, false);
        ObjectWriter ow = objectMapper.writer().withDefaultPrettyPrinter();
        String requestJson=ow.writeValueAsString(user );*/

        mockMvc.perform(post("/api"+AccountControllerRest.BASE_PATH)
        .contentType(MediaType.APPLICATION_JSON_VALUE).content(objectMapper.writeValueAsString(user)).accept(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(status().isOk());
    }
}