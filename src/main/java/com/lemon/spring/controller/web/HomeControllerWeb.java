package com.lemon.spring.controller.web;

import com.lemon.spring.config.Constants;
import com.lemon.spring.service.ApplicationService;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Controller
@Profile(value = {Constants.PROFILE_STATEFUL, Constants.PROFILE_BOTH})
public class HomeControllerWeb {
    public static final String BASE_PATH = "";
    @Inject
    private ApplicationService applicationService;

    @GetMapping(value = {"/", "/home"})
    public String home() {
        return "/home";
    }

    /*@GetMapping(value = {"/error"})
    public String error() {
        return "/home";
    }*/

    @GetMapping(value = "/error")
    public String onError(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) {
        applicationService.logPathError(httpServletRequest, httpServletResponse);
        return "redirect:/web" + AccountControllerWeb.BASE_PATH + "/home";
    }
}
