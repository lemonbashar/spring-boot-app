package com.lemon.spring.controller.web.rest;

import com.lemon.spring.service.AccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
public class AccountController {
    private final AccountService accountService;

    @Autowired
    public AccountController(AccountService accountService) {
        this.accountService = accountService;
    }

    @GetMapping(value = "/account-controller/{key}",produces = MediaType.APPLICATION_JSON_VALUE)
    public String login(@PathVariable String key) {
        if(key.equals("username")) return accountService.currentUsername();
        return "Not Found";
    }
}
