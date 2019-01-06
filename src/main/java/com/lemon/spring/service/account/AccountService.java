package com.lemon.spring.service.account;


import com.lemon.framework.data.JWToken;
import com.lemon.framework.data.LogoutInfo;
import com.lemon.framework.data.UserInfo;
import com.lemon.framework.data.domain.User;

import java.math.BigInteger;
import java.util.Set;

/**
 * A Protocol Service Interface to do account related task using hand-shake
 */
public interface AccountService {
    /**
     * @return Current Username
     */
    String currentUsername();

    /**
     * @return Current user tables id
     */
    BigInteger currentUserId();

    /**
     * Authenticate the User
     * @param userInfo UserInformation for Authenticate
     * @return true if login success
     */
    boolean login(UserInfo userInfo);

    /**
     * Register a User
     * @param user UserInformation for Register
     * */
    void register(User user);

    /**
     * @return Authorities of Current Data-base
     */
    Set<Object> authorities();

    /**
     * Authenticate & Return JWT Token
     * */
    JWToken authenticate(UserInfo userInfo);

    void logout(LogoutInfo logoutInfo);
}
