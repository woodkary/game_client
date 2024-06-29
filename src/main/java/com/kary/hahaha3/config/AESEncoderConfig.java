package com.kary.hahaha3.config;

import com.kary.hahaha3.utils.AESUtil;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;

/**
 * @author:123
 */
@Configuration
public class AESEncoderConfig {
    private static final String encodeRules = "fendo";
    @Bean("AESEncoder")
    public AESUtil getAES() throws NoSuchAlgorithmException {
        KeyGenerator keygen = KeyGenerator.getInstance("AES");
        //2.根据ecnodeRules规则初始化密钥生成器
        //生成一个128位的随机源,根据传入的字节数组
        SecureRandom random = SecureRandom.getInstance("SHA1PRNG");
        random.setSeed(encodeRules.getBytes());
        keygen.init(128, random);
        //3.产生原始对称密钥
        SecretKey original_key = keygen.generateKey();
        //4.获得原始对称密钥的字节数组
        byte[] key = original_key.getEncoded();
        return new AESUtil(key);
    }

}
