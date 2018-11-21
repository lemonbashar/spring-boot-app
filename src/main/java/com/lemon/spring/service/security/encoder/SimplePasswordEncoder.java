package com.lemon.spring.service.security.encoder;

import com.lemon.spring.config.properties.ApplicationProperties;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.inject.Inject;
import java.util.Base64;

/**
 * Created by lemon on 10/1/18.
 */

@SuppressWarnings({"unused", "DefaultFileTemplate"})
@Service
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
