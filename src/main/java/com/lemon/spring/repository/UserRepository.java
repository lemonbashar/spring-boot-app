package com.lemon.spring.repository;

import com.lemon.spring.domain.internal.User;
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
public interface UserRepository extends JpaRepository<User, BigInteger> {
    @Query("SELECT user FROM User user WHERE user.username=:username")
    User findOneByUsername(@Param("username") String username);

    @Query("SELECT user FROM User user INNER JOIN user.authorities auth WHERE auth.name=:authority")
    List<User> findAllByAuthority(@Param("authority") String authority);

    @Query("SELECT user FROM User user INNER JOIN user.authorities auth WHERE auth.name=:authority")
    List<User> findAllByAuthority(Pageable pageable, @Param("authority") String authority);

    @Query("SELECT user FROM User user WHERE user.active=false AND user.updateDate<:date")
    List<User> findAllInactiveAndBeforeUpdatedLastDate(@Param("date") LocalDate date);

    @Query("SELECT user FROM User user WHERE user.email=:email")
    User findOneByEmail(@Param("email") String userEmail);
}
