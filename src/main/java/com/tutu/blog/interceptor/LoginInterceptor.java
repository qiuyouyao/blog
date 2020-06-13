package com.tutu.blog.interceptor;

import org.springframework.stereotype.Component;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author tutu 2020/4/30 23:33
 */
@Component
public class LoginInterceptor extends HandlerInterceptorAdapter {
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        String uri = request.getRequestURI();
        if(uri.startsWith("/admin") && request.getSession().getAttribute("user") == null){
            request.getSession().setAttribute("message", "请先登录...");
            response.sendRedirect(request.getContextPath() +"/admin/login");
            return false;
        }
        return true;
    }
}
