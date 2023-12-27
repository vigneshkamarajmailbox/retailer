package com.botree.retailerssfa.util;

/**
 * Created by abdullahselek on 08/06/15.
 */

import android.util.Base64;

import com.botree.retailerssfa.db.FileAccessUtil;

import java.io.UnsupportedEncodingException;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.InvalidParameterSpecException;
import java.security.spec.KeySpec;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.PBEKeySpec;
import javax.crypto.spec.SecretKeySpec;

public class AESEncryption {

    protected static final byte[] SALT = {
            (byte) 0xA9, (byte) 0x9B, (byte) 0xC8, (byte) 0x32,
            (byte) 0x56, (byte) 0x35, (byte) 0xE3, (byte) 0x03
    };

    private static final int ITERATION_COUNT = 65536;
    private static final int KEY_LENGTH = 256;
    private Cipher encryptCipher;
    private Cipher decryptCipher;

    /**
     * Initiates AESEncryption with password key
     * @param passwordKey
     * @throws Exception
     */
    public AESEncryption(String passwordKey) throws  NoSuchAlgorithmException, InvalidKeySpecException, NoSuchPaddingException, InvalidKeyException, InvalidAlgorithmParameterException, InvalidParameterSpecException {
        SecretKeyFactory factory = FileAccessUtil.getInstance().getSecretKeyFactoryInstance("PBKDF2WithHmacSHA1");
        KeySpec spec = new PBEKeySpec(passwordKey.toCharArray(), SALT, ITERATION_COUNT, KEY_LENGTH);
        SecretKey tempKey = factory.generateSecret(spec);
        SecretKey secret = new SecretKeySpec(tempKey.getEncoded(), "AES");

        encryptCipher = FileAccessUtil.getInstance().getCipherInstance("AES/GCM/NoPadding");
        encryptCipher.init(Cipher.ENCRYPT_MODE, secret);

        decryptCipher = FileAccessUtil.getInstance().getCipherInstance("AES/GCM/NoPadding");
        byte[] iv = encryptCipher.getParameters().getParameterSpec(IvParameterSpec.class).getIV();
        decryptCipher.init(Cipher.DECRYPT_MODE, secret, new IvParameterSpec(iv));
    }

    /**
     * Encrypts given text
     * @param encrypt
     * @return
     * @throws Exception
     */
    public String encrypt(String encrypt) throws  UnsupportedEncodingException, BadPaddingException, IllegalBlockSizeException {
        byte[] bytes = encrypt.getBytes("UTF8");
        byte[] encrypted = encrypt(bytes);
        return Base64.encodeToString(encrypted, Base64.DEFAULT);
    }

    /**
     * Encrypts given byte array
     * @param plain
     * @return
     * @throws Exception
     */
    public byte[] encrypt(byte[] plain) throws  BadPaddingException, IllegalBlockSizeException {
        return encryptCipher.doFinal(plain);
    }

    /**
     * Decrypts given text
     * @param encrypt
     * @return
     * @throws Exception
     */
    public String decrypt(String encrypt) throws  BadPaddingException, IllegalBlockSizeException, UnsupportedEncodingException {
        byte[] bytes = Base64.decode(encrypt, Base64.DEFAULT);
        byte[] decrypted = decrypt(bytes);
        return new String(decrypted, "UTF8");
    }

    /**
     * Decrypts given byte array
     * @param encrypt
     * @return
     * @throws Exception
     */
    public byte[] decrypt(byte[] encrypt) throws  BadPaddingException, IllegalBlockSizeException {
        return decryptCipher.doFinal(encrypt);
    }

}
