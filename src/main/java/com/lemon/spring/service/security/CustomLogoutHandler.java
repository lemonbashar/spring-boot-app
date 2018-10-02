package com.lemon.spring.service.security;

import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.logout.LogoutHandler;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Created by lemon on 10/2/18.
 */

@SuppressWarnings({"unused", "DefaultFileTemplate"})
@Service
public class CustomLogoutHandler implements LogoutHandler {
    @Override
    public void logout(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Authentication authentication) {
        System.out.println("Logout Handle:-->");
        throw new RuntimeException("User Should not Logout That Time");
    }
}
