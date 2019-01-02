package com.lemon.spring.component.security;

import com.lemon.framework.orm.capture.hbm.HbmCapture;
import com.lemon.framework.springsecurity.data.CustomUserDetails;
import com.lemon.spring.domain.User;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import javax.inject.Inject;
import java.util.stream.Collectors;

/**
 * Created by lemon on 10/1/18.
 */

@SuppressWarnings({"unused", "DefaultFileTemplate"})
@Component
public class CustomUserDetailsService implements UserDetailsService {
    @Inject
    private HbmCapture hbmCapture;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return userByUsername(username);
    }

    private UserDetails userByUsername(String username) {
        User user;
        user= (User) hbmCapture.findOne("SELECT user FROM User user WHERE user.username='"+username+"'");
        if(user==null) throw new UsernameNotFoundException("User with Username :"+username +"Not Found");
        return new CustomUserDetails(user.getId(),user.getUsername(),user.getPassword(),user.isActive(),user.getAuthorities().stream().map(val->new SimpleGrantedAuthority(val.getName())).collect(Collectors.toSet()));
    }
}
