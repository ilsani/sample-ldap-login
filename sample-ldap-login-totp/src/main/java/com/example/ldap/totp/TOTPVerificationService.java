package com.example.ldap.totp;

import org.springframework.stereotype.Service;

import org.apache.commons.codec.binary.Base32;

import java.util.concurrent.TimeUnit;

@Service
public class TOTPVerificationService {

    private final int timeStep = 20;
    private final int digits = 8;

    public boolean validate(String secret, String clientTOTP) {

        byte[] secretBytes = new Base32().decode(secret);

        // Client TOTP verification based on https://github.com/johnnymongiat/oath code (lighter version => less code and refactor)
        boolean valid = TOTPValidator.defaultWindow().isValid(secretBytes, TimeUnit.SECONDS.toMillis(timeStep), digits,
                HmacShaAlgorithm.HMAC_SHA_256, clientTOTP);

        return valid;
    }

}
