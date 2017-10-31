<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%
    String path = request.getContextPath();
    String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">

<html>
<head>
    <base href="<%=basePath%>">

    <title>后台管理系统</title>

    <meta http-equiv="pragma" content="no-cache">
    <meta http-equiv="cache-control" content="no-cache">
    <meta http-equiv="expires" content="0">
    <meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
    <meta http-equiv="description" content="This is my page">
    <script src="<%=path%>/static/common/js/jquery-1.9.1.min.js" type="text/javascript"></script>
    <link href="<%=path%>/static/auth/css/common/adminlogin.css" rel="stylesheet" type="text/css" />
    <script src="<%=path%>/static/auth/js/common/innerLogin.js" type="text/javascript"></script>
    <script type="text/javascript">
        var basepath ='<%=path%>';
        var msg='${msg}';
        jQuery(document).ready(function () {
            jQuery("#msgValue").empty();
            jQuery("#msgValue").append("<p>${msg}</p>");

            var userid = "${username}";
            var cookieUser=getCookie("operusername");
            var userpwd = "${password}";
            var cookieuserpwd=getCookie("operpassword");
            if (userid != "") {
                document.getElementById("username").value = userid;
            }
            else if(cookieUser!=null){
                document.getElementById("username").value = cookieUser;
            }
            if (userpwd != "") {
                document.getElementById("password").value = userpwd;
            }else if(cookieuserpwd!=null){
                document.getElementById("password").value = cookieuserpwd;
            }
        });
    </script>
</head>
<body>
<div id="login">
    <!-- 登陆信息开始 -->
    <div id="login_title">后台管理系统</div>
    <form name="form-login" method="post" action="<%=path%>/admin/dologin">
        <div class="login_cont">
            <div class="login_cont_name">
                <div class="login_cont_name1">用户名</div>
                <div class="login_cont_name2">
                    <input type="text" name="username" id="username" value="" onblur="emptyCheckName(this.value)" /></div>
                <div class="login_cont_name3" id="checkname"></div>
            </div>
            <div class="login_cont_pass">
                <div class="login_cont_pass1">密码</div>
                <div class="login_cont_pass2"><input type="password" name="password" id="password"  onblur="emptyCheckPwd(this.value)"/></div>
                <div class="login_cont_pass3" id="checkpwd" ></div>

            </div>
            <div class="login_cont_agree">
                <div class="login_cont_agree1"></div>
                <div class="login_cont_agree2">
                    <input type="checkbox"  id="loginin" onclick="saveMe()" />记住我
                </div>
                <div class="login_cont_agree3"></div>
            </div>

            <div class="login_cont_button">
                <div class="login_cont_button1"></div>
                <div class="login_cont_button2">

                    <input  onclick="submitForm()" type="button" name="submit1"  value="登录" />

                </div>
                <div id="msgValue" class="login_cont_button3"></div>
            </div>



        </div>
    </form>

    <!-- 登陆信息结束 -->
</div>



</body>
</html>
