package com.yf.service;

import com.yf.pojo.User;

public interface UserService {

    Integer checkUsername(String username);

    Integer register(User user);

    User login(String username,String password);
}
