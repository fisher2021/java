package com.upd.common.util;

/**
 * Created by zm on 15-12-11.
 */

import java.io.IOException;
import java.util.Date;
import java.util.Properties;
import javax.mail.Authenticator;
import javax.mail.BodyPart;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Multipart;
import javax.mail.Transport;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;
public class MailUtil2 {
    public static void  main(String args[]){
        try {
            send_email("sss");
        }catch (Exception e) {
            e.printStackTrace();
        }
    }
    public static void send_email(String message) throws IOException, AddressException, MessagingException{
        String to[] = {"493591834@qq.com","1554788718@qq.com"};
        String subject = message;
        String content = message;
        Properties properties = new Properties();
        properties.put("mail.smtp.host", "mail.Groupbigdata.com");
        properties.put("mail.smtp.port", "25");
        properties.put("mail.smtp.auth", "true");
        Authenticator authenticator = new Email_Authenticator("zhangshoubing@Groupbigdata.com", "zhangshoubing@123");
        javax.mail.Session sendMailSession = javax.mail.Session.getDefaultInstance(properties, authenticator);
        MimeMessage mailMessage = new MimeMessage(sendMailSession);

        for(String tomail:to){
            mailMessage.setFrom(new InternetAddress("zhangshoubing@Groupbigdata.com"));
            // Message.RecipientType.TO属性表示接收者的类型为TO
            mailMessage.setRecipient(Message.RecipientType.TO, new InternetAddress(tomail));
            mailMessage.setSubject(subject, "UTF-8");
            mailMessage.setSentDate(new Date());
            // MiniMultipart类是一个容器类，包含MimeBodyPart类型的对象
            Multipart mainPart = new MimeMultipart();
            // 创建一个包含HTML内容的MimeBodyPart
            BodyPart html = new MimeBodyPart();
            html.setContent(content.trim(), "text/html; charset=utf-8");
            mainPart.addBodyPart(html);
            mailMessage.setContent(mainPart);
            Transport.send(mailMessage);
            System.out.println("发送成功");
        }

    }
}

