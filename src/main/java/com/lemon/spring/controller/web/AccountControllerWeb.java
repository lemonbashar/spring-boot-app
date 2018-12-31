package com.lemon.spring.controller.web;

import com.lemon.spring.controller.rest.AccountControllerRest;
import com.lemon.spring.domain.User;
import com.lemon.spring.service.account.AccountService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.inject.Inject;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.Map;

@SuppressWarnings({"SpringJavaAutowiredFieldsWarningInspection", "Duplicates"})
@Controller
@RequestMapping("/web")
public class AccountControllerWeb {
    private final Logger log= LogManager.getLogger(AccountControllerRest.class);


    @Inject
    private AccountService accountService;

    public static final String BASE_PATH="/account-controller";

    @GetMapping(value = BASE_PATH+"/register")
    public String register(Model model) {
        model.addAttribute("user",new User());
        model.addAttribute("authorities",accountService.authorities());
        return "account/register";
    }

    @PostMapping(value = BASE_PATH)
    public ResponseEntity<Map<String, Object>> save(@ModelAttribute User user) {
        accountService.register(user);
        Map<String,Object> objectMap=new HashMap<>();
        objectMap.put("REGISTER_SUCCESS",true);
        log.debug("Successfully Registered...");
        return ResponseEntity.ok(objectMap);
    }

    @GetMapping(value = BASE_PATH+"/profile")
    public String profile(HttpServletResponse httpServletResponse) {
        return "account/profile";
    }

    @GetMapping(value = BASE_PATH+"/login")
    public String login(Model model, @RequestParam(name = "error",required = false) String error,@RequestParam(name = "logout",required = false) String logout) {
        return "account/login";
    }

    @GetMapping(value = BASE_PATH+"/logout")
    public String logout() {
        accountService.logout();
        return "account/login";
    }
}
