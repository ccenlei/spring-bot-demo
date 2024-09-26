package com.spring.bot.demo.utils;

import java.security.KeyFactory;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.Security;
import java.security.spec.PKCS8EncodedKeySpec;
import java.util.Base64;

import javax.crypto.Cipher;

import org.bouncycastle.jce.provider.BouncyCastleProvider;

public class RSACryptoUtils {

    static final String KEY_ALGORITHM = "RSA";

    static final String CHAR_SET = "UTF-8";

    static final String TRANS = "RSA/ECB/PKCS1Padding";

    public static String decryptRSA(String encryptBase64Str, String pKeyBase64Str) throws Exception {
        byte[] encryptBytes = decryptBase64(encryptBase64Str);
        byte[] pKeyBytes = decryptBase64(pKeyBase64Str);
        PKCS8EncodedKeySpec keySpec = new PKCS8EncodedKeySpec(pKeyBytes);
        PrivateKey pKey = KeyFactory.getInstance(KEY_ALGORITHM).generatePrivate(keySpec);
        return decryptRSA(encryptBytes, pKey);
    }

    public static String decryptRSA(byte[] encryptBytes, PrivateKey pKey) throws Exception {
        Cipher cipher = Cipher.getInstance(TRANS);
        cipher.init(Cipher.DECRYPT_MODE, pKey);
        byte[] decryptBytes = cipher.doFinal(encryptBytes);
        return new String(decryptBytes, CHAR_SET);
    }

    public static String encryptRSA(String plaintest, PublicKey pKey) throws Exception {
        Cipher cipher = Cipher.getInstance(TRANS);
        cipher.init(Cipher.ENCRYPT_MODE, pKey);
        byte[] encryptBytes = cipher.doFinal(plaintest.getBytes(CHAR_SET));
        return encryptBase64(encryptBytes);
    }

    public static KeyPair geneRSAKeyPair(final int keysize) throws NoSuchAlgorithmException {
        Security.addProvider(new BouncyCastleProvider());
        KeyPairGenerator kPairGenerator = KeyPairGenerator.getInstance(KEY_ALGORITHM);
        kPairGenerator.initialize(keysize);
        KeyPair kPair = kPairGenerator.genKeyPair();
        return kPair;
    }

    public static String encryptBase64(byte[] keyBytes) {
        return new String(Base64.getEncoder().encode(keyBytes));
    }

    public static byte[] decryptBase64(String keyBase64Str) {
        return Base64.getDecoder().decode(keyBase64Str);
    }
}
