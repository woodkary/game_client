package com.kary.hahaha3.utils;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.nio.charset.StandardCharsets;
import java.util.Properties;

/**
 * @author:123
 */
public class MailUtil {
    @Getter
    @Setter
    private static String authorCode = null;
    private static String email="834479572@qq.com";
    @Getter
    @Setter
    private static String toEmail;
    private static String qqMail="smtp.qq.com";

    public static void setAuthorCode(String authorCode) {
        MailUtil.authorCode = authorCode;
    }
    public static String getRandom6Digit(){
        byte[] bytes=new byte[6];
        for (int i = 0; i < 6; i++) {
            bytes[i]= (byte) ('0'+Math.random()*10);
        }
        return new String(bytes, StandardCharsets.UTF_8);
    }
    public static void sendMail(String toAddr,String text,String title) throws MessagingException {
            final Properties props=new Properties();
            props.put("mail.smtp.auth", "true");
            //注意发送邮件的方法中，发送给谁的，发送给对应的app，
            //要改成对应的app。扣扣的改成qq的，网易的要改成网易的。
            //props.put("mail.smtp.host", "smtp.qq.com");
            props.put("mail.smtp.host", qqMail);
            //QQ邮箱账号
            props.put("mail.user",email);
            //授权码
            props.put("mail.password",authorCode);
            //构建授权信息，用于身份验证
            Authenticator authenticator = new Authenticator() {
                @Override
                protected PasswordAuthentication getPasswordAuthentication() {
                    // 用户名、密码
                    String userName = props.getProperty("mail.user");
                    String password = props.getProperty("mail.password");
                    return new PasswordAuthentication(userName, password);
                }
            };
            //使用环境属性和授权信息，创健邮件会话
            Session mailSession=Session.getInstance(props,authenticator);
            //创健邮件消息
            MimeMessage message=new MimeMessage(mailSession);
            //发件人
            InternetAddress fromAddress=new InternetAddress(email);
            message.setFrom(fromAddress);
            //收件人
            InternetAddress toAddress=new InternetAddress(toAddr);
            message.setRecipient(Message.RecipientType.TO,toAddress);
            //标题
            message.setSubject(title);
            //内容
            message.setContent(text,"text/plain;charset=UTF-8");
            //发送
        System.out.println(authorCode);
        Transport.send(message);
    }
    public static boolean legalQQMail(String email){
        return email!=null&&email.indexOf("@qq.com")!=-1;
    }
}
