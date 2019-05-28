package com.lzh.service.impl;

import com.lzh.SsmTest;
import com.lzh.bean.User;
import com.lzh.dao.UserDao;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * @author lizhenhao
 */
public class UserServiceImplTest extends SsmTest {

    @Autowired
    private UserDao userDao;

    @Test
    public void checkUsername() {

        Integer count = userDao.findCountByUsername("张三");

        System.out.println(count);


    }

    @Test
    public void login() {

        User user = userDao.findUserByUsername("张三");

        System.out.println(user);
    }
}
