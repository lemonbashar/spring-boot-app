package com.lemon.spring.service.account.impl;

import com.lemon.framework.security.auth.AuthorizationBridge;
import com.lemon.spring.domain.User;
import com.lemon.spring.repository.UserRepository;
import com.lemon.spring.service.account.UserService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.inject.Inject;
import java.math.BigInteger;

import static com.lemon.spring.security.AuthoritiesConstant.ROLES_FOR_ADMIN;

@Service
public class UserServiceImpl implements UserService {
    @Inject
    private PasswordEncoder passwordEncoder;

    @Inject
    private UserRepository userRepository;

    @Inject
    private AuthorizationBridge authorizationBridge;


    @Override
    public User userForUpdate(BigInteger id) {
        User user = userRepository.findById(id).get();
        user.setPassword("");
        if (user.getCreateBy() != null) user.setCreateBy(new User(user.getCreateBy().getId()));
        if (user.getUpdateBy() != null) user.setUpdateBy(new User(user.getUpdateBy().getId()));
        return user;
    }

    @Override
    public void updateUser(User user) {
        User prevUser = userRepository.findById(user.getId()).get();
        if (!user.getPassword().isEmpty()) user.setPassword(passwordEncoder.encode(user.getPassword()));
        else user.setPassword(prevUser.getPassword());
        if (!authorizationBridge.hasAnyAuthority(ROLES_FOR_ADMIN)) {
            user.setAuthorities(prevUser.getAuthorities());
            user.setActive(prevUser.getActive());
            user.setUsername(prevUser.getUsername());
            user.setEmail(prevUser.getEmail());
        }
        userRepository.save(user);
    }
}
