package com.lzh.dao;

import com.lzh.bean.User;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @author lizhenhao
 */

public interface UserDao {


    //根据用户名查询
    Integer findCountByUsername(@Param("username") String username);

    //注册用户
    Integer save(User user);

    //根据用户名查询用户对象(登录)
    User findUserByUsername(@Param("username") String username);
}
