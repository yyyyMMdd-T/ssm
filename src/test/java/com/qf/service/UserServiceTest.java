package com.qf.service;

import com.qf.pojo.User;
import org.apache.shiro.crypto.hash.Md5Hash;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

public class UserServiceTest {
    @Autowired
    private UserService userService;
    @Test
    public void login() {
        User user = userService.login("admin", "admin");
        System.out.println(new Md5Hash("admin",null,1024).toString());
        System.err.println(user);
    }
}