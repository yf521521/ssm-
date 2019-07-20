package com.yf.controller;/*
 *
 *
 */

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/test")
public class TestController {

    @PutMapping("/update")
    public String update(String name,String age){
        System.out.println(name + " " +age);
        System.out.println("hahahahahahah");
        return null;
    }
}
