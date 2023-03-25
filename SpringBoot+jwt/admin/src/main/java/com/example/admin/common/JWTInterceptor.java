package com.example.admin.common;

import com.example.admin.common.JWTUtil;
import com.example.admin.common.ServiceException;
import org.apache.commons.lang3.StringUtils;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class JWTInterceptor implements HandlerInterceptor {

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) {
        String token = request.getHeader("token");
        if (StringUtils.isEmpty(token)) {
            throw new ServiceException(401, "token不能为空");
        }
        String username = JWTUtil.getUsername(token);
        JWTUtil.verify(token, username);
        return true;
    }
}
