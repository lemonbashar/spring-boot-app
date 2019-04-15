package com.lemon.spring.component.security;

import com.lemon.framework.properties.spring.ApplicationProperties;
import com.lemon.spring.controller.web.AccountControllerWeb;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import javax.inject.Inject;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Created by lemon on 10/1/18.
 */

@SuppressWarnings({"unused", "DefaultFileTemplate"})
@Component
public class CustomAuthenticationSuccessHandler implements AuthenticationSuccessHandler {
    private final Logger log = LogManager.getLogger(CustomAuthenticationSuccessHandler.class);

    @Inject
    private ApplicationProperties applicationProperties;

    @Override
    public void onAuthenticationSuccess(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Authentication authentication) throws IOException, ServletException {
        log.debug("Authentication Success:--->:" + authentication);
        httpServletResponse.sendRedirect("/web" + AccountControllerWeb.BASE_PATH + "/home");
    }
}
