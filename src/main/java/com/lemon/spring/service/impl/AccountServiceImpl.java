package com.lemon.spring.service.impl;

import com.lemon.spring.service.AccountService;
import org.springframework.stereotype.Service;

@Service
public class AccountServiceImpl implements AccountService {
    @Override
    public String currentUsername() {
        return "lemon";
    }
}
