package com.lzh.service;


import com.lzh.bean.User;

/**
 * @author lizhenhao
 */
public interface UserService {

    Integer checkUsername(String username);

    Integer register(User user);

    User login(String username);


    //根据用户名查询用户对象
    User findUserByUsername(String username);
}
