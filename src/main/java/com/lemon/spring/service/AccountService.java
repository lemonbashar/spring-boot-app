package com.lemon.spring.service;

import com.lemon.spring.domain.User;

public interface AccountService {
    String currentUsername();

    User login(String username, String password);
}
