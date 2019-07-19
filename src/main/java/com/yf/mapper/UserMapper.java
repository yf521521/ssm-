package com.yf.mapper;


import com.yf.pojo.User;
import org.apache.ibatis.annotations.Param;

public interface UserMapper {

    //根据用户名查询数据条数

    Integer findCountByUsername(@Param("username") String username);

    //2. 添加用户信息
    Integer save(User user);


    //3 根据用户名查询用户信息
    User findByUserName(@Param("username") String username);


}
