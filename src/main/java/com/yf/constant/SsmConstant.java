package com.yf.constant;

public interface SsmConstant {
    /*放在session域中的验证码*/
    String CODE = "code";

    /*重定向*/
    String  REDIRECT= "redirect:";

    /*跳转到注册页面的路径*/
    String REGISTER_UI = "/user/register-ui";

    /*跳转到登录页面的路径*/
    String lOGIN_UI = "/user/login-ui";


    /*放在session域中的用户信息*/
    String USER = "user";

    /** 跳转到添加页面 */
    String ITEM_ADD_UI = "/item/add-ui";

    /* 跳转到修改页面*/
    String ITEM_UPDATE = "/item/item_update";
}

