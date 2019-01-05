package com.lemon.spring.service.account.impl;

import com.lemon.framework.orm.capture.hbm.HbmCapture;
import com.lemon.framework.springsecurity.auth.AuthenticationService;
import com.lemon.framework.springsecurity.jwt.auth.JWTAuthenticationService;
import com.lemon.framework.web.data.LogoutInfo;
import com.lemon.framework.web.data.UserInfo;
import com.lemon.spring.controller.rest.AccountControllerRest;
import com.lemon.spring.domain.Authority;
import com.lemon.spring.domain.User;
import com.lemon.spring.security.SecurityUtils;
import com.lemon.spring.service.account.AccountService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;
import java.util.HashSet;
import java.util.Set;

@SuppressWarnings({"unchecked", "JpaQlInspection", "SpringJavaAutowiredFieldsWarningInspection"})
@Service
public class AccountServiceImpl implements AccountService {

    private final Logger log= LogManager.getLogger(AccountControllerRest.class);

    @Inject
    private HbmCapture hbmCapture;

    @Inject
    private PasswordEncoder passwordEncoder;

    @Autowired(required = false)
    private JWTAuthenticationService jwtAuthenticationService;

    @Inject
    private AuthenticationService authenticationService;

    @Override
    public String currentUsername() {
        return SecurityUtils.currentUserLogin();
    }

    @Override
    public boolean login(String username, String password) {
        return authenticationService.authenticate(username,password)!=null;
    }

    @Override
    public void register(User user) {
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        Set<Authority> authorities=new HashSet<>();
        user.getAuthorities().forEach(v->{
            if(hbmCapture.findOne("SELECT auth FROM Authority auth WHERE auth.name='"+v.getName()+"'")==null) {
                hbmCapture.save(v);
            }
            authorities.add(v);
        });
        user.setAuthorities(authorities);
        hbmCapture.save(user);
    }

    @Override
    public Set<Authority> authorities() {
        return new HashSet<>(hbmCapture.getAll("SELECT auth FROM Authority auth"));
    }

    @Override
    public String authenticate(UserInfo userInfo) {
        try {
            return jwtAuthenticationService.authenticate(userInfo).getToken();
        } catch (NullPointerException e) {
            throw new SecurityException("May-Be your Application is Not Enabled For Token-Based Authentication");
        }
    }

    @Override
    public void logout(LogoutInfo logoutInfo,HttpServletRequest httpServletRequest) {
        logoutInfo.setUserId(SecurityUtils.currentUserId());
        jwtAuthenticationService.logout(logoutInfo,httpServletRequest);
    }

    private String resolveToken(HttpServletRequest httpServletRequest) {
        String bearer = httpServletRequest.getHeader("Authorization");
        return StringUtils.hasText(bearer) && bearer.startsWith("Bearer ") ? bearer.substring("Bearer ".length()) : null;
    }
}
