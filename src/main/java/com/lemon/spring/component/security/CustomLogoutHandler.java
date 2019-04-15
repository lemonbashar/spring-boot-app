package com.lemon.spring.component.security;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
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
    private final Logger log = LogManager.getLogger(CustomLogoutHandler.class);

    @Override
    public void logout(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Authentication authentication) {
        log.debug("Logout Handle:-->");
        //throw new RuntimeException("User Should not Logout That Time");
        /* TODO:Related Token Will be Deleted */
    }
}
