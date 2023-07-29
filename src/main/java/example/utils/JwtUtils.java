package example.utils;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;

import java.security.Key;
import java.util.Date;

public class JwtUtils {
    private static final Key SECRET_KEY = Keys.secretKeyFor(SignatureAlgorithm.HS256); // 生成256位密钥

    public static String generateJwt(String subject, long expirationMillis) {
        // 设置过期时间
        Date expirationDate = new Date(System.currentTimeMillis() + expirationMillis);

        // 生成JWT
        String jwt = Jwts.builder()
                .setSubject(subject)
                .setExpiration(expirationDate)
                .signWith(SECRET_KEY)
                .compact();

        return jwt;
    }

    public static String getUsernameFromJwt(String jwt) {
        try {
            // 解析JWT获取Claims
            Jws<Claims> claims = Jwts.parserBuilder()
                    .setSigningKey(SECRET_KEY)
                    .build()
                    .parseClaimsJws(jwt);

            // 从Claims中获取username
            String username = claims.getBody().getSubject();

            return username;
        } catch (Exception e) {
            // 解析失败，返回null
            return null;
        }
    }

    public static boolean validateJwt(String jwt) {
        try {
            // 验证JWT签名和有效性
            Jws<Claims> claims = Jwts.parserBuilder()
                    .setSigningKey(SECRET_KEY)
                    .build()
                    .parseClaimsJws(jwt);

            // 如果没有抛出异常，则JWT是有效的
            return true;
        } catch (Exception e) {
            // 验证失败，JWT无效
            return false;
        }
    }
}
