package com.lzh.service;


import com.lzh.bean.User;

/**
 * @author lizhenhao
 */
public interface UserService {

    Integer checkUsername(String username);

    Integer register(User user);

    User login(String username);
}
