package com.yf.controller;/*
 *
 *
 */

import com.alibaba.druid.util.StringUtils;
import com.yf.pojo.User;
import com.yf.service.UserService;
import com.yf.util.SendSMSUtil;
import com.yf.vo.ResultVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import java.util.HashMap;
import java.util.Map;

import static com.yf.constant.SsmConstant.*;

@Controller
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserService userService;

    @Autowired
    private SendSMSUtil sendSMSUtil;

    //跳转到注册页面
    @GetMapping("/register-ui")
    public String registerUI(){

        //转发到登陆页面
        return "/user/register";
    }

    //接受json数据时，不能使用传统的方式接收
    @PostMapping("/check-username")
    //@ResponseBody不走视图解析器，直接响应，并自动序列化为json
    @ResponseBody
    public Map<String, Object> checkusername(@RequestBody User user){
        //json数据需要反序列化成pojo对象或Map集合
        //1 调用service，判断用户名是否可用
        Integer count = userService.checkUsername(user.getUsername());

//        return new ResultVO(0,"成功",count);
        //2 封装响应数据的Map
        Map<String,Object> res = new HashMap<>();
        res.put("code",0 );
        res.put("msg","成功" );
        res.put("data",count );
        //3 返回结果
        return res;
    }

    //发送短信
    //produces设置请求头编码
    @PostMapping(value = "/send-sms",produces = "text/html;charset=utf-8")
    @ResponseBody
    public String sendSMS(@RequestParam String phone, HttpSession session){
        //1 调用工具发送短信
        String result = sendSMSUtil.sendSMS(session, phone);
        //2 将result响应给ajax引擎
        return result;
    }

    //执行注册
    @PostMapping("/register")
    public String register(@Valid User user, BindingResult bindingResult, String registerCode, HttpSession session, RedirectAttributes RedirectAttributes){
        //1  校验验证码
        if (!StringUtils.isEmpty(registerCode)){
//            String code =  (int)session.getAttribute(CODE)+"";
            if (!registerCode.equals("123")){
                //验证码错误
                RedirectAttributes.addAttribute("registerInfo","验证码错误" );
                return REDIRECT + REGISTER_UI;
            }
        }
        //2 校验参数是否合法
        if (bindingResult.hasErrors() || StringUtils.isEmpty(registerCode)){
            //参数不合法
            String msg = bindingResult.getFieldError().getDefaultMessage();
            RedirectAttributes.addAttribute("registerInfo",msg );
            return REDIRECT + REGISTER_UI;
        }
        //3 调用service
        Integer count = userService.register(user);
        if (count == 1){
            //注册成功
            return  REDIRECT + lOGIN_UI;
        }else {
            //注册失败
            RedirectAttributes.addAttribute("registerInfo", "服务器有问题请联系管理员");
            return REDIRECT + REGISTER_UI;
        }
    }

    //跳转登录
    @GetMapping("/login-ui")
    public String login(){
        return "/user/login";
    }


    //执行登录
    @PostMapping("/login")
    @ResponseBody
    public ResultVO login(String username,String password,HttpSession session){
        //1 校验参数是否合法
        if (StringUtils.isEmpty(username)  || StringUtils.isEmpty(password)){
            //参数不为空
            return new ResultVO(1,"用户名或密码为必填项",null);
        }
        //2 调用service执行登录
        User user = userService.login(username,password);
        //3 根据service返回结果判断是否登录成功
        if (user != null){
            //4 如果成功，用户信息放到session域中
            session.setAttribute(USER,user );
            System.out.println("hahahah");
            return new ResultVO(0,"成功",null);
        }else{
            //如果失败，响应错误哦信息给ajax引擎
            return new ResultVO(2,"用户名或密码失败",null);
        }

    }
}


























