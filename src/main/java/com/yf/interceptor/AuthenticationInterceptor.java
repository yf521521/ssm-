package com.yf.interceptor;/*
 *
 *
 */

import com.yf.constant.SsmConstant;
import com.yf.pojo.User;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class AuthenticationInterceptor implements HandlerInterceptor {

    //前处理  执行controller方法之前
    //可以选择对请求进行拦截/放行
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        //1、获得session
        HttpSession session = request.getSession();
        //2、通过session获取用户信息
        User user = (User) session.getAttribute(SsmConstant.USER);
        //3、判断是否为空
        if (user == null){
            //3.1、为null --》 用户未登录 --》跳转到登录页面 --》 return false
            response.sendRedirect(request.getContextPath()+"/user/login-ui");
            return false;
        }else {
            //3.2、不为null  --》 return true
            return true;
        }
    }

    //执行完controller。返回ModelAndView之后执行
    //拦截器的post处理中，修改ModelAndView
    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {

    }

    //拦截器处理，响应数据之前
    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {

    }
}
