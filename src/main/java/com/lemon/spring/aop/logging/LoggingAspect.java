package com.lemon.spring.aop.logging;

import com.lemon.spring.config.Constants;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.core.env.Environment;

import java.util.Arrays;

@Aspect
@Profile(value = Constants.PROFILE_DEVELOPMENT)
public class LoggingAspect {
    private final Logger log = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private Environment env;

    @Pointcut("within(com.lemon.spring.controller.*..*)")
    public void controllerPointcut() {
    }

    @Pointcut("within(com.lemon.spring.repository..*)")
    public void repositoryPointcut() {

    }

    @Pointcut("within(com.lemon.spring.service..*)")
    public void servicePointcut() {

    }

    @Pointcut("controllerPointcut() || repositoryPointcut() || servicePointcut()")
    public void allPointcut() {

    }

    @AfterThrowing(pointcut = "allPointcut()", throwing = "e")
    public void logAfterThrowing(JoinPoint joinPoint, Throwable e) {
        if (env.acceptsProfiles(Constants.PROFILE_DEVELOPMENT)) {
            log.error("Exception in {}.{}() with cause = {}", joinPoint.getSignature().getDeclaringTypeName(),
                    joinPoint.getSignature().getName(), e.getCause(), e);
        } else {
            log.error("Exception in {}.{}() with cause = {}", joinPoint.getSignature().getDeclaringTypeName(),
                    joinPoint.getSignature().getName(), e.getCause());
        }
    }

    @Around("allPointcut()")
    public Object logAround(ProceedingJoinPoint joinPoint) throws Throwable {
        log.debug("Enter: {}.{}() with argument[s] = {}", joinPoint.getSignature().getDeclaringTypeName(),
                joinPoint.getSignature().getName(), Arrays.toString(joinPoint.getArgs()));
        try {
            Object result = joinPoint.proceed();
            log.debug("Exit: {}.{}() with result = {}", joinPoint.getSignature().getDeclaringTypeName(),
                    joinPoint.getSignature().getName(), result);
            return result;
        } catch (IllegalArgumentException e) {
            log.error("Illegal argument: {} in {}.{}()", Arrays.toString(joinPoint.getArgs()),
                    joinPoint.getSignature().getDeclaringTypeName(), joinPoint.getSignature().getName());

            throw e;
        }
    }
}
