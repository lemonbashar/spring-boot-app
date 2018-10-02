package com.lemon.spring.controller.web;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/web")
public class AccountControllerWeb {
    private static final String BASE_PATH="/account-controller";

    @GetMapping(value = BASE_PATH+"/profile/{username}")
    public String profile(@PathVariable String username, ModelMap modelMap) {
        modelMap.put("username",username);
        return "account/profile";
    }

    @GetMapping(value = BASE_PATH+"/login")
    public String login(Model model,String error,String logout) {
        return "account/login";
    }

    @GetMapping(value = BASE_PATH+"/logout")
    public String logout() {
        return "account/login";
    }

    @GetMapping(value = BASE_PATH+"/register")
    public String register() {
        return "account/register";
    }
}
