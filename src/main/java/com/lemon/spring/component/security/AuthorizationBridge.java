package com.lemon.spring.component.security;

import com.lemon.framework.orm.capture.hbm.HbmCapture;
import com.lemon.framework.protocolservice.auth.AccountService;
import com.lemon.spring.domain.Authority;
import com.lemon.spring.repository.UserRepository;
import com.lemon.spring.security.AuthoritiesConstant;
import com.lemon.spring.security.SecurityUtils;
import org.springframework.stereotype.Component;

import javax.inject.Inject;
import java.math.BigInteger;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

@SuppressWarnings("unchecked")
@Component
public class AuthorizationBridge implements com.lemon.framework.security.auth.AuthorizationBridge<BigInteger> {

    @Inject
    private AccountService<BigInteger> accountService;

    @Inject
    private HbmCapture hbmCapture;

    @Inject
    private UserRepository userRepository;

    @Override
    public boolean hasAnyAuthority(String... authorities) {
        return SecurityUtils.hasAnyAuthority(authorities);
    }

    @Override
    public boolean hasAuthority(String... authorities) {
        return SecurityUtils.hasAuthority(authorities);
    }

    @Override
    public boolean hasNoAuthority(String... authorities) {
        return SecurityUtils.hasNoAuthority(authorities);
    }

    @Override
    public boolean hasNoAnyAuthority(String... authorities) {
        return SecurityUtils.hasNoAnyAuthority(authorities);
    }

    @Override
    public boolean isAdmin() {
        return SecurityUtils.hasAnyAuthority(AuthoritiesConstant.ROLES_FOR_ADMIN);
    }

    @Override
    public Set<String> authorities() {
        return new HashSet<>(hbmCapture.getAll("SELECT auth.name FROM Authority auth"));
    }

    @Override
    public Set<String> authoritiesOf(BigInteger userId) {
        return userRepository.findById(userId).map(user -> user.getAuthorities().stream().map(Authority::getName).collect(Collectors.toSet())).orElseThrow(()->new SecurityException("User Not Found"));
    }

    @Override
    public Set<String> authoritiesOfCurrentUser() {
        return SecurityUtils.getAuthoritiesStringCurrentUser();
    }

    @Override
    public BigInteger currentUserId() {
        return SecurityUtils.currentUserId();
    }

    @Override
    public String currentUserLogin() {
        return SecurityUtils.currentUserLogin();
    }

}
