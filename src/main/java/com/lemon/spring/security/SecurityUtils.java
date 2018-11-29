package com.lemon.spring.security;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;

public final class SecurityUtils {
    public static String currentUserLogin() {
        Authentication authentication=SecurityContextHolder.getContext().getAuthentication();
        Object principal=authentication.getPrincipal();
        String login=null;
        if(principal instanceof UserDetails)
            login=((UserDetails)principal).getUsername();
        else if(principal instanceof String)
            login=principal.toString();
        return login;
    }
}
