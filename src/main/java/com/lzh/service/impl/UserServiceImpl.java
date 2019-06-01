package com.lzh.service.impl;

import com.lzh.bean.User;
import com.lzh.dao.UserDao;
import com.lzh.service.UserService;
import org.apache.shiro.crypto.hash.Md5Hash;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.UUID;


/**
 * @author lizhenhao
 */

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserDao userDao;


    @Override
    public Integer checkUsername(String username) {

        Integer count = userDao.findCountByUsername(username);

        return  count;

    }

    /*
    * 注册账户
    * */
    @Override
    public Integer register(User user) {

        //1.
        String salt = UUID.randomUUID().toString();

        Md5Hash newPassword = new Md5Hash(user.getPassword(), salt, 1024);

        user.setPassword(newPassword.toString());
        user.setSalt(salt);

        return userDao.save(user);
    }



    @Override
    public User login(String username) {

        return userDao.findUserByUsername(username);

    }

    @Override
    public User findUserByUsername(String username) {

        return userDao.findUserByUsername(username);
    }
}
