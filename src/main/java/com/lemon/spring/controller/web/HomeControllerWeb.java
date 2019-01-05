package com.lemon.spring.controller.web;

import com.lemon.spring.config.Constants;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
@Profile(value = {Constants.PROFILE_STATEFUL,Constants.PROFILE_BOTH})
public class HomeControllerWeb {

    public static final String BASE_PATH = "";

    @GetMapping(value = {"/","/home"})
    public String home() {
        return "home";
    }

    @GetMapping(value = "/error")
    public String onError() {
        return "redirect:/web"+AccountControllerWeb.BASE_PATH+"/login";
    }
}
