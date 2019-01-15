package com.lemon.spring.component.security.method;

import com.lemon.framework.security.auth.AuthorizationBridge;
import com.lemon.spring.domain.Authority;
import org.springframework.security.access.PermissionEvaluator;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

import java.io.Serializable;

@SuppressWarnings("WeakerAccess")
@Component
public class GlobalPermissionEvaluator implements PermissionEvaluator {
    public static final String READ="READ";
    public static final String WRITE="WRITE";
    public static final String IS_ADMIN="IS_ADMIN";

    private AuthorizationBridge authorizationBridge;

    /**
     * permission is the permission type of like hasPermission(,'READ')
     * */
    @Override
    public boolean hasPermission(Authentication authentication, Object target, Object permission) {
        switch (permission.toString()) {
            case READ:
                return true;
            case WRITE:
                return target instanceof Authority;
            case IS_ADMIN:
                return authorizationBridge.isAdmin();
        }
        return false;
    }

    @Override
    public boolean hasPermission(Authentication authentication, Serializable targetId, String targetType, Object permission) {
        return false;
    }
}
