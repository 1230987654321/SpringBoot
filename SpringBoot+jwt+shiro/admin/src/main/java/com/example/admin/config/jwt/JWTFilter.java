package com.example.admin.config.jwt;

import com.auth0.jwt.exceptions.AlgorithmMismatchException;
import com.auth0.jwt.exceptions.SignatureVerificationException;
import com.auth0.jwt.exceptions.TokenExpiredException;
import com.example.admin.SpringContext;
import com.example.admin.config.enums.ResponseCodeEnum;
import com.example.admin.config.exception.ServiceException;
import com.example.admin.config.redis.RedisTokenService;
import com.example.admin.entity.Admin;
import com.example.admin.service.AdminService;
import org.apache.shiro.web.filter.authc.BasicHttpAuthenticationFilter;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.HandlerExceptionResolver;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Objects;

/**
 * @program workspace
 * @description 过滤器
 * @author 贲玉柱
 * @create 2023/3/21 16:12
 **/
public class JWTFilter extends BasicHttpAuthenticationFilter {

    /**
     *
     */
    @Override
    protected boolean executeLogin(ServletRequest request, ServletResponse response) throws Exception {
        HttpServletRequest httpServletRequest = (HttpServletRequest) request;
        String authorization = httpServletRequest.getHeader("token");
        JWTToken token = new JWTToken(authorization);
        // 提交给realm进行登入
        getSubject(request, response).login(token);
        return true;
    }

    /**
     * Shiro 集成 JWT后,不知道为什么,无法抓取异常,只能通过handlerExceptionResolver来处理异常
     * 返回FALSE是因为,不在返回异常后停止的话,还会继续往下走
     */
    @Override
    protected boolean isAccessAllowed(ServletRequest request, ServletResponse response, Object mappedValue) {
        // 通过自定义的 SpringContext 的 getBean 方法获取 HandlerExceptionResolver 来处理异常,
        // 不能用@autowired与@Qualifier("handlerExceptionResolver")注解来handlerExceptionResolver 是会抛空指针的
        HandlerExceptionResolver handlerExceptionResolver = (HandlerExceptionResolver) SpringContext.getBean("handlerExceptionResolver");
        //通过 class 获得 bean
        AdminService adminService = SpringContext.getBean(AdminService.class);
        RedisTokenService redisTokenService = SpringContext.getBean(RedisTokenService.class);
        // 获取 TOKEN
        String token = ((HttpServletRequest) request).getHeader("token");
        // 判断 TOKEN 是否存在
        if (token == null) {
            handlerExceptionResolver.resolveException((HttpServletRequest) request, (HttpServletResponse) response, null, new ServiceException(  401,"TOKEN不存在") );
            return false;
        } else if( redisTokenService.isTokenExists(token) ){
            handlerExceptionResolver.resolveException((HttpServletRequest) request, (HttpServletResponse) response, null, new ServiceException(  401,"TOKEN已被废弃") );
            return false;
        }
        // 通过 TOKEN 获取用户名
        String username = JWTUtil.getUsername(token);
        if(Objects.equals(username, "false")){
            handlerExceptionResolver.resolveException((HttpServletRequest) request, (HttpServletResponse) response, null, new ServiceException(  401,"TOKEN不合法" ) );
            return false;
        }
        // 验证 TOKEN 是否正确
        try {
            JWTUtil.verify(token, username);
        } catch (SignatureVerificationException e) {
            handlerExceptionResolver.resolveException((HttpServletRequest) request, (HttpServletResponse) response, null, new ServiceException( 401,"签名错误" ) );
            return false;
        } catch (TokenExpiredException e) {
            // 获取请求 TOKEN 的过期时间
            Long expirationTime = JWTUtil.getExpiration(token);
            // 获取当前的时间
            Long newTime = System.currentTimeMillis();
            // 如果相差小于5MIN,生成新的TOKEN,否则返回TOKEN过期
            if((newTime - expirationTime)/60000 < 5){
                //生成新的token
                String newToken = JWTUtil.createJWT(username);
                //将新token添加到响应头中
                ((HttpServletResponse) response).setHeader("token", newToken);
                // 生成新的 TOKEN 后,作废之前的TOKEN,防止重复生成 TOKEN
                redisTokenService.addToken(token);
            }else{
                handlerExceptionResolver.resolveException((HttpServletRequest) request, (HttpServletResponse) response, null, new ServiceException( 401,"TOKEN过期" ) );
                return false;
            }
        } catch (AlgorithmMismatchException e) {
            handlerExceptionResolver.resolveException((HttpServletRequest) request, (HttpServletResponse) response, null, new ServiceException( 401,"TOKEN算法不一致" ) );
            return false;
        } catch (Exception e){
            handlerExceptionResolver.resolveException((HttpServletRequest) request, (HttpServletResponse) response, null, new ServiceException( 401,"TOKEN未知错误") );
            return false;
        }
        // 查询用户信息,并抛出异常
        Admin admin = adminService.getUsername(username);
        if (admin == null) {
            handlerExceptionResolver.resolveException((HttpServletRequest) request, (HttpServletResponse) response, null, new ServiceException( ResponseCodeEnum.NOT_EXIST ) );
            return false;
        }else if (!admin.getStatus().equals(1)) {
            handlerExceptionResolver.resolveException((HttpServletRequest) request, (HttpServletResponse) response, null, new ServiceException( ResponseCodeEnum.ACCOUNT_DISABLED ) );
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
     * 对跨域提供支持
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
