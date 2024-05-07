package com.example.backend_badminton.utils;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.auth0.jwt.interfaces.JWTVerifier;

import java.util.Date;

public class JwtUtil {

    private static final String SECRET = "Cyril"; // 强密钥
    private static final long EXPIRE_TIME = 24 * 60 * 60 * 1000; // 24小时

    public static String createToken(Integer userId, String identity, String phone) {
        Date date = new Date(System.currentTimeMillis() + EXPIRE_TIME);
        Algorithm algorithm = Algorithm.HMAC256(SECRET);

        return JWT.create()
                .withClaim("userId", userId)
                .withClaim("identity", identity)
                .withClaim("phone", phone)
                .withExpiresAt(date)
                .sign(algorithm);
    }

    public static DecodedJWT verifyToken(String token) {
        try {
            Algorithm algorithm = Algorithm.HMAC256(SECRET); // 使用与创建 token 相同的秘密
            JWTVerifier verifier = JWT.require(algorithm)
                    .build(); // 重新构建用于验证的验证器

            DecodedJWT jwt = verifier.verify(token); // 验证 token
//            System.out.println("Token is valid.");
//            System.out.println("User ID: " + jwt.getClaim("userId").asInt());
//            System.out.println("Identity: " + jwt.getClaim("identity").asString());
//            System.out.println("Phone: " + jwt.getClaim("phone").asString());

            return jwt;
        } catch (JWTVerificationException exception) {
            // 无效的签名/Token已过期等
            System.out.println("Invalid token: " + exception.getMessage());
            return null; // 如果验证失败，返回 null
        }
    }
}
