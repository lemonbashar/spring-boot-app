package com.lemon.spring.component.security;

import com.lemon.framework.properties.ApplicationProperties;
import com.lemon.spring.controller.web.AccountControllerWeb;
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
    @Inject
    private ApplicationProperties applicationProperties;
    @Override
    public void onAuthenticationSuccess(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Authentication authentication) throws IOException, ServletException {
        System.out.println("Authenticated Success:--->:"+authentication);
        httpServletResponse.sendRedirect("/web"+AccountControllerWeb.BASE_PATH+"/profile");
    }
}
