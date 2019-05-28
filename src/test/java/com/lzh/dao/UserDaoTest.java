package com.lzh.dao;

import com.lzh.SsmTest;
import com.lzh.bean.User;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author lizhenhao
 */
public class UserDaoTest extends SsmTest {

    @Autowired
    private UserDao userDao;

    @Test
    public void findCountByUsername() {

        Integer count = userDao.findCountByUsername("张三");
        System.out.println(count);

    }

    @Test
    public void save(){

    }

    @Test
    public void findUserByUsername(){

        User user = userDao.findUserByUsername("张三");

        System.out.println(user);

    }
}
