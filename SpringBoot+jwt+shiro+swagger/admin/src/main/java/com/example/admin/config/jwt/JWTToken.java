package com.example.admin.config.jwt;

import org.apache.shiro.authc.AuthenticationToken;

/**
 * @author 贲玉柱
 * @program workspace
 * @description 自定义身份认证令牌类
 * @create 2023/3/21 16:12
 **/
public class JWTToken implements AuthenticationToken {

    // 密钥
    private final String token;

    /**
     * 构造函数
     *
     * @param token 密钥
     * @return
     */
    public JWTToken(String token) {
        this.token = token;
    }

    /**
     * 获取身份信息
     *
     * @return
     */
    @Override
    public Object getPrincipal() {
        return token;
    }

    /**
     * 获取凭证信息
     *
     * @return
     */
    @Override
    public Object getCredentials() {
        return token;
    }
}
