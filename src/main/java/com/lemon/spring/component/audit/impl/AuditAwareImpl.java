package com.lemon.spring.component.audit.impl;

import com.lemon.spring.component.audit.AuditAware;
import com.lemon.spring.domain.AbstractAudit;
import com.lemon.spring.domain.User;
import com.lemon.spring.repository.UserRepository;
import com.lemon.spring.security.AuthoritiesConstant;
import com.lemon.spring.security.SecurityUtils;
import com.lemon.spring.web.page.PageImpl;
import org.springframework.stereotype.Component;

import javax.inject.Inject;
import java.math.BigInteger;
import java.time.LocalDate;

@Component
public class AuditAwareImpl implements AuditAware {
    @Inject
    private UserRepository userRepository;


    @Override
    public void aware(AbstractAudit audit) {
        awareCreate(audit);
        awareUpdate(audit);
    }

    @Override
    public void awareUpdate(AbstractAudit audit) {
        audit.setUpdateBy(new User(findCreatorId()));
        audit.setUpdateDate(LocalDate.now());
    }

    private BigInteger findCreatorId() {
        try {
            return SecurityUtils.currentUserId();
        } catch (Exception e) {
            e.printStackTrace();
        }
        try {
            return userRepository.findOneByUsername("system").getId();
        } catch (Exception e) {
            e.printStackTrace();
        }

        return userRepository.findAllByAuthority(new PageImpl(1), AuthoritiesConstant.ROLE_ADMIN).get(0).getId();
    }

    @Override
    public void awareCreate(AbstractAudit audit) {
        audit.setCreateBy(new User(findCreatorId()));
        audit.setCreateDate(LocalDate.now());
    }
}
