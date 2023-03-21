package com.example.admin.config.jwt;

import org.apache.shiro.authc.AuthenticationToken;

/**
 * @program workspace
 * @description 自定义身份认证令牌类
 * @author 贲玉柱
 * @create 2023/3/21 16:12
 **/
public class JWTToken implements AuthenticationToken {

    // 密钥
    private final String token;

    public JWTToken(String token) {
        this.token = token;
    }

    @Override
    public Object getPrincipal() {
        return token;
    }

    @Override
    public Object getCredentials() {
        return token;
    }
}
