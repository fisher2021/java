package com.upd.common.util;

/**
 * Created by zm on 15-12-11.
 */

import com.upd.common.util.email.MessageTemplateImpl;

import java.util.Date;
import java.util.Properties;

import javax.mail.Authenticator;
import javax.mail.Message.RecipientType;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

public class EmailUtils {

    private static final String FROM = "18200389020@163.com";

    /**
     * 注册成功后,向用户发送帐户激活链接的邮件
     *
     * @param email
     *     未激活的用户
     */
    public static void sendAccountActivateEmail(String  email, MessageTemplateImpl messageTemplate) {
        Session session = getSession();
        MimeMessage message = new MimeMessage(session);
        try {
            message.setSubject(messageTemplate.getRegisterSubject());
            message.setSentDate(new Date());
            message.setFrom(new InternetAddress(FROM));
            message.setRecipient(RecipientType.TO,
                    new InternetAddress(email));
            message.setContent("content", "text/html;charset=utf-8");
            // 发送邮件
            Transport.send(message);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    //发送用户激活邮件
    public static void sendEmail(String  email, String content,String subject) {
        Session session = getSession();
        MimeMessage message = new MimeMessage(session);
        try {
            message.setSubject(subject);
            message.setSentDate(new Date());
            message.setFrom(new InternetAddress(FROM));
            message.setRecipient(RecipientType.TO,
                    new InternetAddress(email));
            message.setContent(content, "text/html;charset=utf-8");
            // 发送邮件
            Transport.send(message);
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }
    
    
    
    public static Session getSession() {
        Properties props = new Properties();
        props.setProperty("mail.transport.protocol", "smtp");
        props.setProperty("mail.smtp.host", "smtp.163.com");
        props.setProperty("mail.smtp.port", "25");
        props.setProperty("mail.smtp.auth", "true");
        Session session = Session.getInstance(props, new Authenticator() {
            @Override
            protected PasswordAuthentication getPasswordAuthentication() {
                String password = null;
				/*InputStream is = EmailUtils.class
						.getResourceAsStream("password.dat");
				byte[] b = new byte[1024];
				try {
					int len = is.read(b);
					password = new String(b, 0, len);
				} catch (IOException e) {
					e.printStackTrace();
				}*/
                return new PasswordAuthentication(FROM, "gaoxin6114689");
            }

        });
        return session;
    }
}