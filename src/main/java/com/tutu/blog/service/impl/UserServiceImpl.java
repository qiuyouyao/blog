package com.tutu.blog.service.impl;

import com.tutu.blog.bean.User;
import com.tutu.blog.dao.UserDao;
import com.tutu.blog.service.UserService;
import com.tutu.blog.util.Md5Util;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * @author tutu 2020/4/30 22:26
 */
@Service
public class UserServiceImpl implements UserService {

    @Resource
    UserDao userDao;

    @Override
    public User checkUser(String username, String password) {
        return userDao.findUserByUsernameAndPassword(username, Md5Util.byteToHexString(password.getBytes()));
    }
}
