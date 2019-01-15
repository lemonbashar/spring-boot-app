package com.lemon.spring.component.security;

import com.lemon.spring.controller.rest.AccountControllerRest;
import com.lemon.spring.controller.web.AccountControllerWeb;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Created by lemon on 10/1/18.
 */

@SuppressWarnings({"unused", "DefaultFileTemplate"})

@Component
public class CustomAuthenticationFailureHandler implements AuthenticationFailureHandler {
    private final Logger log= LogManager.getLogger(CustomAuthenticationFailureHandler.class);

    @Override
    public void onAuthenticationFailure(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, AuthenticationException e) throws IOException, ServletException {
        log.debug("Authenticated Failed:--->");
        httpServletResponse.sendRedirect("/web"+ AccountControllerWeb.BASE_PATH+"/home");
    }
}
