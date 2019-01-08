package com.lemon.spring.security;

import com.lemon.framework.springsecurity.auth.data.AuthenticationToken;
import com.lemon.framework.springsecurity.auth.data.CustomUserDetails;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;

import java.math.BigInteger;
import java.util.Set;
import java.util.stream.Collectors;

@SuppressWarnings("Convert2MethodRef")
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
        if(authentication.getPrincipal() instanceof CustomUserDetails)
            return ((CustomUserDetails)authentication.getPrincipal()).getId();
        if(authentication instanceof AuthenticationToken) {
            return ((AuthenticationToken)authentication).getUserId();
        }
        throw new SecurityException("Not a Valid Authentication to Find User-Id");
    }

    public static boolean hasAnyAuthority(String ...authorities) {
        Set<String> grantedAuth=getAuthoritiesStringCurrentUser();
        for(String authority:authorities)
            if(grantedAuth.contains(authority)) return true; /*If Contain at least one Authority, then true*/
        return false;
    }

    public static boolean hasNoAnyAuthority(String ...authorities) {
        return !hasAnyAuthority(authorities);
    }

    public static boolean hasNoAuthority(String ...authorities) {
        return !hasAuthority(authorities);
    }

    public static boolean hasAuthority(String ...authorities) {
        Set<String> grantedAuth=getAuthoritiesStringCurrentUser();
        for(String authority:authorities)
            if(!grantedAuth.contains(authority)) return false; /*If At least one authority missing then false*/
        return true;
    }

    public static Set<String> getAuthoritiesStringCurrentUser() {
        Authentication authentication=SecurityContextHolder.getContext().getAuthentication();
        return authentication.getAuthorities().stream().map(val-> val.getAuthority()).collect(Collectors.toSet());

    }
}
