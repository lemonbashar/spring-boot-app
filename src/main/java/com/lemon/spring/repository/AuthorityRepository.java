package com.lemon.spring.repository;

import com.lemon.spring.domain.Authority;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;


@Repository
@Transactional
public interface AuthorityRepository extends JpaRepository<Authority, String> {
}
