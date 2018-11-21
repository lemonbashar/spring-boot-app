package com.lemon.spring.service.account.impl;

import com.lemon.spring.domain.Authority;
import com.lemon.spring.domain.User;
import com.lemon.spring.service.account.AccountService;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.inject.Inject;
import java.util.HashSet;
import java.util.Set;

@SuppressWarnings({"unchecked", "JpaQlInspection"})
@Service
public class AccountServiceImpl implements AccountService {
    /*@Inject
    private UserRepository userRepository;*/
    @Inject
    private SessionFactory sessionFactory;

    @Inject
    private PasswordEncoder passwordEncoder;

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
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        Set<Authority> authorities=new HashSet<>();

        try(Session session=sessionFactory.openSession()) {
            session.beginTransaction();
            user.getAuthorities().forEach(v->{
                if(session.createQuery("SELECT auth FROM Authority auth WHERE auth.name='"+v.getName()+"'").list().size()<=0) {
                    v= (Authority) session.save(v);
                }
                authorities.add(v);
            });
            user.setAuthorities(authorities);
            session.persist(user);
            session.getTransaction().commit();
        }
    }

    @Override
    public Set<Authority> authorities() {
        Set<Authority> authorities=new HashSet<>();
        try(Session session=sessionFactory.openSession()) {
            authorities=new HashSet<>(session.createQuery("SELECT auth FROM Authority auth").list());
        }
        return authorities;
    }
}
