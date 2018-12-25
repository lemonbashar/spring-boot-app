package com.lemon.spring.component.security.encoder;

import com.lemon.framework.properties.ApplicationProperties;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import javax.inject.Inject;
import java.util.Base64;

/**
 * Created by lemon on 10/1/18.
 */

@SuppressWarnings({"unused", "DefaultFileTemplate"})
@Component
public class SimplePasswordEncoder implements PasswordEncoder {
    @Inject
    private ApplicationProperties properties;

    @Override
    public String encode(CharSequence charSequence) {
        return Base64.getEncoder().encodeToString((charSequence + properties.settings.security.secretKey).getBytes());
    }

    @Override
    public boolean matches(CharSequence charSequence, String encodedPassword) {
        return encode(charSequence).equals(encodedPassword);
    }
}
