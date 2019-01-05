package com.lemon.spring.service.account;

import com.lemon.framework.web.data.JWToken;
import com.lemon.framework.web.data.LogoutInfo;
import com.lemon.framework.web.data.UserInfo;
import com.lemon.spring.domain.Authority;
import com.lemon.spring.domain.User;

import javax.servlet.http.HttpServletRequest;
import java.util.Set;

public interface AccountService {
    String currentUsername();

    boolean login(String username, String password);

    void register(User user);

    Set<Authority> authorities();

    /**
     * Authenticate & Return JWT Token
     * */
    JWToken authenticate(UserInfo userInfo);

    void logout(LogoutInfo logoutInfo,HttpServletRequest httpServletRequest);
}
