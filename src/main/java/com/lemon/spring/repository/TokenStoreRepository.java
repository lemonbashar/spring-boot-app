package com.lemon.spring.repository;

import com.lemon.spring.domain.TokenStore;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
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
    TokenStore findByUsername(@Param("username") String username);

    @Modifying
    @Transactional
    @Query("UPDATE TokenStore tokenStore SET tokenStore.active=:activeStatus WHERE tokenStore.token=:token")
    void updateTokenStatus(@Param("token") String token, @Param("activeStatus") boolean activeStatus);

    @Modifying
    @Transactional
    @Query("UPDATE TokenStore tokenStore SET tokenStore.active=true WHERE tokenStore.ipAddress=:ipAddress AND tokenStore.user.id=:userId")
    void deactivateTokenByUidAndIp(@Param("userId") BigInteger userId, @Param("ipAddress") String ipAddress);

    @Modifying
    @Transactional
    @Query("UPDATE TokenStore tokenStore SET tokenStore.active=:activeStatus WHERE tokenStore.user.id=:userId")
    void updateTokenStatusByUid(@Param("userId") BigInteger userId, @Param("activeStatus") boolean activeStatus);

    @Query("SELECT count(tokenStore.id) FROM TokenStore tokenStore WHERE tokenStore.user.id=:userId AND tokenStore.active=:activeStatus")
    long countByUidAndActiveStatus(@Param("userId") BigInteger userId, @Param("activeStatus") boolean activeStatus);
}
