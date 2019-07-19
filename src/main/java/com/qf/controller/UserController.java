package com.qf.controller;


import org.springframework.util.StringUtils;
import com.qf.pojo.User;
import com.qf.service.UserService;
import com.qf.util.SendSMSUtil;
import com.qf.vo.ResultVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import static com.qf.constant.Ssmconstant.*;

@Controller
@RequestMapping("/user")
public class UserController {
    @Autowired
    private UserService userService;

//    短信工具
    @Autowired
    private SendSMSUtil sendSMSUtil;

//   跳转到注册页面

    @GetMapping("/register-ui")
    public String registerUI(){
        return "user/register";
    }
    @PostMapping("/check-username")
    @ResponseBody
    public ResultVo checkUsername(@RequestBody User user){
       //调用service 判断用户名是否可用
       Integer count=userService.checkUsername(user.getUsername());

       //使用resultvo相应数据
        return new ResultVo(0,"成功",count);
    }
    //发送短信
    @PostMapping(value = "/send-sms",produces = "text/html;charset=utf-8")
    @ResponseBody
    public  String sendSMS(@RequestParam String phone, HttpSession session){
        //调用工具发送短信
        String result=sendSMSUtil.sendSMS(session, phone);
        //讲result响应给ajax引擎
        return result;
    }
    @PostMapping("/register")
    public String register(@Valid User user, BindingResult
            bindingResult, String registerCode,
            HttpSession session, RedirectAttributes redirectAttributes){

        //1校验验证码
        if (!StringUtils.isEmpty(registerCode)){
            // 验证码不为空,
            String code = (int) session.getAttribute(CODE) + "";
            if(!registerCode.equals(code)){
                // 验证码不正确
                redirectAttributes.addAttribute("registerInfo","验证码错误!");
                return REDIRECT + REGISTER_UI;
            }
        }
        //校验参数是否合法
        if (bindingResult.hasErrors() || StringUtils.isEmpty(registerCode)){
            String msg = bindingResult.getFieldError().getDefaultMessage();
            if(StringUtils.isEmpty(msg)){
                msg = "验证码为必填项,岂能不填!";
            }
            redirectAttributes.addAttribute("registerInfo",msg);
            return REDIRECT + REGISTER_UI;
        }
        //调用service 执行注册功能
        Integer count=userService.register(user);
        //根据service返回的结果跳转到指定页面
        if (count==1){
            //注册成功
            return REDIRECT + LOGIN_UI;

        }else {
            redirectAttributes.addAttribute("registerInfo","服务器爆炸");
            return REDIRECT + REGISTER_UI;
        }
    }
    //跳转到登录页面
    //get请求 http://localhost/user/login-ui
    @GetMapping("/login-ui")
    public String loginUI(){

        return "user/login";
    }
    //执行登录
    //request url:http://locahost/user/login
    //request method:post
    @PostMapping("/login")
    @ResponseBody
    public ResultVo login(String username,String password,HttpSession session){
        //1校验参数是否合法
        if (StringUtils.isEmpty(username) || StringUtils.isEmpty(password)){
            //参数buhefa
            return new ResultVo(1, "用户名和密码为必填项", null);
        }
        //2调用service执行登录
        User user=userService.login(username,password);
        //3根据service返回结果判断登录是否成功
        if (user!=null) {
            //4如果成功  将用户信息放入session中
            session.setAttribute("USER", user);
            return new ResultVo(0, "登陆成功", null);
        }else {
            //5如果失败  ,响应错误信息给ajax引擎
            return new ResultVo(2, "用户名和密码错误", null);
        }
    }




}
