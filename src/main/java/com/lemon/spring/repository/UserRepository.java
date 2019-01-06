package com.lemon.spring.repository;

import com.lemon.spring.domain.UserModel;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigInteger;
import java.time.LocalDate;
import java.util.List;


@Repository
@Transactional
public interface UserRepository extends JpaRepository<UserModel, BigInteger> {
    @Query("SELECT user FROM UserModel user WHERE user.username=:username")
    UserModel findOneByUsername(@Param("username") String username);

    @Query("SELECT user FROM UserModel user INNER JOIN user.authorities auth WHERE auth.name=:authority")
    List<UserModel> findAllByAuthority(@Param("authority") String authority);

    @Query("SELECT user FROM UserModel user INNER JOIN user.authorities auth WHERE auth.name=:authority")
    List<UserModel> findAllByAuthority(Pageable pageable, @Param("authority") String authority);

    @Query("SELECT user FROM UserModel user WHERE user.active=false AND user.updateDate<:date")
    List<UserModel> findAllInactiveAndBeforeUpdatedLastDate(@Param("date") LocalDate date);
}
