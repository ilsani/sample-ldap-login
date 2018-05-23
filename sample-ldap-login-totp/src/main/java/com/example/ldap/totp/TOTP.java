package com.example.ldap.totp;

public final class TOTP {

    private final String value;
    private final long time;
    private final HmacShaAlgorithm hmacShaAlgorithm;
    private final int digits;
    private final long timeStep;

    TOTP(String value, long time, HmacShaAlgorithm hmacShaAlgorithm, int digits, long timeStep) {
        this.value = value;
        this.time = time;
        this.hmacShaAlgorithm = hmacShaAlgorithm;
        this.digits = digits;
        this.timeStep = timeStep;
    }

    public static TOTPBuilder key(byte[] key) {
        return new TOTPBuilder(key);
    }

    public String value() {
        return value;
    }


    public long time() {
        return time;
    }

    public HmacShaAlgorithm hmacShaAlgorithm() {
        return hmacShaAlgorithm;
    }

    public int digits() {
        return digits;
    }

    public long timeStep() {
        return timeStep;
    }

    @Override
    public int hashCode() {
        return value.hashCode();
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        TOTP other = (TOTP) obj;
        return value.equals(other.value);
    }

}
