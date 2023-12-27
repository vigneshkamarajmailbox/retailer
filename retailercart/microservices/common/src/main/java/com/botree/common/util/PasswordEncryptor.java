package com.botree.common.util;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import com.botree.common.constants.StringConstants;
import com.botree.common.exception.ServiceException;

/**
 * Password Encryptor class to digest the password.
 * @author vinodkumar.a
 */
public final class PasswordEncryptor {

    /**
     * Empty constructor.
     */
    private PasswordEncryptor() {

    }

    /**
     * Method to digest the input values for the password.
     * @param password   password
     * @param salt       salt
     * @param isSalesman isSalesman
     * @return response
     */
    public static String digest(final String password, final String salt, final Boolean isSalesman) {
        try {
            var md = MessageDigest.getInstance("MD5");
            md.update(salt.toLowerCase().getBytes());
            if (Boolean.TRUE.equals(isSalesman)) {
                md.digest();
            }
            return toHex(md.digest(password.getBytes()));
        } catch (NoSuchAlgorithmException e) {
            throw new ServiceException(e);
        }
    }

    /**
     * Method to convert the byte[] to string.
     * @param bytes bytes
     * @return string
     */
    private static String toHex(final byte[] bytes) {
        var buf = new StringBuilder();
        for (var i = 0; i < bytes.length; i++) {
            if ((StringConstants.CONST_MASK1 & bytes[i]) < StringConstants.CONST_MASK2) {
                buf.append("0" + Integer.toHexString(StringConstants.CONST_MASK3 & bytes[i]));
            } else {
                buf.append(Integer.toHexString(StringConstants.CONST_MASK3 & bytes[i]));
            }
        }
        return buf.toString();
    }

}
