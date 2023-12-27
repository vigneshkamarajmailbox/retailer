package com.botree.rapp.masterengine.util;

import com.botree.rapp.masterengine.exception.ServiceException;
import com.botree.rapp.masterengine.constants.StringConstants;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * PasswordEncryptor class to digest the password.
 * @author vinodkumara
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
            if ((StringConstants.MASK1 & bytes[i]) < StringConstants.MASK2) {
                buf.append("0" + Integer.toHexString(StringConstants.MASK3 & bytes[i]));
            } else {
                buf.append(Integer.toHexString(StringConstants.MASK3 & bytes[i]));
            }
        }
        return buf.toString();
    }

}
