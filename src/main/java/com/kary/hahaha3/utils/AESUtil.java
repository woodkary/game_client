package com.kary.hahaha3.utils;

import com.thoughtworks.xstream.core.util.Base64Encoder;
import lombok.Getter;

import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import java.nio.charset.StandardCharsets;

/**
 * @author:123
 */
//创建时必须先传入key，再自动生成获取一个 AES 密钥规范
public class AESUtil {
    /* 加密模式之 ECB，算法/模式/补码方式 */
    private static final String AES_ECB = "AES/ECB/PKCS5Padding";
    /* 加密模式之 CBC，算法/模式/补码方式 */
    private static final String AES_CBC = "AES/CBC/PKCS5Padding";
    /* 加密模式之 CFB，算法/模式/补码方式 */
    private static final String AES_CFB = "AES/CFB/PKCS5Padding";
    @Getter
    private byte[] key;//128位,16byte,8字符
    private String mode=AES_ECB;
    private SecretKey spec;

    public AESUtil(byte[] key) {
        this.key = key;
        spec=getSecretKeySpec(this.key);
    }

    public void setKey(byte[] key) {
        this.key = key;
        spec=getSecretKeySpec(this.key);
    }
    private byte[] getBytes(String key){
        return (key!=null)?key.getBytes(StandardCharsets.UTF_8):null;
    }
    //获取一个 AES 密钥规范
    private SecretKeySpec getSecretKeySpec(byte[] key){
        return new SecretKeySpec(key,"AES");
    }
    public String encrypt(String clearText){
        if(clearText==null||"".equals(clearText)){
            return null;
        }
        try{
            // 创建AES加密器
            Cipher cipher=Cipher.getInstance(mode);
            cipher.init(Cipher.ENCRYPT_MODE,spec);
            // 加密字节数组
            byte[] encryptedBytes = cipher.doFinal(getBytes(clearText));
            // 将密文转换为 Base64 编码字符串
            return new Base64Encoder().encode(encryptedBytes);
            //return new String(encryptedBytes,StandardCharsets.UTF_8);
        } catch (Exception e){
            e.printStackTrace();
            return null;
        }
    }
    public String decrypt(String cipherText){
        if(cipherText==null||"".equals(cipherText)){
            return null;
        }
        // 将密文转换为16字节的字节数组
        byte[] textBytes = new Base64Encoder().decode(cipherText);
        //byte[] textBytes=getBytes(cipherText);
        try{
            // 创建AES解密器
            Cipher cipher=Cipher.getInstance(mode);
            cipher.init(Cipher.DECRYPT_MODE,spec);
            // 解密字节数组
            byte[] decryptedBytes = cipher.doFinal(textBytes);
            // 将明文转换为字符串
            return new String(decryptedBytes, StandardCharsets.UTF_8);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }

    }
}
