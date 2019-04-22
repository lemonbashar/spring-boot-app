package com.lemon.spring.service.account.impl;

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
