package example.utils;

import java.util.HashMap;
import java.util.Map;

public class MemoryCodeStorage {
    private static final long EXPIRATION_TIME = 5 * 60 * 1000; // 5分钟的有效期

    private static Map<String, VerificationCode> codeMap = new HashMap<>();

    public static void saveCode(String email, String code) {
        long expiration = System.currentTimeMillis() + EXPIRATION_TIME;
        VerificationCode verificationCode = new VerificationCode(code, expiration);
        codeMap.put(email, verificationCode);
    }

    public static boolean verifyCode(String email, String code) {
        VerificationCode verificationCode = codeMap.get(email);
        if (verificationCode != null && verificationCode.getCode().equals(code)) {
            if (System.currentTimeMillis() <= verificationCode.getExpiration()) {
                // 验证码匹配且未过期
                codeMap.remove(email); // 使用后移除验证码
                return true;
            } else {
                // 验证码过期
                codeMap.remove(email); // 移除过期的验证码
            }
        }
        return false;
    }

    private static class VerificationCode {
        private String code;
        private long expiration;

        public VerificationCode(String code, long expiration) {
            this.code = code;
            this.expiration = expiration;
        }

        public String getCode() {
            return code;
        }

        public long getExpiration() {
            return expiration;
        }
    }
}
