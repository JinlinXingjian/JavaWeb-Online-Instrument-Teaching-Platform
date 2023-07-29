package example.utils;

import org.mindrot.jbcrypt.BCrypt;

public class PasswordUtil {
    // 生成盐并使用 BCrypt 哈希算法对密码进行加密
    public static String encryptPassword(String password) {
        String salt = BCrypt.gensalt(10);
        return BCrypt.hashpw(password, salt);
    }

    // 验证提供的密码是否与哈希密码匹配
    public static boolean verifyPassword(String password, String hashedPassword) {
        System.out.println("test1: "+hashedPassword);
        System.out.println("test1: "+encryptPassword(password));
        System.out.println("test1: "+hashedPassword.equals(encryptPassword(password)));
        return BCrypt.checkpw(password, hashedPassword);
    }
}
