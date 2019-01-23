package com.lemon.spring.repository;

import com.lemon.spring.domain.PasswordRecover;
import com.lemon.spring.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigInteger;

@Repository
@Transactional
public interface PasswordRecoverRepository extends JpaRepository<PasswordRecover, BigInteger> {

    PasswordRecover findByUser(User user);

}
