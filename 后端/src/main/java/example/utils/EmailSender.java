package example.utils;

import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeUtility;
import java.io.UnsupportedEncodingException;
import java.util.Properties;

public class EmailSender {
    public static void sendEmail(String recipientEmail, String subject, String content) {
        // 配置邮件服务器的相关属性
        Properties properties = new Properties();
        properties.put("mail.smtp.host", "smtp.qq.com");
        properties.put("mail.smtp.port", "587");
        properties.put("mail.smtp.auth", "true");
        properties.put("mail.smtp.starttls.enable", "true");

        // 发件人邮箱地址和授权码
        final String senderEmail = "2647600900@qq.com";
        final String authorizationCode = "riygbsbanjydecij";

        // 创建邮件会话
        Session session = Session.getInstance(properties, new Authenticator() {
            @Override
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(senderEmail, authorizationCode);
            }
        });

        try {
            // 创建邮件消息
            MimeMessage message = new MimeMessage(session);
            message.setFrom(new InternetAddress(senderEmail));
            message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(recipientEmail));
            message.setSubject(MimeUtility.encodeText(subject, "UTF-8", "B"));
            message.setContent(content, "text/html;charset=UTF-8");

            // 发送邮件
            Transport.send(message);
            System.out.println("邮件已发送成功！");
        } catch (MessagingException e) {
            e.printStackTrace();
            System.out.println("邮件发送失败！");
        } catch (UnsupportedEncodingException e) {
            throw new RuntimeException(e);
        }
    }
}
