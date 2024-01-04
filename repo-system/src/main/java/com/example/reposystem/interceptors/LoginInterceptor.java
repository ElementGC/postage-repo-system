package com.example.reposystem.interceptors;

import com.example.reposystem.utils.JwtUtil;
import com.example.reposystem.utils.ThreadLocalUtil;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import java.util.Map;

//拦截器，负责验证网页的请求头是否符合规范（token是否合法且生效），无效则返回401
@Component
public class LoginInterceptor implements HandlerInterceptor {

    //进入前先检查请求头获取token并解析其中信息
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
            throws Exception {
        //获取令牌
        String token = request.getHeader("Authorization");
        //验证token
        try {
            Map<String,Object> claims = JwtUtil.parseToken(token);
            //通过ThreadLocal存储登录用户信息(是以Map集合的形式存储的，后面取用的时候注意类型转换）
            ThreadLocalUtil.set(claims);
            //验证通过，放行即可
            return true;
        } catch (Exception e) {
            //验证不通过，报错401
            response.setStatus(401);
            return false;

        }
    }

    //响应完成后销毁
    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
        //清空ThreadLocal中的数据，防止内存泄露
        ThreadLocalUtil.remove();

    }
}
