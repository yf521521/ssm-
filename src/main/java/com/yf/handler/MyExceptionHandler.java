package com.yf.handler;/*
 *
 *
 */

import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
@Component
public class MyExceptionHandler {

//    Exception是所有异常的父类，即处理所有异常
    @ExceptionHandler(Exception.class)
    public String ex(Exception ex){
        //异常被页面处理，故将异常输出
        ex.printStackTrace();
        return "error";
    }
}
