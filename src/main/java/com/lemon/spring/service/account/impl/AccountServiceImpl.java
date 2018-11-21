package com.lemon.spring.service.account.impl;

import com.lemon.spring.domain.User;
import com.lemon.spring.repository.UserRepository;
import com.lemon.spring.service.account.AccountService;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.stereotype.Service;

import javax.inject.Inject;

@Service
public class AccountServiceImpl implements AccountService {
    /*@Inject
    private UserRepository userRepository;*/
    @Inject
    private SessionFactory sessionFactory;

    @Override
    public String currentUsername() {
        return "lemon";
    }

    @Override
    public User login(String username, String password) {
        return username.equals(currentUsername())&&password.equals("123456")?new User(1L,username,password):null;
    }

    @Override
    public void register(User user) {
        try(Session session=sessionFactory.openSession()) {
            session.beginTransaction();
            session.persist(user);
            session.getTransaction().commit();
        }
    }
}
