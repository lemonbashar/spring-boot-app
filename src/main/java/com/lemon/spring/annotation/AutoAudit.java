package com.lemon.spring.annotation;

import com.lemon.spring.enumeretion.audit.AutoActive;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Ensure that The Audited Entity is Make Active or Not when persist,save or update
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
public @interface AutoAudit {
    AutoActive autoActive() default AutoActive.NONE;

    String[] activeInactiveRole() default {};
}
