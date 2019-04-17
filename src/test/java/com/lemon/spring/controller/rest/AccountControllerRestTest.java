package com.lemon.spring.controller.rest;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.lemon.framework.data.UserInfo;
import com.lemon.spring.Application;
import com.lemon.spring.domain.internal.Authority;
import com.lemon.spring.domain.internal.User;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.http.MediaType;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.web.servlet.MockMvc;

import javax.inject.Inject;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.Arrays;
import java.util.HashSet;

import static com.lemon.spring.controller.rest.AccountControllerRest.BASE_PATH;
import static org.springframework.boot.test.context.SpringBootTest.WebEnvironment.MOCK;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest(
        webEnvironment = MOCK,
        classes = Application.class)
@AutoConfigureMockMvc
@RunWith(SpringJUnit4ClassRunner.class)
@EnableJpaRepositories(basePackages = "com.lemon.spring.repository")
public class AccountControllerRestTest {
    @Inject
    private MockMvc mockMvc;

    @Inject
    private ObjectMapper objectMapper;

    @Inject
    private JdbcTemplate jdbcTemplate;

    private int serverPort=2343;

    /*@Inject
    private TestRestTemplate testRestTemplate;*/

    /*
        TODO: If Enable it, It just show you that, the bean is or not called by test, and inside things not execute, and if disable, it work with existing code
        Like here it will save the user, if it enable, it doesn't save the user
    @MockBean
    private AccountService accountService;*/

    /*@MockBean
    private AccountService accountService;*/

    @Before
    public void setUp() throws Exception {
        System.out.print("");
    }

    @Test
    public void save() throws Exception {
        User user= simpleUserCreation();
        mockMvc.perform(post("/api"+ BASE_PATH)
        .contentType(MediaType.APPLICATION_JSON_VALUE).content(objectMapper.writeValueAsString(user)).accept(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(status().isOk());

        String id=jdbcTemplate.query("SELECT id FROM spring_user where username='lemonuser'",(rs,i)->rs.getString(1)).get(0);
        jdbcTemplate.execute("delete from user_authorities where user_id='"+id+"'");
        jdbcTemplate.execute("DELETE from spring_user where id='"+id+"'");

    }

    private User simpleUserCreation() {
        User user=new User();
        user.setUsername("lemonuser");
        user.setAuthorities(new HashSet<>(Arrays.asList(new Authority("ROLE_USER"),new Authority("ROLE_REST_TEST"))));
        user.setPassword("rest-test-123");
        user.setEmail("resttest@mail.com");
        user.setFullName("Rest Test Full Name");
        return user;
    }

    private User adminCreation() {
        User user=new User();
        user.setUsername("adminTest");
        user.setAuthorities(new HashSet<>(Arrays.asList(new Authority("ROLE_USER"),new Authority("ROLE_REST_TEST"),new Authority("ROLE_ADMIN"))));
        user.setPassword("admin");
        user.setEmail("admin.test@mail.com");
        user.setFullName("Admin Test Full Name");
        return user;
    }

    @Test
    public void login() throws Exception {
        loginSimple("sdfg","sdfg");
        //loginJwt("admin","admin");
    }

    private void loginSimple(String username, String password) throws Exception {
        UserInfo userInfo =new UserInfo();
        userInfo.setUsername(username);
        userInfo.setPassword(password);

        mockMvc.perform(post("/api"+ BASE_PATH+"/login-rest")
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .content(objectMapper.writeValueAsString(userInfo))
        ).andExpect(status().isOk());
    }

    private void loginJwt(String username, String password) throws Exception {
        UserInfo userInfo =new UserInfo();
        userInfo.setUsername(username);
        userInfo.setPassword(password);

        mockMvc.perform(post("/api"+ BASE_PATH+"/login-jwt")
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .content(objectMapper.writeValueAsString(userInfo))
        ).andExpect(status().isOk());

/*
        TestRestTemplate testRestTemplate=new TestRestTemplate();

        MultiValueMap<String, String> headers = new LinkedMultiValueMap<String, String>();
        Map map = new HashMap<String, String>();
        map.put("Content-Type", "application/json");

        headers.setAll(map);

        Map req_payload = new HashMap();
        req_payload.put("", "");

        HttpEntity<?> request = new HttpEntity<>(req_payload, headers);
        String url = baseUrl()+BASE_PATH+"/login-jwt";

        ResponseEntity<?> response = new RestTemplate().postForEntity(url, request, String.class);
        JWToken entityResponse = (JWToken) response.getBody();
*/

        //JWToken jwToken=testRestTemplate.postForObject(toUri(BASE_PATH+"/login-jwt"),userInfo, JWToken.class);

    }


    //@Test
    public void keyVal() throws Exception {
        loginAdmin();
        mockMvc.perform(get("/api"+BASE_PATH+"/key/username")
            .contentType(MediaType.APPLICATION_JSON_VALUE)
        ).andExpect(status().isOk());

    }

    private void loginAdmin() throws Exception {
        loginJwt("admin","admin");
    }

    protected String baseUrl() throws URISyntaxException {
        return "http://localhost:"+serverPort+"/api";
    }

    protected URI toUri(String url) {
        try {
            return new URI(baseUrl()+url);
        } catch (URISyntaxException e) {
            e.printStackTrace();
        }

        return null;
    }
}