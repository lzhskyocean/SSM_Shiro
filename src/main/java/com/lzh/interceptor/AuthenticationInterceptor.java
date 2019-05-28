package com.lzh.interceptor;

import com.lzh.bean.User;
import com.lzh.constant.SSMConstant;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * @author lizhenhao
 */

/*
* 认证拦截
* */
public class AuthenticationInterceptor implements HandlerInterceptor {

    /*
     * 请求到达前端控制器之前(实际源码:在获取处理器适配器之后)
     * */
    @Override
    public boolean preHandle(HttpServletRequest request,
                             HttpServletResponse response,
                             Object handler) throws Exception {
        //权限的校验
        //1.获取session对象
        HttpSession session = request.getSession();

        //2.从session中获取user对象
        User user = (User) session.getAttribute(SSMConstant.USER);

        //3.判断用户是否登录
        if (user != null) {
            //用户登录则放行
            return true;
        }
        //用户未登录则进行拦截,并重定向到登录页面
        response.sendRedirect(request.getContextPath() + SSMConstant.USER_LOGIN);
        return false;
    }

    /*
     * 渲染视图之前
     * */
    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {

    }

    /*
     * 响应数据之前
     * */
    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {

    }

}
