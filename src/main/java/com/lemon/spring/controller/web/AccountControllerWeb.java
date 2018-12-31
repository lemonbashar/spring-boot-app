package com.lemon.spring.controller.web;

import com.lemon.spring.domain.User;
import com.lemon.spring.interfaces.WebController;
import com.lemon.spring.service.account.AccountService;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.inject.Inject;
import javax.servlet.http.HttpServletResponse;
import java.util.List;
import java.util.Map;

@SuppressWarnings("SpringJavaAutowiredFieldsWarningInspection")
@Controller
@RequestMapping("/web")
public class AccountControllerWeb implements WebController<User> {
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
    @Override
    public ResponseEntity<Map<String, Object>> save(@ModelAttribute User entity) {
        System.out.print(entity);
        return null;
    }

    @Override
    public ResponseEntity<Map<String, Object>> update(User entity) {
        return null;
    }

    @Override
    public ResponseEntity<User> findOne(Long id) {
        return null;
    }

    @Override
    public ResponseEntity<List<User>> findAll() {
        return null;
    }

    @Override
    public ResponseEntity<Map<String, Object>> delete(Long id) {
        return null;
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
        return "account/login";
    }
}
