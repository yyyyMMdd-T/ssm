package com.qf.mapper;


import com.qf.pojo.User;
import org.apache.ibatis.annotations.Param;

public interface UserMapper {
//    根据用户查询数据条数
    Integer findCountUsername(@Param("username")String username);

//    添加用户信息
    Integer save(User user);

    User findByUsername(@Param("username")String username);

    //根据用户名查询信息

//    User findByUsername(@Param("username")String username);

}
