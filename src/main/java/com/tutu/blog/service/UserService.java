package com.tutu.blog.service;

import com.tutu.blog.bean.User;

/**
 * @author tutu 2020/4/30 22:24
 */
public interface UserService {

    /**
     * 登录
     * @param username 用户名
     * @param password 密码
     * @return 登录的用户
     */
    User checkUser(String username, String password);
}
