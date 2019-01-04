package com.lemon.spring.repository;

import com.lemon.spring.domain.TokenStore;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.Cacheable;
import java.math.BigInteger;
import java.time.LocalDate;
import java.util.Date;
import java.util.List;

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

    @Query("SELECT tokenStore FROM TokenStore tokenStore WHERE tokenStore.createDate<:givenDate")
    List<TokenStore> findAllBeforeDate(@Param("givenDate") LocalDate givenDate);

    @Query("SELECT tokenStore FROM TokenStore tokenStore WHERE tokenStore.token=:token")
    TokenStore findByToken(@Param("token") String token);

    @Query("SELECT tokenStore FROM TokenStore tokenStore WHERE tokenStore.user.id=:userId")
    List<TokenStore> findAllByUid(@Param("userId") BigInteger userId);

    @Query("SELECT tokenStore FROM TokenStore tokenStore WHERE tokenStore.user.id=:userId ORDER BY tokenStore.id ASC ")
    List<TokenStore> findAllByUid(Pageable pageable, @Param("userId") BigInteger userId);

    @Query("SELECT tokenStore FROM TokenStore tokenStore WHERE tokenStore.user.id=:userId AND tokenStore.active=:activeStatus ORDER BY tokenStore.id ASC  ")
    List<TokenStore> findAllByUid(Pageable pageable, @Param("userId") BigInteger userId, @Param("activeStatus") boolean activeStatus);

    @Query("SELECT tokenStore FROM TokenStore tokenStore WHERE tokenStore.user.id=:userId AND tokenStore.ipAddress=:ipAddress")
    List<TokenStore> findAllByUidAndIp(@Param("userId") BigInteger userId , @Param("ipAddress") String ipAddress);

    @Query("SELECT tokenStore FROM TokenStore tokenStore WHERE tokenStore.user.id=:userId AND tokenStore.ipAddress=:ipAddress ORDER BY tokenStore.id ASC ")
    List<TokenStore> findAllByUidAndIp(Pageable pageable,@Param("userId") BigInteger userId , @Param("ipAddress") String ipAddress);

    @Query("SELECT tokenStore FROM TokenStore tokenStore WHERE tokenStore.user.id=:userId AND tokenStore.ipAddress=:ipAddress AND tokenStore.active=:activeStatus ORDER BY tokenStore.id ASC ")
    List<TokenStore> findAllByUidAndIp(Pageable pageable,@Param("userId") BigInteger userId , @Param("ipAddress") String ipAddress, @Param("activeStatus") boolean activeStatus);

    @Query("SELECT tokenStore FROM TokenStore tokenStore WHERE tokenStore.token=:token AND tokenStore.ipAddress=:remoteIpAddress AND tokenStore.active=:activeStatus")
    TokenStore findByTokenIpAndStatus(@Param("token") String token, @Param("remoteIpAddress") String remoteIpAddress, @Param("activeStatus") boolean activeStatus);

    @Query("SELECT count(tokenStore.id) FROM TokenStore tokenStore WHERE tokenStore.user.id=:userId AND tokenStore.createDate>=:fromDate AND tokenStore.createDate<=:toDate")
    long countTokenByDate(@Param("fromDate") LocalDate fromDate, @Param("toDate") LocalDate toDate,@Param("userId") BigInteger userId);

    @Query("SELECT count(tokenStore.id) FROM TokenStore tokenStore WHERE tokenStore.user.id=:userId AND tokenStore.createDate=:date")
    long countTokenByDate(@Param("date") LocalDate date,@Param("userId") BigInteger userId);

    @Query("SELECT count(tokenStore.id) FROM TokenStore tokenStore WHERE tokenStore.user.id=:userId AND tokenStore.ipAddress=:ipAddress AND tokenStore.active=:activeStatus")
    long countByUidIpAndActiveStatus(@Param("userId") BigInteger userId, @Param("ipAddress") String ipAddress, @Param("activeStatus") boolean activeStatus);

    @Query("SELECT tokenStore FROM TokenStore tokenStore WHERE tokenStore.user.id=:userId AND tokenStore.ipAddress !=:ipAddress")
    List<TokenStore> findAllByUidRatherThanCurrentIp(@Param("userId") BigInteger userId, @Param("ipAddress") String ipAddress);

    @Query("SELECT tokenStore FROM TokenStore tokenStore WHERE tokenStore.user.id=:userId AND tokenStore.token !=:token")
    List<TokenStore> findAllByUidRatherThanCurrentToken(@Param("userId") BigInteger userId, @Param("token") String token);
}
