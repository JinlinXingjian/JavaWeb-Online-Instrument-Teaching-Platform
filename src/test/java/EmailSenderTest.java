import example.utils.EmailSender;

public class EmailSenderTest {
    public static void main(String[] args) {
        String recipientEmail = "2647600900@qq.com";
        String subject = "测试邮件";
        String content = "这是一封测试邮件，请忽略。";

        EmailSender.sendEmail(recipientEmail, subject, content);
    }
}
