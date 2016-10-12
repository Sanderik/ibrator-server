package com.sanderik.helpers;

import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Service;

import java.math.BigInteger;
import java.security.SecureRandom;

@Service
public final class TokenGenerator {
    private SecureRandom random = new SecureRandom();

    @Bean
    public String generateToken() {
        return new BigInteger(130, random).toString(32);
    }
}
