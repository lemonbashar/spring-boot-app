package com.lemon.spring.service.account.impl;

import com.lemon.framework.data.JWToken;
import com.lemon.framework.data.LogoutInfo;
import com.lemon.framework.data.UserInfo;
import com.lemon.framework.orm.capture.hbm.HbmCapture;
import com.lemon.framework.protocolservice.auth.AccountService;
import com.lemon.framework.springsecurity.jwt.JwtAuthManager;
import com.lemon.framework.springsecurity.session.SessionAuthManager;
import com.lemon.spring.domain.Authority;
import com.lemon.spring.domain.User;
import com.lemon.spring.security.SecurityUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.inject.Inject;
import java.math.BigInteger;
import java.util.HashSet;
import java.util.Set;

@SuppressWarnings({"unchecked", "JpaQlInspection", "SpringJavaAutowiredFieldsWarningInspection"})
@Service
public class AccountServiceImpl implements AccountService<BigInteger> {

    private final Logger log= LogManager.getLogger(AccountServiceImpl.class);

    @Inject
    private HbmCapture hbmCapture;

    @Inject
    private PasswordEncoder passwordEncoder;

    @Autowired(required = false)
    private JwtAuthManager jwtAuthManager;

    @Inject
    private SessionAuthManager sessionAuthManager;

    @Override
    public String currentUsername() {
        return SecurityUtils.currentUserLogin();
    }

    @Override
    public BigInteger currentUserId() {
        return SecurityUtils.currentUserId();
    }

//    @Override
//    public boolean login(UserInfo userInfo) {
//        return sessionAuthManager.authenticate(userInfo)!=null;
//    }

    @Override
    public void register(Object userObj) {
        User user= (User) userObj;
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
    public Set<Object> authorities() {
        return new HashSet<>(hbmCapture.getAll("SELECT auth FROM Authority auth"));
    }

    @Override
    public JWToken authenticate(UserInfo userInfo) {
        /*If The App is Stateful-Only*/
        if(jwtAuthManager==null) {
            sessionAuthManager.authenticate(userInfo);
            return new JWToken("");
        }
        try {
            /*If The app stateless or both*/
            return jwtAuthManager.authenticate(userInfo).token();
        } catch (NullPointerException e) {
            throw new SecurityException("May-Be your Application is Not Enabled For Token-Based Authentication");
        }
    }

    @Override
    public void logout(LogoutInfo logoutInfo) {
        logoutInfo.setUserId(SecurityUtils.currentUserId());
        jwtAuthManager.logout(logoutInfo);
    }


}
