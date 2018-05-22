package com.example.ldap.totp;

import org.springframework.stereotype.Service;

import org.apache.commons.codec.binary.Base32;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import java.nio.ByteBuffer;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;

@Service
public class TOTPVerificationService {

    public boolean validate(String secret, String code) throws Exception {

        int icode = -1;
        int variance = 2;

        try {
            icode = Integer.parseInt(code);
        }
        catch (Exception ex) {
            return false;
        }

        long timeIndex = System.currentTimeMillis() / 1000 / 30;
        byte[] secretBytes = new Base32().decode(secret);

        for (int i = -variance; i <= variance; ++i) {
            if (getCode(secretBytes, timeIndex + i) == icode) {
                return true;
            }
        }

        return false;
    }

    private long getCode(byte[] secret, long timeIndex) throws NoSuchAlgorithmException, InvalidKeyException {

        SecretKeySpec signKey = new SecretKeySpec(secret, "HmacSHA1");
        ByteBuffer buffer = ByteBuffer.allocate(8);
        buffer.putLong(timeIndex);
        byte[] timeBytes = buffer.array();
        Mac mac = Mac.getInstance("HmacSHA1");
        mac.init(signKey);
        byte[] hash = mac.doFinal(timeBytes);
        int offset = hash[19] & 0xf;
        long truncatedHash = hash[offset] & 0x7f;
        for (int i = 1; i < 4; i++) {
            truncatedHash <<= 8;
            truncatedHash |= hash[offset + i] & 0xff;
        }
        return truncatedHash %= 1000000;
    }

}
