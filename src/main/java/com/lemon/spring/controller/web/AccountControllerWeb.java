package com.lemon.spring.controller.web;

import com.lemon.spring.domain.User;
import com.lemon.spring.service.account.AccountService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.inject.Inject;

@Controller
@RequestMapping("/web")
public class AccountControllerWeb {
    @Inject
    private AccountService accountService;
    private static final String BASE_PATH="/account-controller";

    @GetMapping(value = BASE_PATH+"/profile/{username}")
    public String profile(@PathVariable String username, ModelMap modelMap) {
        modelMap.put("username",username);
        return "account/profile";
    }

    @GetMapping(value = BASE_PATH+"/login")
    public String login(Model model, @RequestParam(name = "error",required = false) String error,@RequestParam(name = "logout",required = false) String logout) {
        return "account/login";
    }

    @GetMapping(value = BASE_PATH+"/logout")
    public String logout() {
        return "account/login";
    }

    @GetMapping(value = BASE_PATH+"/register")
    public String register(Model model) {
        model.addAttribute("user",new User());
        model.addAttribute("authorities",accountService.authorities());
        return "account/register";
    }
}
