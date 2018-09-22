package com.lemon.spring.controller.rest;

import com.lemon.spring.domain.User;
import com.lemon.spring.interfaces.WebController;
import com.lemon.spring.service.AccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api")
public class AccountControllerRest implements WebController<User> {
    private static final String BASE_PATH="/account-controller";
    private final AccountService accountService;

    @Autowired
    public AccountControllerRest(AccountService accountService) {
        this.accountService = accountService;
    }

    @GetMapping(value = BASE_PATH+"/{key}",produces = MediaType.APPLICATION_JSON_VALUE)
    public String login(@PathVariable String key) {
        if(key.equals("username")) return accountService.currentUsername();
        return "Not Found";
    }

    @Override
    @PostMapping(value = BASE_PATH+"/save",produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Map<String, Object>> save(@RequestBody User entity) {
        return null;
    }

    @Override
    @PutMapping(value = BASE_PATH+"/update")
    public ResponseEntity<Map<String, Object>> update(@RequestBody User entity) {
        return null;
    }

    @Override
    @GetMapping(value = BASE_PATH+"/find-one/{id}",produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<User> findOne(@PathVariable Long id) {
        return null;
    }

    @Override
    @GetMapping(value = BASE_PATH+"/find-all",produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<User>> findAll() {
        return null;
    }

    @Override
    @DeleteMapping(value = BASE_PATH+"/delete/{id}",produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Map<String, Object>> delete(Long id) {
        return null;
    }
}
