package com.upd.common.util;

/**
 * Created by zhangshao on 2015/8/7.
 */
    import com.upd.common.util.email.EmailSender;

    import java.io.IOException;
    import java.util.Date;
    import java.util.Properties;
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
    public class MailUtil {
        public static void  main(String args[]){
            try {
                send_email("791611281@qq.com","测试","确认邮件");
            }catch (Exception e) {
                e.printStackTrace();
            }
        }
        public static void send_email(String tomail,String subjects, String message) throws IOException, AddressException, MessagingException{
            System.out.println("sending email to:-->"+tomail);
            EmailSender authenticator=new EmailSender();
            String subject = subjects;
            String content = message;
            Properties properties = new Properties();
            properties.put("mail.smtp.host",authenticator.getHost());
            properties.put("mail.smtp.port", authenticator.getPort());
            properties.put("mail.smtp.auth", "true");

            javax.mail.Session sendMailSession = javax.mail.Session.getDefaultInstance(properties, authenticator);
            MimeMessage mailMessage = new MimeMessage(sendMailSession);

                mailMessage.setFrom(new InternetAddress(authenticator.getUserName()));
                // Message.RecipientType.TO属性表示接收者的类型为TO
                mailMessage.setRecipient(Message.RecipientType.TO, new InternetAddress(tomail));
                mailMessage.setSubject(subject, authenticator.getEncoding());
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

