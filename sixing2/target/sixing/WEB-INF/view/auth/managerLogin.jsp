<%--
  Created by IntelliJ IDEA.
  User: xiao.tao
  Date: 2016/5/24
  Time: 11:44
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ include file="../common/taglibs.jsp" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">

    <meta name="viewport" content="width=device-width, initial-scale=1.0" />
    <meta name="description" content="Xenon Boostrap Admin Panel" />
    <meta name="author" content="" />

    <title>登录</title>

    <!--    <link rel="stylesheet" href="http://fonts.useso.com/css?family=Arimo:400,700,400italic">-->
    <link rel="stylesheet" href="${ctx}/static/admin/assets/css/fonts/linecons/css/linecons.css">
    <link rel="stylesheet" href="${ctx}/static/admin/assets/css/fonts/fontawesome/css/font-awesome.min.css">
    <link rel="stylesheet" href="${ctx}/static/admin/assets/css/bootstrap.css">
    <link rel="stylesheet" href="${ctx}/static/admin/assets/css/xenon-core.css">
    <link rel="stylesheet" href="${ctx}/static/admin/assets/css/xenon-forms.css">
    <link rel="stylesheet" href="${ctx}/static/admin/assets/css/xenon-components.css">
    <link rel="stylesheet" href="${ctx}/static/admin/assets/css/xenon-skins.css">
    <link rel="stylesheet" href="${ctx}/static/admin/css/common/custom.css">

    <script src="${ctx}/static/admin/assets/js/jquery-1.11.1.min.js"></script>

    <!-- HTML5 shim and Respond.js IE8 support of HTML5 elements and media queries -->
    <!--[if lt IE 9]>
    <script src="https://oss.maxcdn.com/html5shiv/3.7.2/html5shiv.min.js"></script>
    <script src="https://oss.maxcdn.com/respond/1.4.2/respond.min.js"></script>
    <![endif]-->
    <style>
        .my-icon  #icon-error{right:100px;}
        .login-page{
            background-image: url("${ctx}/static/common/images/bg.jpg");
            background-size: 100% 100%;
            background-position: center center;
        }
        .login-form{
            background-color: rgba(255,255,255,0.5);
        }
    </style>

</head>
<body class="page-body login-page">
<div class="login-container">
    <div class="row">
        <div class="col-sm-6">
            <script type="text/javascript">
                jQuery(document).ready(function($)
                {

                    var msg='${msg}';
                    $("#errorMsessage").empty();
                    $("#errorMsessage").text(msg);
                    // Reveal Login form
                    setTimeout(function(){ $(".fade-in-effect").addClass('in'); }, 1);


                    // Validation and Ajax action
                    $("#login").validate({
                        rules: {
                            username: {
                                required: true
                            },
                            passwd: {
                                required: true
                            },
                            icon:{
                                required: true
                            }
                        },
                        messages: {
                            username: {
                                required: '请输入用户名'
                            },

                            passwd: {
                                required: '请输入密码'
                            },
                            icon:{
                                required: "请输入验证码"
                            }
                        },

                    });

                    // Set Form focus
                    $("form#login .form-group:has(.form-control):first .form-control").focus();
                });


                $(".btn-info").click(function(){
                    if($('#login').valid()){
                        $("#login").submit();
                    }
                });
            </script>

            <!-- Errors container -->
            <div class="errors-container">


            </div>

            <!-- Add class "fade-in-effect" for login form effect -->
            <form action="${ctx}/admin/dologin" method="post" role="form" id="login" class="login-form fade-in-effect">

                <div class="login-header">
                    <a href="/" class="logo">
                        <img src="" alt="" width="80" />
                        <span>log in</span>
                    </a>
                    <p>亲爱的用户,登录进入后台管理系统!</p>
                </div>

                <div class="form-group">
                    <label class="control-label" for="username">用户名</label>
                    <input type="text" class="form-control" name="username" id="username" value="${username}" autocomplete="off" />
                </div>

                <div class="form-group">
                    <label class="control-label" for="passwd">密码</label>
                    <input type="password" class="form-control" name="password" id="passwd" value="${password}" autocomplete="off" />
                </div>
                <div class="form-group my-icon">
                    <label class="control-label" for="icon">验证码</label>
                    <input type="text" class="form-control dib" name="icon" id="icon" autocomplete="off" value="${icon}" style="width:301px;"/>
                    <img src="" id="imgCode" class="dib">
                </div>
                <div class="form-group">
                    <span class="help-block">
                        <span class="middle" style="color: #ff0000;" id="errorMsessage"></span>
                    </span>
                </div>
                <div class="form-group">
                    <button type="submit" class="btn btn-info btn-block text-left">
                        <i class="fa-lock"></i>
                        登录
                    </button>
                </div>


            </form>

        </div>

    </div>

</div>



<!-- Bottom Scripts -->
<script src="${ctx}/static/admin/assets/js/bootstrap.min.js"></script>
<script src="${ctx}/static/admin/assets/js/TweenMax.min.js"></script>
<script src="${ctx}/static/admin/assets/js/resizeable.js"></script>
<script src="${ctx}/static/admin/assets/js/joinable.js"></script>
<script src="${ctx}/static/admin/assets/js/xenon-api.js"></script>
<script src="${ctx}/static/admin/assets/js/xenon-toggles.js"></script>
<script src="${ctx}/static/admin/assets/js/jquery-validate/jquery.validate.min.js"></script>
<script src="${ctx}/static/admin/assets/js/toastr/toastr.min.js"></script>


<!-- JavaScripts initializations and stuff -->
<script src="${ctx}/static/admin/assets/js/xenon-custom.js"></script>
<script>
    $(function(){
        changeCode();
        //更换验证码
        $('#imgCode').click(function(){
            changeCode();
        });
    });

    function changeCode() {
        var img = document.getElementById("imgCode");
        img.src = "registcode?" + new Date();
    }
</script>
</body>
</html>
