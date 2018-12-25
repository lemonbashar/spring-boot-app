package com.lemon.spring.service.account.impl;

import com.lemon.framework.orm.capture.hbm.HbmCapture;
import com.lemon.spring.component.security.jwt.TokenProvider;
import com.lemon.spring.data.UserInfo;
import com.lemon.spring.domain.Authority;
import com.lemon.spring.domain.User;
import com.lemon.spring.security.SecurityUtils;
import com.lemon.spring.service.account.AccountService;
import com.lemon.spring.component.security.CustomUserDetailsService;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.inject.Inject;
import java.util.HashSet;
import java.util.Set;

@SuppressWarnings({"unchecked", "JpaQlInspection"})
@Service
public class AccountServiceImpl implements AccountService {

    @Inject
    private HbmCapture hbmCapture;

    @Inject
    private CustomUserDetailsService userDetailsService;

    @Inject
    private PasswordEncoder passwordEncoder;

    @Inject
    private AuthenticationManager authenticationManager;

    @Inject
    private TokenProvider tokenProvider;

    @Override
    public String currentUsername() {
        return SecurityUtils.currentUserLogin();
    }

    @Override
    public boolean login(String username, String password) {
        UserDetails userDetails=userDetailsService.loadUserByUsername(username);
        if(passwordEncoder.matches(password,userDetails.getPassword())) {
            SecurityContext context = SecurityContextHolder.getContext();
            context.setAuthentication(new UsernamePasswordAuthenticationToken(userDetails.getUsername(), userDetails.getPassword(), userDetails.getAuthorities()));
            return true;
        }
        return false;
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
        login(user.getUsername(),user.getPassword());
    }

    @Cacheable(value = Authority.CACHE)
    @Override
    public Set<Authority> authorities() {
        return new HashSet<>(hbmCapture.getAll("SELECT auth FROM Authority auth"));
    }

    @Override
    public String authenticate(UserInfo userInfo) {
        UsernamePasswordAuthenticationToken authenticationToken=new UsernamePasswordAuthenticationToken(userInfo.getUsername(),userInfo.getPassword());
        Authentication authentication=authenticationManager.authenticate(authenticationToken);
        SecurityContextHolder.getContext().setAuthentication(authentication);
        String token=tokenProvider.createToken(authentication,userInfo.isRememberMe());

        return token;
    }
}
