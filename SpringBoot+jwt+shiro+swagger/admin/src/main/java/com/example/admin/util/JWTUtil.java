package com.example.admin.util;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTDecodeException;
import org.springframework.stereotype.Component;

import java.util.Date;

/**
 * @author 贲玉柱
 * @program workspace
 * @description 生成和解析 JSON Web Token（JWT）的工具类
 * @create 2023/3/21 16:13
 **/
@Component
public class JWTUtil {

    // 过期时间15分钟
    private static final long EXPIRE_TIME = 15 * 60 * 1000;

    // 设置秘钥
    private static final Algorithm key = Algorithm.HMAC256("1234567890".getBytes());

    /**
     * 生成签名
     *
     * @param username 用户名
     * @return 加密的token
     */
    public static String createJWT(String username) {
        Date date = new Date(System.currentTimeMillis() + EXPIRE_TIME);
        // 附带username信息
        return JWT.create().withClaim("username", username).withExpiresAt(date).sign(key);
    }


    /**
     * 获得token中的信息无需secret解密也能获得
     *
     * @param token 密钥
     * @return token中包含的用户名
     */
    public static String getUsername(String token) {
        try {
            return JWT.decode(token).getClaim("username").asString();
        } catch (JWTDecodeException e) {
            // Shiro 集成 JWT后,不知道为什么,无法抓取异常,只能返回FALSE,在过滤器中处理异常信息
            return "false";
        }
    }

    /**
     * 获得token中的过期时间
     *
     * @param token 密钥
     * @return token中的过期时间
     */
    public static Long getExpiration(String token) {
        return JWT.decode(token).getClaims().get("exp").asDate().getTime();
    }

    /**
     * 校验token是否正确
     *
     * @param token    密钥
     * @param username token中包含的用户名
     */
    public static void verify(String token, String username) {
        // Shiro 集成 JWT后,不知道为什么,无法抓取异常,只能返回FALSE,在过滤器中处理异常信息
        JWTVerifier verifier = JWT.require(key).withClaim("username", username).build();
        verifier.verify(token);
    }
}
