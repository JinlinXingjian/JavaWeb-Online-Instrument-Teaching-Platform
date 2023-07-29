package example.utils;

import java.util.Random;

public class VerificationCodeGenerator {
    public static String generateNumericCode(int length) {
        StringBuilder code = new StringBuilder();
        Random random = new Random();

        for (int i = 0; i < length; i++) {
            int digit = random.nextInt(10); // 生成 0-9 之间的随机数字
            code.append(digit);
        }

        return code.toString();
    }
}
