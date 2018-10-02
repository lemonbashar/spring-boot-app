package com.lemon.spring.controller.rest;

import com.lemon.spring.domain.User;
import com.lemon.spring.interfaces.WebController;
import com.lemon.spring.repository.UserRepository;
import com.lemon.spring.service.AccountService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api")
public class AccountControllerRest implements WebController<User> {
    private static final String BASE_PATH="/account-controller";

    private final AccountService accountService;
    private final UserRepository userRepository;

    private Logger log= LogManager.getLogger(AccountControllerRest.class);

    @Autowired
    public AccountControllerRest(AccountService accountService, UserRepository userRepository) {
        this.accountService = accountService;
        this.userRepository = userRepository;
    }

    @Override
    @PostMapping(value = BASE_PATH,produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Map<String, Object>> save(@RequestBody User entity) {

        return null;
    }

    @Override
    @PutMapping(value = BASE_PATH)
    public ResponseEntity<Map<String, Object>> update(@RequestBody User entity) {
        return null;
    }

    @Override
    @GetMapping(value = BASE_PATH+"/{id}",produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<User> findOne(@PathVariable Long id) {
        log.info("Finding One User by Id:"+id);
        return null;
    }

    @Override
    @GetMapping(value = BASE_PATH,produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<User>> findAll() {
        return null;
    }

    @Override
    @DeleteMapping(value = BASE_PATH+"/{id}",produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Map<String, Object>> delete(Long id) {
        return null;
    }

    @GetMapping(value = BASE_PATH+"/key/{key}",produces = MediaType.APPLICATION_JSON_VALUE)
    public String login(@PathVariable String key) {
        if(key.equals("username")) return accountService.currentUsername();
        return "Not Found";
    }

    /*@PostMapping(value = BASE_PATH+"/login")
    public void login(HttpServletResponse response, @RequestParam(name = "username",required = true) String username, @RequestParam(name = "password",required = true) String password) throws IOException {
        User user=accountService.login(username,password);
        if(user!=null) {
            response.sendRedirect("/web/account-controller/profile/"+username);
        }
        else response.sendRedirect("/web/account-controller/register");
    }*/


    @PostMapping(value = BASE_PATH+"/login")
    public void login(Model model) throws IOException {
        System.out.println("<><><><><>");
    }
}
