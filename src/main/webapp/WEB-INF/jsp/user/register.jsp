<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt"  prefix="fmt"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>注册账号页面</title>
<link rel="stylesheet" href="${pageContext.request.contextPath }/static/css/bootstrap.css"/>
<link rel="stylesheet" href="${pageContext.request.contextPath }/static/css/1.css"/>
</head>
<body> 
<div class="page-header">
    <h1>注册账号</h1>
</div>
<form id="user" action="${pageContext.request.contextPath}/user/register" method="post" >
    <div class="form-group">
        <label for="username">用户名:</label>
        <input type="password" class="form-control" name="username" id="username" placeholder="请输入用户名" onblur="checkUsername(this)"/>
        <span style="margin-left: 50px;" id="usernameInfo"></span>
    </div>
    <div class="form-group">
        <label for="password">密码:</label>
        <input type="password" class="form-control" name="password" id="password" placeholder="请输入密码" />
         <span id="checkPasswordInfo"></span>
    </div>
    <div class="form-group">
        <label for="phone">手机号:</label>
        <div>
            <input type="text" class="form-control" name="phone" id="phone" placeholder="请输入手机号" style="display: inline;width: 200px;" />
            &nbsp;&nbsp;&nbsp;
            <button type="button" class="btn btn-primary" onclick="sendSMS(this)">发送验证</button>
        </div>

    </div>
    <div class="form-group">
        <label for="registerCode">请输入验证码:</label>
        <input type="text" class="form-control" name="registerCode" id="registerCode" placeholder="请输验证码" />
    </div>
    <button type="submit" class="btn btn-primary">注册</button>
    &nbsp;&nbsp;&nbsp;
    <button type="button" onclick="location.href = '${pageContext.request.contextPath}/user/login'" class="btn btn-success">跳转登录</button>
</form>
</body>
<script type="text/javascript" src="${pageContext.request.contextPath }/static/js/jquery.min.js"></script>
<script type="text/javascript">

    //异步验证用户名
    function  checkUsername(obj){

        //1.获取表单某个输入框的数据
        //var username = obj.value;

        var $username = $(obj).val();

        //2.
        if($username==""){//非空判断
            alert("用户名不能为空");
            return;
        }
        //3.发起ajax请求并携带请求参数到后台
        $.ajax({
            url:"${pageContext.request.contextPath}/user/checkusername",
            type:"POST",
            data:"{\"username\":\""+$username+"\"}",
            dataType:"json",
            success:function (result) {
                if(result.data==0){
                    $("#usernameInfo").html("用户名可以使用.").css("color","green");
                }else {
                    $("#usernameInfo").html("用户名已被注册!").css("color","red");
                }
            },
            error:function (result) {
                alert("系统正忙,稍后再试!!!")
            },
            contentType:"application/json;charset=utf-8"
        })


    }




    wait = 60;
    //发送手机短信
    function sendSMS(obj){
        //1.获得用户输入的手机号.
        var phone = $("#phone").val();
        //2. 校验手机号正确性.
        if(!(/^1[3|4|5|8|7][0-9]\d{4,8}$/.test(phone))){
            alert("不是完整的11位手机号或者正确的手机号前七位");
            return;
        }
        //3. 发送,发送验证码;
        //------------------------------------
        //简单post的ajax发送
        $.post(
            "${pageContext.request.contextPath}/user/sendsms",
            "phone="+phone,
            function (data) {
                alert(data);
            },
            "text"
        );


        //------------------------------------
        //4. 60s点击一次按钮.
        var wait = 60;
        time(obj);
    }


    // 页面让验证码显示60s后再次点击.
    function time(obj) {
        if (wait <= 0) {
            obj.removeAttribute("disabled");
            obj.innerHTML = "发送验证";
            wait = 60;
        } else {
            obj.setAttribute("disabled", true);
            obj.innerHTML = wait+"s后重试";
            wait--;
            setTimeout(function() {
                    time(obj);
                },
                1000);
        }
    }
</script>
</html>