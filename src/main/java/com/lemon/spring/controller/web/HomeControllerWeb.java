package com.lemon.spring.controller.web;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
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
