package com.lemon.spring.security;

import com.lemon.framework.springsecurity.auth.AuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;

import java.math.BigInteger;

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

    public static BigInteger currentUserId() {
        Authentication authentication=SecurityContextHolder.getContext().getAuthentication();
        if(authentication instanceof AuthenticationToken) {
            return ((AuthenticationToken)authentication).getUserId();
        }
        throw new SecurityException("Not a Valid Authentication to Find User-Id");
    }
}
