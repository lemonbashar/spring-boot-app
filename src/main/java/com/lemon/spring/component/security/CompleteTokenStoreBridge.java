package com.lemon.spring.component.security;

import com.lemon.framework.springsecurity.auth.AuthenticationToken;
import com.lemon.framework.springsecurity.jwt.bridge.TokenStoreBridge;
import com.lemon.spring.domain.TokenStore;
import com.lemon.spring.domain.User;
import com.lemon.spring.repository.TokenStoreRepository;
import org.springframework.context.annotation.DependsOn;
import org.springframework.stereotype.Component;

import javax.inject.Inject;
import java.util.Date;

@DependsOn("tokenStoreRepository")
@Component
public class CompleteTokenStoreBridge implements TokenStoreBridge {

    @Inject
    private TokenStoreRepository tokenStoreRepository;

    @Override
    public String tokenByUsername(String username) {
        TokenStore tokenStore = tokenStoreRepository.findByUsername(username);
        if(tokenStore !=null) return tokenStore.getToken();
        return null;
    }

    @Override
    public boolean isActiveToken(String token) {
        return tokenStoreRepository.findByActiveStatus(token,true)!=null;
    }

    @Override
    public boolean isActiveToken(String token, Date validateDate) {
        TokenStore tokenStore=tokenStoreRepository.findByValidateDate(token,validateDate);
        return tokenStore!=null;
    }

    @Override
    public void saveToken(String token, AuthenticationToken authenticationToken,Date validateDate) {
        TokenStore tokenStore=new TokenStore();
        tokenStore.setToken(token);
        tokenStore.setUser(new User(authenticationToken.getUserId()));
        tokenStore.setValidateDate(validateDate);
        tokenStore.setActive(true);

        tokenStoreRepository.save(tokenStore);
    }
}
