package com.lemon.spring.config.security.encoder;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

/**
 * Created by lemon on 10/1/18.
 */

@SuppressWarnings({"unused", "DefaultFileTemplate"})
@Component
public class SimplePasswordEncoder implements PasswordEncoder {
    @Override
    public String encode(CharSequence charSequence) {
        return charSequence.toString();
    }

    @Override
    public boolean matches(CharSequence charSequence, String encodedPassword) {
        return encode(charSequence).equals(encodedPassword);
    }
}
