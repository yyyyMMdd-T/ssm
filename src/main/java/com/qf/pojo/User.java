package com.qf.pojo;

import lombok.Data;
import org.hibernate.validator.constraints.NotBlank;

import java.util.Date;

@Data
public class User {
    private long id;

    @NotBlank(message = "用户名为必填项,岂能为空")
    private String username;
    @NotBlank(message = "密码为必填项,岂能不填!")
    private String password;
    @NotBlank(message = "手机号为必填项,岂能不填!")
    private String phone;

    private Date created;
}
