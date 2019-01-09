package com.lemon.spring.service.account.impl;

import com.lemon.spring.domain.User;
import com.lemon.spring.repository.UserRepository;
import com.lemon.spring.service.account.UserService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.inject.Inject;
import java.math.BigInteger;

@Service
public class UserServiceImpl implements UserService {
    @Inject
    private PasswordEncoder passwordEncoder;

    @Inject
    private UserRepository userRepository;


    @Override
    public User userForUpdate(BigInteger id) {
        User user=userRepository.findById(id).get();
        user.setPassword("");
        if(user.getCreateBy()!=null)user.setCreateBy(new User(user.getCreateBy().getId()));
        if(user.getUpdateBy()!=null)user.setUpdateBy(new User(user.getUpdateBy().getId()));

        return user;
    }

    @Override
    public void updateUser(User user) {
        if(!user.getPassword().isEmpty())user.setPassword(passwordEncoder.encode(user.getPassword()));
        else user.setPassword(userRepository.findById(user.getId()).get().getPassword());
        userRepository.save(user);
    }
}
