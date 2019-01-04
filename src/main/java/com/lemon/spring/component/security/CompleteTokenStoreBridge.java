package com.lemon.spring.component.security;

import com.lemon.framework.orm.capture.hbm.HbmCapture;
import com.lemon.framework.springsecurity.auth.AuthenticationToken;
import com.lemon.framework.springsecurity.jwt.bridge.TokenStoreBridge;
import com.lemon.framework.web.data.UserInfo;
import com.lemon.spring.component.audit.AuditAware;
import com.lemon.spring.domain.TokenStore;
import com.lemon.spring.domain.User;
import com.lemon.spring.repository.TokenStoreRepository;
import com.lemon.spring.web.page.PageImpl;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.context.annotation.DependsOn;
import org.springframework.stereotype.Component;

import javax.inject.Inject;
import java.math.BigInteger;
import java.time.LocalDate;
import java.util.Date;
import java.util.List;

@DependsOn("tokenStoreRepository")
@Component
public class CompleteTokenStoreBridge implements TokenStoreBridge {

    @Inject
    private TokenStoreRepository tokenStoreRepository;

    @Inject
    private AuditAware auditAware;

    @Inject
    private HbmCapture hbmCapture;

    @Override
    public String tokenByUsername(String username) {
        TokenStore tokenStore = tokenStoreRepository.findByUsername(username);
        if(tokenStore !=null) return tokenStore.getToken();
        return null;
    }

    @Override
    public boolean isActiveToken(String token) {
        //return hbmCapture.findOne("SELECT tokenStore FROM TokenStore tokenStore WHERE tokenStore.token='"+token+"' AND tokenStore.active=true")!=null;
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
            updateTokenActiveStatus(tokenStore,activeStatus);
        }
    }

    @Override
    public void deactivateAllByUidAndIpAddress(BigInteger userId, String ipAddress) {
        deactivate(tokenStoreRepository.findAllByUidAndIp(userId,ipAddress));
    }

    @Override
    public int totalTokenByActiveStatus(BigInteger userId, boolean activeStatus) {
        return (int) tokenStoreRepository.countByUidAndActiveStatus(userId,activeStatus);
    }

    @Override
    public void deactivateAllByUid(BigInteger userId) {
        deactivate(tokenStoreRepository.findAllByUid(userId));
    }

    @Override
    public void deleteToken(String token) {
        TokenStore tokenStore=tokenStoreRepository.findByToken(token);
        if(tokenStore!=null) tokenStoreRepository.delete(tokenStore);
    }

    @Override
    public int countTokenByDate(LocalDate fromDate, LocalDate toDate,BigInteger userId){
        return (int)tokenStoreRepository.countTokenByDate(fromDate,toDate,userId);
    }

    @Override
    public int countTokenByDate(LocalDate now,BigInteger userId){
        return (int)tokenStoreRepository.countTokenByDate(now,userId);
    }

    @Override
    public int countTokenByUserIdAndStatus(BigInteger userId, boolean activeStatus){
        return (int) tokenStoreRepository.countByUidAndActiveStatus(userId,activeStatus);
    }

    @Override
    public void deactivateOlderTokenCountByUid(int count, BigInteger userId){
        deactivate(tokenStoreRepository.findAllByUid(new PageImpl(count),userId,true));
    }

    @Override
    public int countTokenByUserIdIpAndStatus(BigInteger userId, String ipAddress, boolean activeStatus){
        return (int) tokenStoreRepository.countByUidIpAndActiveStatus(userId,ipAddress,activeStatus);
    }

    @Override
    public void deactivateOlderTokenCountByUidAndIp(int count, BigInteger userId, String ipAddress){
        deactivate(tokenStoreRepository.findAllByUidAndIp(new PageImpl(count),userId,ipAddress,true));
    }

    @Override
    public void deactivate(String token) {
        updateTokenActiveStatus(token,false);
    }

    @Override
    public void deactivateAllByUidRatherThanIp(BigInteger userId, String ipAddress) {
        deactivate(tokenStoreRepository.findAllByUidRatherThanCurrentIp(userId,ipAddress));
    }

    @Override
    public void deactivateAllByUidRatherThanToken(BigInteger userId, String token) {
        deactivate(tokenStoreRepository.findAllByUidRatherThanCurrentToken(userId,token));
    }

    private void deactivate(List<TokenStore> tokenStores) {
        tokenStores.forEach(tokenStore->{
            updateTokenActiveStatus(tokenStore,false);
        });
    }

    private void updateTokenActiveStatus(TokenStore tokenStore,boolean activeStatus) {
        tokenStore.setActive(activeStatus);
        auditAware.awareUpdate(tokenStore);
        tokenStoreRepository.save(tokenStore);
    }
}
