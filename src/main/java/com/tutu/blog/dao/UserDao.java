package com.tutu.blog.dao;

import com.tutu.blog.bean.User;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * @author tutu 2020/4/30 22:26
 */
public interface UserDao extends JpaRepository<User,Long> {
    /**
     * 登录校验
     * @param username 用户名
     * @param password 密码
     * @return 用户信息
     */
    User findUserByUsernameAndPassword(String username, String password);
}
