package com.qf.interceptor;

import com.qf.constant.Ssmconstant;
import com.qf.pojo.User;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class AuthenticationInterceptor implements HandlerInterceptor {

   //前处理   执行controller 方法 之前
    //可以选择对请求进行拦截/放行  false 拦截  true  放行
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object o) throws Exception {
       //拦截器的前处理
      //1获得 session
        HttpSession session=request.getSession();
        //2通过session获取用户信息
        User user= (User) session.getAttribute(Ssmconstant.USER);
        //3判断是否为null
        if (user==null) {
//        4为null用户为登录  跳转到登录页面
            response.sendRedirect(request.getContextPath() + "/user/login-ui");
            return false;
         } else{
//  5部位null  返回 true
          return true;
         }
    }

    //执行完 从controller 返回modelandview之后执行
    //拦截器的 post中 新修改 modelandview
    @Override
    public void postHandle(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o, ModelAndView modelAndView) throws Exception {
        System.err.println("post请求");
    }


    //相应数据之前
    @Override
    public void afterCompletion(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o, Exception e) throws Exception {
        System.err.println("拦截器后");
    }
}
