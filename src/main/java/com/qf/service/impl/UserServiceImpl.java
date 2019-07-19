package com.qf.service.impl;

import com.mysql.jdbc.StringUtils;
import com.qf.mapper.UserMapper;
import com.qf.pojo.User;
import com.qf.service.UserService;
import org.apache.shiro.crypto.hash.Md5Hash;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


@Service
public class UserServiceImpl implements UserService {
    @Autowired
    private UserMapper userMapper;
    @Override
    public Integer checkUsername(String username) {
        if (!StringUtils.isNullOrEmpty(username)){
            username=username.trim();
        }
        Integer count=userMapper.findCountUsername(username);
        return count;
    }

    @Override
    public Integer register(User user) {
        //对密码加密
        String newPwd=new Md5Hash(user.getPassword(),null,1024).toString();
        user.setPassword(newPwd);
        //用maapper保存数据
        Integer count=userMapper.save(user);
        //返回信息
        return count;


    }

    @Override
    public User login(String username, String password) {
//       1 根据用户名查询信息
        User user = userMapper.findByUsername(username);


        if(user != null) {
            //2.1 如果不为null -> 判断密码.
            String newPwd = new Md5Hash(password,null,1024).toString();
            //3. 如果密码正确,直接返回查询到user.
            if(user.getPassword().equals(newPwd)){
                // 登录成功,返回user对象
                return user;
            }
        }
        //4. 其他情况统一返回null.
        return null;
    }
}
