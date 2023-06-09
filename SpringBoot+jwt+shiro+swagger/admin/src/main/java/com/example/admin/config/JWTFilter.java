package com.example.admin.config;

import com.auth0.jwt.exceptions.AlgorithmMismatchException;
import com.auth0.jwt.exceptions.SignatureVerificationException;
import com.auth0.jwt.exceptions.TokenExpiredException;
import com.example.admin.SpringContext;
import com.example.admin.common.JWTToken;
import com.example.admin.common.ResponseCodeEnum;
import com.example.admin.common.ServiceException;
import com.example.admin.entity.Admin;
import com.example.admin.service.AdminService;
import com.example.admin.util.JWTUtil;
import com.example.admin.util.RedisUtil;
import org.apache.shiro.web.filter.authc.BasicHttpAuthenticationFilter;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.HandlerExceptionResolver;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Objects;

/**
 * @author 贲玉柱
 * @program workspace
 * @description 过滤器
 * @create 2023/3/21 16:12
 **/
public class JWTFilter extends BasicHttpAuthenticationFilter {

    private final Environment environment;
    public JWTFilter(Environment environment) {
        this.environment = environment;
    }

    /**
     * 此方法用于对用户进行身份验证。
     *
     * @param request  ServletRequest
     * @param response ServletResponse
     * @return boolean
     * @throws Exception Exception
     */
    @Override
    protected boolean executeLogin(ServletRequest request, ServletResponse response) throws Exception {
        HttpServletRequest httpServletRequest = (HttpServletRequest) request;
        String authorization = httpServletRequest.getHeader("token");
        JWTToken token = new JWTToken(authorization);
        getSubject(request, response).login(token);
        return true;
    }

    /**
     * 此函数检查是否允许用户访问请求的资源。
     *
     * @param request     ServletRequest
     * @param response    ServletResponse
     * @param mappedValue Object
     * @return boolean
     */
    @Override
    protected boolean isAccessAllowed(ServletRequest request, ServletResponse response, Object mappedValue) {
        // 通过自定义的 SpringContext 的 getBean 方法获取 HandlerExceptionResolver 来处理异常
        // 不能用@autowired 与 @Qualifier("HandlerExceptionResolver") 注解 HandlerExceptionResolver是会抛出空指针异常的
        HandlerExceptionResolver handlerExceptionResolver = (HandlerExceptionResolver) SpringContext.getBean("handlerExceptionResolver");
        // 通过自定义的 SpringContext 的 getBean 方法获取 AdminService
        AdminService adminService = SpringContext.getBean(AdminService.class);
        //通过 class 获得自定义的 redisUtil
        RedisUtil redisUtil = SpringContext.getBean(RedisUtil.class);
        // 通过 environment 获取redis配置;
        String REDIS_KEY_ADMIN_PREFIX = environment.getProperty("spring.redis.admin-prefix");
        // 获取 TOKEN
        String token = ((HttpServletRequest) request).getHeader("token");
        // 判断 TOKEN 是否存在
        if (token == null || token.equals("")) {
            handlerExceptionResolver.resolveException((HttpServletRequest) request, (HttpServletResponse) response, null, new ServiceException(401, "TOKEN不存在"));
            return false;
        } else if (redisUtil.isTokenExists(token)) {
            handlerExceptionResolver.resolveException((HttpServletRequest) request, (HttpServletResponse) response, null, new ServiceException(401, "TOKEN已被废弃"));
            return false;
        }
        // 通过 TOKEN 获取用户名
        String username = JWTUtil.getUsername(token);
        if (Objects.equals(username, "false")) {
            handlerExceptionResolver.resolveException((HttpServletRequest) request, (HttpServletResponse) response, null, new ServiceException(401, "TOKEN不合法"));
            return false;
        }
        // 验证 TOKEN 是否正确
        try {
            JWTUtil.verify(token, username);
        } catch (SignatureVerificationException e) {
            handlerExceptionResolver.resolveException((HttpServletRequest) request, (HttpServletResponse) response, null, new ServiceException(401, "签名错误"));
            return false;
        } catch (TokenExpiredException e) {
            // 获取请求 TOKEN 的过期时间
            Long expirationTime = JWTUtil.getExpiration(token);
            // 获取当前的时间
            Long newTime = System.currentTimeMillis();
            // 如果相差小于5MIN,生成新的TOKEN,否则返回TOKEN过期
            if ((newTime - expirationTime) / 60000 < 5) {
                //生成新的token
                String newToken = JWTUtil.createJWT(username);
                //将新token添加到响应头中
                ((HttpServletResponse) response).setHeader("token", newToken);
                // 生成新的 TOKEN 后,作废之前的TOKEN,防止重复生成 TOKEN
                redisUtil.addToken(token);
            } else {
                handlerExceptionResolver.resolveException((HttpServletRequest) request, (HttpServletResponse) response, null, new ServiceException(401, "TOKEN过期"));
                return false;
            }
        } catch (AlgorithmMismatchException e) {
            handlerExceptionResolver.resolveException((HttpServletRequest) request, (HttpServletResponse) response, null, new ServiceException(401, "TOKEN算法不一致"));
            return false;
        } catch (Exception e) {
            handlerExceptionResolver.resolveException((HttpServletRequest) request, (HttpServletResponse) response, null, new ServiceException(401, "TOKEN未知错误"));
            return false;
        }
        // 查询用户信息,并抛出异常
        Admin admin = redisUtil.getData(REDIS_KEY_ADMIN_PREFIX + username, Admin.class);
        if (admin == null) {
            // 如果 Redis 中不存在该用户信息，则从数据库中获取并存储到 Redis 中
            admin = adminService.getAdminByUsername(username);
            if (admin == null) {
                handlerExceptionResolver.resolveException((HttpServletRequest) request, (HttpServletResponse) response, null, new ServiceException(401, ResponseCodeEnum.NOT_EXIST.getMessage()));
                return false;
            }
            // 将查询用户信息储存 Redis 中
            redisUtil.addData(REDIS_KEY_ADMIN_PREFIX + username, admin);
        }
        if (!admin.getStatus().equals(1)) {
            handlerExceptionResolver.resolveException((HttpServletRequest) request, (HttpServletResponse) response, null, new ServiceException(401, ResponseCodeEnum.ACCOUNT_DISABLED.getMessage()));
            return false;
        }
        //进入 executeLogin 方法执行登入
        try {
            executeLogin(request, response);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return true;
    }


    /**
     * 解决跨域问题
     *
     * @param request  ServletRequest
     * @param response ServletResponse
     * @return boolean
     * @throws Exception Exception
     */
    @Override
    protected boolean preHandle(ServletRequest request, ServletResponse response) throws Exception {
        HttpServletRequest httpServletRequest = (HttpServletRequest) request;
        HttpServletResponse httpServletResponse = (HttpServletResponse) response;
        httpServletResponse.setHeader("Access-control-Allow-Origin", httpServletRequest.getHeader("Origin"));
        httpServletResponse.setHeader("Access-Control-Allow-Methods", "GET,POST,OPTIONS,PUT,DELETE");
        httpServletResponse.setHeader("Access-Control-Allow-Headers", httpServletRequest.getHeader("Access-Control-Request-Headers"));
        // 跨域时会首先发送一个option请求，这里我们给option请求直接返回正常状态
        if (httpServletRequest.getMethod().equals(RequestMethod.OPTIONS.name())) {
            httpServletResponse.setStatus(HttpStatus.OK.value());
            return false;
        }
        return super.preHandle(request, response);
    }
}
