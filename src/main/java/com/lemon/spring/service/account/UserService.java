package com.lemon.spring.service.account;


import com.lemon.spring.domain.internal.User;

import java.math.BigInteger;

public interface UserService {

    User userForUpdate(BigInteger id);

    void updateUser(User user);
}
