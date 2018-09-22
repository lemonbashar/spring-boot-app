package com.lemon.spring.controller.web;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HomeControllerWeb {

    @GetMapping(value = {"/","/home"})
    public String home() {
        return "home";
    }
}
