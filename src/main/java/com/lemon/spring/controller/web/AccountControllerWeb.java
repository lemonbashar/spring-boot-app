package com.lemon.spring.controller.web;

import com.lemon.framework.protocolservice.auth.AccountService;
import com.lemon.framework.security.auth.AuthorizationBridge;
import com.lemon.spring.config.Constants;
import com.lemon.spring.domain.User;
import com.lemon.spring.interfaces.ViewController;
import com.lemon.spring.repository.AuthorityRepository;
import com.lemon.spring.repository.UserRepository;
import com.lemon.spring.security.AuthoritiesConstant;
import com.lemon.spring.security.SecurityUtils;
import com.lemon.spring.service.account.UserService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.context.annotation.Profile;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.access.prepost.PostAuthorize;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.inject.Inject;
import javax.servlet.http.HttpServletResponse;
import java.math.BigInteger;

@SuppressWarnings({"SpringJavaAutowiredFieldsWarningInspection", "Duplicates"})
@Controller
@Profile(value = {Constants.PROFILE_STATEFUL,Constants.PROFILE_BOTH})
@RequestMapping("/web")
public class AccountControllerWeb implements ViewController<User> {
    private final Logger log= LogManager.getLogger(AccountControllerWeb.class);

    @Inject
    private AccountService accountService;

    @Inject
    private UserRepository userRepository;

    @Inject
    private AuthorityRepository authorityRepository;

    @Inject
    private AuthorizationBridge authorizationBridge;

    @Inject
    private UserService userService;


    public static final String BASE_PATH="/account-controller";

    @GetMapping(value = BASE_PATH+"/register")
    public String savePageAccess(Model model) {
        model.addAttribute("user",new User());
        model.addAttribute("authorities",authorityRepository.findAll());
        return "account/register";
    }

    @PostMapping(value = BASE_PATH)
    @Override
    public String save(@ModelAttribute User user) {
        accountService.register(user);
        log.debug("Successfully Registered...");
        return "account/login";
    }

    @GetMapping(value = BASE_PATH+"/update/{id}")
    public String updatePageAccess(Model model,@PathVariable BigInteger id) {
        User user=userService.userForUpdate(id);
        if(authorizationBridge.hasNoAuthority(AuthoritiesConstant.ROLES_FOR_ADMIN)) {
            if(!user.getUsername().equals(SecurityUtils.currentUserLogin())) return "account/profile";
        }
        model.addAttribute("user",user);
        model.addAttribute("authorities",authorityRepository.findAll());
        return "account/update";
    }


    @GetMapping(value = BASE_PATH+"/update/me")
    public String updatePageAccessMe(Model model) {
        User user=userService.userForUpdate(SecurityUtils.currentUserId());
        model.addAttribute("user",user);
        model.addAttribute("authorities",authorityRepository.findAll());
        return "account/update";
    }

    @PreAuthorize("#user.username==principal.username OR hasAnyAuthority('ROLE_ADMIN')")
    @PostMapping(value = BASE_PATH+"/update")
    @Override
    public String update(@ModelAttribute User user) {
        userService.updateUser(user);
        return "account/profile";
    }

    @Override
    public String findOne(BigInteger id) {
        return null;
    }

    @Override
    public String findAll() {
        return null;
    }

    @Override
    public String delete(BigInteger id) {
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
}
