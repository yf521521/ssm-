package com.yf.service.impl;/*
 *
 *
 */

import com.alibaba.druid.util.StringUtils;
import com.yf.mapper.UserMapper;
import com.yf.pojo.User;
import com.yf.service.UserService;
import org.apache.shiro.crypto.hash.Md5Hash;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserMapper userMapper;

    @Override
    public Integer checkUsername(String username) {
        //健壮型代码
        if (!StringUtils.isEmpty(username)){
            username = username.trim();
        }
        Integer count = userMapper.findCountByUsername(username);
        return count;
    }

    @Override
    public Integer register(User user) {
        //1. 对密码加密.
        String newPwd = new Md5Hash(user.getPassword(),null,1024).toString();
        user.setPassword(newPwd);
        //2. 调用mapper保存数据.
        Integer count = userMapper.save(user);
        //3. 返回信息
        return count;
    }

    @Override
    public User login(String username, String password) {
        //用username查询用户信息
        User user = userMapper.findByUserName(username);

        //根据用户信息对比密码是否一致
        if (user != null){
            String newPwd = new Md5Hash(password,null,1024).toString();
            if (user.getPassword().equals(newPwd)){
                return user;
            }
        }
        //其他情况统一返回null
        return null;
    }
}













