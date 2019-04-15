package com.lemon.spring.security;

import com.lemon.framework.springsecurity.auth.data.AuthenticationToken;
import com.lemon.framework.springsecurity.auth.data.CustomUserDetails;
import org.apache.log4j.Logger;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;

import java.math.BigInteger;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

@SuppressWarnings("Convert2MethodRef")
public final class SecurityUtils {
    private static final Logger log = Logger.getLogger(SecurityUtils.class);

    public static Authentication authentication() {
        return SecurityContextHolder.getContext().getAuthentication();
    }

    public static String currentUserLogin() {
        Object principal = authentication().getPrincipal();
        String login = null;
        if (principal instanceof UserDetails)
            login = ((UserDetails) principal).getUsername();
        else if (principal instanceof String)
            login = principal.toString();
        return login;
    }

    public static BigInteger currentUserId() {
        Authentication authentication = authentication();
        if (authentication.getPrincipal() instanceof CustomUserDetails)
            return ((CustomUserDetails) authentication.getPrincipal()).getId();
        if (authentication instanceof AuthenticationToken) {
            return ((AuthenticationToken) authentication).getUserId();
        }
        log.error("Not a Valid Authentication to Find User-Id");
        throw new SecurityException("Not a Valid Authentication to Find User-Id");
    }

    public static boolean hasAnyAuthority(String... authorities) {
        Set<String> grantedAuth = getAuthoritiesStringCurrentUser();
        for (String authority : authorities)
            if (grantedAuth.contains(authority)) return true; /*If Contain at least one Authority, then true*/
        return false;
    }

    public static boolean hasNoAnyAuthority(String... authorities) {
        return !hasAnyAuthority(authorities);
    }

    public static boolean hasNoAuthority(String... authorities) {
        return !hasAuthority(authorities);
    }

    public static boolean hasAuthority(String... authorities) {
        Set<String> grantedAuth = getAuthoritiesStringCurrentUser();
        for (String authority : authorities)
            if (!grantedAuth.contains(authority)) return false; /*If At least one authority missing then false*/
        return true;
    }

    public static Set<String> getAuthoritiesStringCurrentUser() {
        try {
            return authentication().getAuthorities().stream().map(val -> val.getAuthority()).collect(Collectors.toSet());
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            e.printStackTrace();
        }

        return new HashSet<>();
    }
}
