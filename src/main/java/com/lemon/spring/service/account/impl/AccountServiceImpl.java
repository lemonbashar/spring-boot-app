package com.lemon.spring.service.account.impl;

import com.lemon.spring.domain.User;
import com.lemon.spring.service.account.AccountService;
import org.springframework.stereotype.Service;

@Service
public class AccountServiceImpl implements AccountService {
    @Override
    public String currentUsername() {
        return "lemon";
    }

    @Override
    public User login(String username, String password) {
        return username.equals(currentUsername())&&password.equals("123456")?new User(1L,username,password):null;
    }
}
