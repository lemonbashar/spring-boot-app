package com.lemon.spring.repository;

import com.lemon.spring.domain.TokenStore;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigInteger;
import java.util.Date;

@Repository
@Transactional
public interface TokenStoreRepository extends JpaRepository<TokenStore, BigInteger> {

    @Query("SELECT tokenStore FROM TokenStore tokenStore WHERE tokenStore.token=:token AND tokenStore.active=true AND tokenStore.validateDate<=:validateDate")
    TokenStore findByValidateDate(@Param("token") String token, @Param("validateDate") Date validateDate);

    @Query("SELECT tokenStore FROM TokenStore tokenStore WHERE tokenStore.token=:token AND tokenStore.active=:active")
    TokenStore findByActiveStatus(@Param("token") String token, @Param("active") boolean active);

    @Query("SELECT tokenStore FROM TokenStore tokenStore WHERE tokenStore.user.username=:username")
    String findByUsername(@Param("username") String username);
}
