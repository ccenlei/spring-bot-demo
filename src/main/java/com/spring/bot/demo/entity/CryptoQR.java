package com.spring.bot.demo.entity;

import java.security.KeyPair;

import java.security.PrivateKey;
import java.security.PublicKey;

import com.github.hui.quick.plugin.qrcode.wrapper.QrCodeGenWrapper;
import com.github.hui.quick.plugin.qrcode.wrapper.QrCodeOptions;
import com.spring.bot.demo.utils.RSACryptoUtils;

public class CryptoQR {

    private final String qrDir = "/Users/coushi/Documents/Developments/qr/tmp/";

    private final String logo = "/Users/coushi/Documents/Developments/qr/Pasted6.png";

    private final int keysize = 1024;

    private final String tapStr = "てら";

    public String exportQRCodeImg(String keyWords) throws Exception {
        KeyPair kPair = RSACryptoUtils.geneRSAKeyPair(this.keysize);
        PublicKey pubKey = kPair.getPublic();
        PrivateKey priKey = kPair.getPrivate();

        String priKeyStr = RSACryptoUtils.encryptBase64(priKey.getEncoded());
        String encryptStr = riseNoise(RSACryptoUtils.encryptRSA(keyWords, pubKey));
        String textline = encryptStr + this.tapStr + priKeyStr;

        boolean ans = QrCodeGenWrapper.of(encryptStr)
                .setW(400)
                .setLogo(this.logo)
                .setLogoStyle(QrCodeOptions.LogoStyle.ROUND)
                .setLogoBgColor(0xfffefefe)
                .setLogoBorderBgColor(0xffc7c7c7)
                .setLogoBorder(true)
                .setLogoRate(10)
                .asFile(this.qrDir + "lqr4.png");
        System.out.println(ans);

        return textline;
    }

    public String decryptStr(String textline) throws Exception {
        String[] args = textline.split(this.tapStr);
        String encryptStr = args[0];
        String priKeyStr = args[1];
        return decryptStr(encryptStr, priKeyStr);
    }

    public String decryptStr(String encryptStr, String priKeyStr) throws Exception {
        String decryptStr = RSACryptoUtils.decryptRSA(reduceNoise(encryptStr), priKeyStr);
        return decryptStr;
    }

    private String riseNoise(final String oriStr) {
        String tarStr = oriStr + "    ";
        return tarStr;
    }

    private String reduceNoise(final String oriStr) {
        String tarStr = oriStr.strip();
        return tarStr;
    }

    public static void main(String[] args) throws Exception {
        String keyWords = "bbbbbbbbbb";
        CryptoQR cQr = new CryptoQR();
        String textline = cQr.exportQRCodeImg(keyWords);

        System.out.println(textline);
        System.out.println(cQr.decryptStr(textline));
    }
}
