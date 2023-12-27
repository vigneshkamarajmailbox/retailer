package com.botree.retailerssfa.util;

import android.graphics.Color;

import com.botree.retailerssfa.R;

public enum PasswordStrengthChecker {
    WEAK(R.string.password_strength_weak, Color.RED),
    MEDIUM(R.string.password_strength_medium, Color.argb(255, 220, 185, 0)),
    STRONG(R.string.password_strength_strong, Color.GREEN),
    VERY_STRONG(R.string.password_strength_very_strong, Color.BLUE);

    //--------REQUIREMENTS--------
    static int requiredLength = 8;
    static int maximumLength = 15;
    static boolean requireSpecialCharacters = true;
    static boolean requireDigits = true;
    static boolean requireLowerCase = true;
    static boolean requireUpperCase = false;

    int resId;
    int color;
    static int currentScore = 0;

    PasswordStrengthChecker(int resId, int color) {
        this.resId = resId;
        this.color = color;
    }

    public CharSequence getText(android.content.Context ctx) {
        return ctx.getText(resId);
    }

    public int getColor() {
        return color;
    }

    public static PasswordStrengthChecker calculateStrength(String password) {
        currentScore = 0;
        boolean sawUpper = false;
        boolean sawLower = false;
        boolean sawDigit = false;
        boolean sawSpecial = false;

        for (int i = 0; i < password.length(); i++) {
            char c = password.charAt(i);
            if (!sawSpecial && !Character.isLetterOrDigit(c)) {
                currentScore += 1;
                sawSpecial = true;
            } else {
                if (!sawDigit && Character.isDigit(c)) {
                    currentScore += 1;
                    sawDigit = true;
                } else {
                    if (!sawUpper || !sawLower) {
                        if (Character.isUpperCase(c))
                            sawUpper = true;
                        else
                            sawLower = true;
                        if (sawUpper && sawLower)
                            currentScore += 1;
                    }
                }
            }
        }
        return getStrength(getScore(password, sawUpper, sawLower, sawDigit, sawSpecial));
    }

    static PasswordStrengthChecker getStrength(int currentScore) {

        switch (currentScore) {
            case 0:
                return WEAK;
            case 1:
                return MEDIUM;
            case 2:
                return STRONG;
            case 3:
                return VERY_STRONG;
            default:
        }
        return VERY_STRONG;
    }

    static int getScore(String password, boolean sawUpper, boolean sawLower, boolean sawDigit, boolean sawSpecial) {
        if (password.length() > requiredLength) {
            if ((requireSpecialCharacters && !sawSpecial)
                    || (requireUpperCase && !sawUpper)
                    || (requireLowerCase && !sawLower)
                    || (requireDigits && !sawDigit)) {
                currentScore = 1;
            } else {
                currentScore = 2;
                if (password.length() > maximumLength) {
                    currentScore = 3;
                }
            }
        } else {
            currentScore = 0;
        }
        return currentScore;
    }

}
