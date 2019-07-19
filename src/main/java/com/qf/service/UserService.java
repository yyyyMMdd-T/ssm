package com.qf.service;

import com.qf.pojo.User;

public interface UserService {
    //根据用户查询是否可用
    Integer checkUsername(String username);

    //注册功能
    Integer register(User user);

    //执行登录
    User login(String username, String password);
}
