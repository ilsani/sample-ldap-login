package com.example.ldap.totp;

/**
 * A Time-based One-time Password (TOTP) validator.
 * https://tools.ietf.org/html/rfc6238#section-5.2
 */
public final class TOTPValidator {

    /** The default window verification size. */
    public static final int DEFAULT_WINDOW = 1;

    private final int window;

    /**
     * Creates a new instance of {@code TOTPValidator} initialized with the
     * specified {@code window} verification size.
     */
    private TOTPValidator(int window) {
        this.window = window;
    }

    /**
     * Returns a new {@link TOTPValidator} instance initialized with the
     * {@link #DEFAULT_WINDOW} verification size.
     */
    public static TOTPValidator defaultWindow() {
        return window(DEFAULT_WINDOW);
    }

    /**
     * Returns a new {@link TOTPValidator} instance initialized with the
     * specified {@code window} verification size.
     */
    public static TOTPValidator window(int window) {
        return new TOTPValidator(window);
    }

    /**
     * Returns {@code true} if the specified TOTP {@code value} matches the
     * value of the TOTP generated at validation, otherwise {@code false}. The
     * current system time (current time in milliseconds since the UNIX epoch)
     * is used as the validation reference time.
     */
    public boolean isValid(byte[] key, long timeStep, int digits, HmacShaAlgorithm hmacShaAlgorithm, String value) {
        return isValid(key, timeStep, digits, hmacShaAlgorithm, value, System.currentTimeMillis());
    }

    /**
     * Returns {@code true} if the specified TOTP {@code value} matches the
     * value of the TOTP generated at validation, otherwise {@code false}.
     */
    public boolean isValid(byte[] key, long timeStep, int digits, HmacShaAlgorithm hmacShaAlgorithm, String value, long validationTime) {
        boolean result = false;
        TOTPBuilder builder = TOTP.key(key).timeStep(timeStep).digits(digits).hmacSha(hmacShaAlgorithm);
        for (int i = -window; i <= window; i++) {
            final long time = validationTime + (i * timeStep);
            final TOTP vtotp = builder.build(time);
            if (vtotp.value().equals(value)) {
                result = true;
                break;
            }
        }
        return result;
    }

}
