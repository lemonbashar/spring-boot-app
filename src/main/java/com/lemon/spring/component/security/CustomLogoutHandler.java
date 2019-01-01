package com.lemon.spring.component.security;

import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.logout.LogoutHandler;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Created by lemon on 10/2/18.
 */

@SuppressWarnings({"unused", "DefaultFileTemplate"})
@Component
public class CustomLogoutHandler implements LogoutHandler {
    @Override
    public void logout(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Authentication authentication) {
        System.out.println("Logout Handle:-->");
        //throw new RuntimeException("User Should not Logout That Time");
        /* TODO:Related Token Will be Deleted */
    }
}
