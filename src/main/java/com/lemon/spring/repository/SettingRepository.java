package com.lemon.spring.repository;

import com.lemon.spring.domain.internal.Setting;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigInteger;

@Repository
@Transactional
public interface SettingRepository extends JpaRepository<Setting, BigInteger> {
    Setting findOneBySettingKeyAndActive(String settingKey,Boolean active);
}
