package com.lzh.controller;

import com.lzh.bean.User;
import com.lzh.constant.SSMConstant;
import com.lzh.service.UserService;
import com.yunpian.sdk.YunpianClient;
import com.yunpian.sdk.model.Result;
import com.yunpian.sdk.model.SmsSingleSend;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.crypto.hash.Md5Hash;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import java.util.HashMap;
import java.util.Map;


/**
 * @author lizhenhao
 */

@Controller
@RequestMapping("/user")
public class UserController {


    @Autowired
    private UserService userService;


    //加载properties文件
    @Value("${yunpian.apikey}")
    private String apikey;


    /*
     * 异步校验用户数据
     * @RequestBod接收json数据(接收数据参数的位置可以是Map或者POJO类)
     *
     * @ResponseBody返回json数据或者通过原生的HttpServletResponse
     *
     * */
    @RequestMapping("/checkusername")
    @ResponseBody //返回的数据不通过视图解析器,直接返回浏览器/ajax
    public Map<String, Object> checkUsername(@RequestBody User user) {
        /*
            jsonlib.(很少用,版本太旧)
            jackson.(spring默认支持--spring推荐)
            gson.(spring支持,当POJO类过于复杂时,序列化比其他方式更稳定)
            fastJSON.(号称速度最快的json序列化工具)
         */
        String username = user.getUsername();

        //健壮性判断(在这只是体现一下StringUtils工具类的isEmpty方法,仅此而已)
        /*if (!StringUtils.isEmpty(username)){
            username = username.trim();
        }*/

        Integer count = userService.checkUsername(username);

        Map<String, Object> map = new HashMap<>();

        map.put("data", count);

        return map;
    }


    /*
        手机发送短信模块
     */
    @RequestMapping("/sendsms")
    @ResponseBody
    public String sendSms(@RequestParam String phone, HttpSession session) {

        System.out.println("用户手机号:" + phone);

        //初始化clnt,使用单例模式
        YunpianClient clnt = new YunpianClient(apikey).init();

        //随机生成6位的验证码
        int code = (int) ((Math.random() * 9 + 1) * 1000000);
        System.out.println(code);
        //将正确的验证码code放到session域中
        session.setAttribute(SSMConstant.CODE, code);

        //发送短API
        Map<String, String> param = clnt.newParam(2);
        param.put(YunpianClient.MOBILE, phone);
        param.put(YunpianClient.TEXT, "【云片网】您的验证码是" + code);
        Result<SmsSingleSend> r = clnt.sms().single_send(param);
        //获取返回结果，返回码:r.getCode(),返回码描述:r.getMsg(),API结果:r.getData(),其他说明:r.getDetail(),调用异常:r.getThrowable()
        //账户:clnt.user().* 签名:clnt.sign().* 模版:clnt.tpl().* 短信:clnt.sms().* 语音:clnt.voice().* 流量:clnt.flow().* 隐私通话:clnt.call().*

        //释放clnt
        clnt.close();

        return "ok";

    }

    /*
     * 用户注册
     * */
    @RequestMapping(value = "/register")
    public String register(@Valid User user,
                           BindingResult bindingResult,
                           String registerCode,
                           HttpSession session
    ) {
        //1.判断参数是否合法
        if (StringUtils.isEmpty(registerCode) || bindingResult.hasErrors()) {
            return SSMConstant.REGISTER_PAGE;
        }

        String phoneCode = String.valueOf(session.getAttribute(SSMConstant.CODE));

        //2.验证码是否正确
        if (phoneCode.equals(registerCode)) {
            //进行注册
            Integer count = userService.register(user);

            if (count == 1) {//重定向到登录页面

                return SSMConstant.REDIRECT + SSMConstant.LOGIN_PAGE;
            }

        }
        return SSMConstant.REGISTER_PAGE;

    }

    /*
     * 用户登录
     * */
    @RequestMapping("/login")
    public String login(String username,
                        String password,
                        HttpSession session,
                        Model model
    ) {


        //1.区区主题









        //1.校验:是否错误信息
        if (StringUtils.isEmpty(username) || StringUtils.isEmpty(password)) {

            return SSMConstant.LOGIN_PAGE;
        }

        //2.获取主体
        Subject subject = SecurityUtils.getSubject();


        try {
            //3.主体认证提交请求
            subject.login(new UsernamePasswordToken(username,password));
            //4.登录成功
            return SSMConstant.REDIRECT + SSMConstant.ITEM_LIST;
        } catch (AuthenticationException e) {
            e.printStackTrace();
            //登录失败
            return SSMConstant.LOGIN_PAGE;
        }






        //2.先根据用户名进行查询
     /*   User user = userService.login(username);

        if (user != null) {//用户不为空,则进行密码验证
            //检验密码
            if (user.getPassword().equals(new Md5Hash(password).toString())) {
                //密码验证成功,将user放入session中
                session.setAttribute(SSMConstant.USER, user);

                return SSMConstant.REDIRECT + SSMConstant.ITEM_LIST;
            }
        }

        model.addAttribute(SSMConstant.LOGIN_INFO, "你是傻逼小胖墩!!!");

        return SSMConstant.LOGIN_PAGE;*/
    }

}
