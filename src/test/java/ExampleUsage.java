
import example.utils.JwtUtils;

public class ExampleUsage {
    public static void main(String[] args) {
        // 生成JWT
        String jwt = JwtUtils.generateJwt("user123", 3600000L); // 有效期为1小时

//        String jwt="eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJhZG1pbjMiLCJleHAiOjE2ODY2NjUwNjV9.u_xuHe-r-UyVWRXnTmkXaLv3CPqYZTyXpjIchPuU4kA";
        // 输出JWT
        System.out.println("JWT: " + jwt);
        System.out.println("username:"+JwtUtils.getUsernameFromJwt(jwt));
        // 验证JWT
        boolean isValid = JwtUtils.validateJwt(jwt);
        System.out.println("JWT验证结果: " + isValid);
    }
}
