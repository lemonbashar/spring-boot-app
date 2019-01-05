package com.lemon.spring.aop.audit;

import com.lemon.spring.annotation.AutoAudit;
import com.lemon.spring.component.audit.AuditAware;
import com.lemon.spring.domain.AbstractAudit;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.context.annotation.Configuration;

import javax.inject.Inject;

@Configuration
@Aspect
public class AuditAwareAspect {
    @Inject
    private AuditAware auditAware;

    @Pointcut("execution(* org.springframework.data.jpa.repository.JpaRepository.save(..))")
    public void repositorySaveCallPointcut() {}

    @Pointcut("execution(* com.lemon.framework.orm.capture.AbstractCapture.persist(..)) || execution(* com.lemon.framework.orm.capture.AbstractCapture.save(..))")
    public void hibernateSessionSaveCall() {}

    @Pointcut("repositorySaveCallPointcut() || hibernateSessionSaveCall()")
    public void allPointcut() {}

    @Before("allPointcut()")
    public void audit(JoinPoint joinPoint) throws Throwable {
        Object obj=joinPoint.getArgs()[0];
        if(obj instanceof AbstractAudit && obj.getClass().isAnnotationPresent(AutoAudit.class)) {
            auditAware.aware((AbstractAudit) obj);
        }
    }
}
