package com.lemon.spring.service.account;

import com.lemon.spring.domain.Authority;
import com.lemon.spring.domain.User;

import java.util.Set;

public interface AccountService {
    String currentUsername();

    User login(String username, String password);

    void register(User user);

    Set<Authority> authorities();
}
