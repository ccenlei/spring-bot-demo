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

import com.github.hui.quick.plugin.qrcode.wrapper.QrCodeGenWrapper;
import com.github.hui.quick.plugin.qrcode.wrapper.QrCodeOptions;

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

    public static void main(String[] args) throws Exception {
        KeyPair kPair = geneRSAKeyPair(1024);
        PublicKey pubKey = kPair.getPublic();
        PrivateKey priKey = kPair.getPrivate();

        String priKeyStr = encryptBase64(priKey.getEncoded());
        System.out.println(priKeyStr);
        System.out.println();

        String encryptStr = encryptRSA("てらとちはち", pubKey);
        System.out.println(encryptStr);
        System.out.println();

        String decryptStr = decryptRSA(encryptStr, priKeyStr);
        System.out.println(decryptStr);

        String qrDir = "/Users/coushi/Documents/Developments/qr/tmp";
        String logo = "/Users/coushi/Documents/Developments/qr/Pasted6.png";

        boolean ans = QrCodeGenWrapper.of(encryptStr)
                .setW(400)
                .setLogo(logo)
                .setLogoStyle(QrCodeOptions.LogoStyle.ROUND)
                .setLogoBgColor(0xfffefefe)
                .setLogoBorderBgColor(0xffc7c7c7)
                .setLogoBorder(true)
                .asFile(qrDir + "/lqr4.png");
        System.out.println(ans);
    }
}
