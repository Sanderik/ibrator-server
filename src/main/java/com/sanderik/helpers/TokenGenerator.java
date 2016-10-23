package com.sanderik.helpers;

import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Service;

import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.SecureRandom;

@Service
public final class TokenGenerator {
    private SecureRandom random = new SecureRandom();

    @Bean
    public String generateToken() {
        return sha256(new BigInteger(130, random).toString(32));
    }

    private String sha256(String string){
        try{
            MessageDigest digest = MessageDigest.getInstance("SHA-256");
            byte[] hash = digest.digest(string.getBytes("UTF-8"));
            StringBuilder hexString = new StringBuilder();

            for (byte aHash : hash) {
                String hex = Integer.toHexString(0xff & aHash);
                if (hex.length() == 1) hexString.append('0');
                hexString.append(hex);
            }

            return hexString.toString();
        } catch(Exception ex){
            throw new RuntimeException(ex);
        }
    }
}
