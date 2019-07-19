package com.yf.handler;/*
 *
 *
 */

import org.springframework.web.servlet.HandlerExceptionResolver;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.Map;

public class SsmExceptionHandler implements HandlerExceptionResolver {
    @Override
    public ModelAndView resolveException(
                                HttpServletRequest request,
                                HttpServletResponse response,
                                Object handler,
                                Exception ex) {

        Map<String,Object> map = new HashMap<>();
        map.put("ex",ex );

        if (ex instanceof ArithmeticException){
            //出现了算数异常
            return new ModelAndView("arithmeticView",map);
        }

        return new ModelAndView("500", map);
    }
}
