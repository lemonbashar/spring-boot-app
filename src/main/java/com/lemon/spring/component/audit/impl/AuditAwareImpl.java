package com.lemon.spring.component.audit.impl;

import com.lemon.spring.component.audit.AuditAware;
import com.lemon.spring.domain.AbstractAudit;
import com.lemon.spring.domain.UserModel;
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
        if(!audit.isUpdate())awareCreate(audit);
        else awareUpdate(audit);
    }

    @Override
    public void awareUpdate(AbstractAudit audit) {
        audit.setUpdateBy(new UserModel(findCreatorId()));
        audit.setUpdateDate(LocalDate.now());
    }

    private BigInteger findCreatorId() {
        try {
            return SecurityUtils.currentUserId();
        } catch (Throwable e) {
        }
        try {
            return userRepository.findOneByUsername("system").getId();
        } catch (Throwable e) {
        }
        try {
            return userRepository.findAllByAuthority(new PageImpl(1), AuthoritiesConstant.ROLE_ADMIN).get(0).getId();
        } catch (Throwable e) {
        }
        return userRepository.findAll(new PageImpl(1)).getContent().get(0).getId();

    }

    @Override
    public void awareCreate(AbstractAudit audit) {
        audit.setCreateBy(new UserModel(findCreatorId()));
        audit.setCreateDate(LocalDate.now());
    }
}
