package com.lemon.spring.component.security;

import com.lemon.framework.springsecurity.auth.AuthenticationToken;
import com.lemon.framework.springsecurity.jwt.bridge.TokenStoreBridge;
import com.lemon.framework.web.data.UserInfo;
import com.lemon.spring.component.audit.AuditAware;
import com.lemon.spring.domain.TokenStore;
import com.lemon.spring.domain.User;
import com.lemon.spring.repository.TokenStoreRepository;
import org.springframework.context.annotation.DependsOn;
import org.springframework.stereotype.Component;

import javax.inject.Inject;
import java.math.BigInteger;
import java.util.Date;
import java.util.List;

@DependsOn("tokenStoreRepository")
@Component
public class CompleteTokenStoreBridge implements TokenStoreBridge {

    @Inject
    private TokenStoreRepository tokenStoreRepository;

    @Inject
    private AuditAware auditAware;

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
    public boolean isActiveToken(String token,String remoteIpAddress) {
        return tokenStoreRepository.findByTokenIpAndStatus(token,remoteIpAddress,true)!=null;
    }

    @Override
    public boolean isActiveToken(String token, Date validateDate) {
        TokenStore tokenStore=tokenStoreRepository.findByValidateDate(token,validateDate);
        return tokenStore!=null;
    }

    @Override
    public void saveToken(String token, AuthenticationToken authenticationToken, Date validateDate, UserInfo userInfo) {
        TokenStore tokenStore=new TokenStore();
        tokenStore.setToken(token);
        tokenStore.setUser(new User(authenticationToken.getUserId()));
        tokenStore.setValidateDate(validateDate);
        tokenStore.setActive(true);
        tokenStore.setIpAddress(userInfo.getIpAddress());
        auditAware.awareCreate(tokenStore);
        tokenStoreRepository.save(tokenStore);
    }

    @Override
    public void updateTokenActiveStatus(String token, boolean activeStatus) {
        TokenStore tokenStore=tokenStoreRepository.findByToken(token);
        if(tokenStore !=null) {
            tokenStore.setActive(activeStatus);
            auditAware.awareUpdate(tokenStore);
            tokenStoreRepository.save(tokenStore);
        }
    }

    @Override
    public void deactivateAllByUidAndIpAddress(BigInteger userId, String ipAddress) {
        List<TokenStore> tokenStores=tokenStoreRepository.findAllByUidAndIp(userId,ipAddress);
        tokenStores.forEach(tokenStore->{
            tokenStore.setActive(false);
            auditAware.awareUpdate(tokenStore);
            tokenStoreRepository.save(tokenStore);
        });
    }

    @Override
    public int totalTokenByActiveStatus(BigInteger userId, boolean activeStatus) {
        return (int) tokenStoreRepository.countByUidAndActiveStatus(userId,activeStatus);
    }

    @Override
    public void deactivateAllByUid(BigInteger userId) {
        List<TokenStore> tokenStores=tokenStoreRepository.findAllByUid(userId);
        tokenStores.forEach(tokenStore->{
            tokenStore.setActive(false);
            auditAware.awareUpdate(tokenStore);
            tokenStoreRepository.save(tokenStore);
        });
    }

    @Override
    public void deleteToken(String token) {
        TokenStore tokenStore=tokenStoreRepository.findByToken(token);
        if(tokenStore!=null) tokenStoreRepository.delete(tokenStore);
    }
}
