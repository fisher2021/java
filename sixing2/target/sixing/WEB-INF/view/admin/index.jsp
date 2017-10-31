<%--
  Created by IntelliJ IDEA.
  User: Administrator
  Date: 2016/4/28
  Time: 22:13
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
  <meta name="description" content="SMOK" />
  <meta name="author" content="" />
  <title>后台管理端首页</title>

  <!--<link rel="stylesheet" href="http://fonts.useso.com/css?family=Arimo:400,700,400italic">-->
  <link rel="stylesheet" href="${ctx}/static/admin/assets/css/fonts/linecons/css/linecons.css">
  <link rel="stylesheet" href="${ctx}/static/admin/assets/css/fonts/fontawesome/css/font-awesome.min.css">
  <link rel="stylesheet" href="${ctx}/static/admin/assets/css/bootstrap.css">
  <link rel="stylesheet" href="${ctx}/static/admin/assets/css/xenon-core.css">
  <link rel="stylesheet" href="${ctx}/static/admin/assets/css/xenon-forms.css">
  <link rel="stylesheet" href="${ctx}/static/admin/assets/css/xenon-components.css">
  <link rel="stylesheet" href="${ctx}/static/admin/assets/css/xenon-skins.css">
  <link rel="stylesheet" href="${ctx}/static/admin/css/common/custom.css">

  <script src="${ctx}/static/admin/assets/js/jquery-1.11.1.min.js"></script>
  <!--<script src="../static/js/auth/menu/indexMenu.js"></script>-->
  <!-- HTML5 shim and Respond.js IE8 support of HTML5 elements and media queries -->
  <!--[if lt IE 9]>
  <script src="https://oss.maxcdn.com/html5shiv/3.7.2/html5shiv.min.js"></script>
  <script src="https://oss.maxcdn.com/respond/1.4.2/respond.min.js"></script>
  <![endif]-->
  <style>
    .sidebar-menu{
      min-width: 260px !important;
      z-index: 1 !important
    }
    .page-container .main-content{
      background-color: #eeeeee;
    }
  </style>

</head>
<body class="page-body">

<div class="page-container" ><!-- add class "sidebar-collapsed" to close sidebar by default, "chat-visible" to make chat appear always -->

  <!-- Add "fixed" class to make the sidebar fixed always to the browser viewport. -->
  <!-- Adding class "toggle-others" will keep only one menu item open at a time. -->
  <!-- Adding class "collapsed" collapse sidebar root elements and show only icons. -->
  <div class="sidebar-menu toggle-others fixed" >

    <div class="sidebar-menu-inner" style="width:270px">

      <header class="logo-env" style="height: 50px !important;">

        <!-- logo -->
        <div class="logo">
          <a href="home.html" class="logo-expanded" target="evendor">
            <img src="${ctx}/static/userManage/common/images/en_US/logo_03.png" width="80" alt="" />
          </a>
          <a href="dashboard-1.html" class="logo-collapsed" style="color: #fff;font-size: 24px;">
            SM
          </a>
        </div>

        <!-- This will toggle the mobile menu and will be visible only on mobile devices -->
        <div class="mobile-menu-toggle visible-xs">


          <a href="#" data-toggle="mobile-menu">
            <i class="fa-bars"></i>
          </a>
        </div>

        <!-- This will open the popup with user profile settings, you can use for any purpose, just be creative -->
        <!--	<div class="settings-icon">
                <a href="#" data-toggle="settings-pane" data-animate="true">
                    <i class="linecons-cog"></i>
                </a>
            </div>-->


      </header>



      <ul id="main-menu" class="main-menu">
        <!-- add class "multiple-expanded" to allow multiple submenus to open -->
        <!-- class "auto-inherit-active-class" will automatically add "active" class for parent elements who are marked already with class "active" -->
        <c:forEach var="menu" items="${authmenu}">
          <li class="dropdown hover-line">
            <a href="#" target="evendor" onclick="parentClick('${menu.menuLabel}');">
              <i class="${menu.menuClass}"></i>
              <span class="title" id="label" >${menu.menuLabel}</span>
              <script type='text/javascript'>
                function  parentClick(label){
                  $("#first").text(" > "+label);
                  $("#sec").text("");
                }
              </script>
            </a>
            <ul>
              <c:forEach var="submenu" items="${menu.subMenuList}">
                <li>
                  <a href="${ctx}/${submenu.url}" target="evendor" onclick="subClick('${submenu.menuLabel}');">
                    <span class="title" id="subLabel" >${submenu.menuLabel}</span>
                    <script type='text/javascript'>
                              function  subClick(label){
                                $("#sec").text(" > "+label);
                              }
                    </script>

                  </a>
                </li>
              </c:forEach>
            </ul>
          </li>
        </c:forEach>
      </ul>

    </div>

  </div>

  <div class="main-content" style="background-color: #eeeeee">

    <!-- User Info, Notifications and Menu Bar -->
    <nav class="navbar user-info-navbar" role="navigation"  style="height: 60px !important;min-width: 1390px !important;">
        <div class="col-sm-5" style="height: 50px">
          <div class="breadcrumb-env" style="padding:20px 20px 15px 10px">
            <ol class="breadcrumb bc-1">
              <li class="active" id="">
                <a href="javascript:;"><i class="fa-home"></i>主页</a>
                <a href="javascript:;"><i id="first">  </i></a>
                <a href="javascript:;"><i id="sec">  </i></a>
              </li>
              <%--<li>--%>
                <%--<a href="forms-native.html">Forms</a>--%>
              <%--</li>--%>
              <%--<li class="active">--%>
                <%--<strong>Input Masks</strong>--%>
              <%--</li>--%>
            </ol>
          </div>
        </div>



      <!-- Right links for user info navbar -->
      <ul class="user-info-menu right-links list-inline list-unstyled" style="height: 50px">

        <li class="search-form" style="height: 50px"><!-- You can add "always-visible" to show make the search input visible -->

          <form method="get" action="extra-search.html"  style="padding:20px 20px 15px 20px">
            <input type="text" name="s" class="form-control search-field" placeholder="请输入搜索内容" />


          </form>

        </li>

        <li class="dropdown user-profile" style="height: 50px">
          <a href="#" data-toggle="dropdown" style="padding:20px 20px 15px 20px">
            <!--	<img src="assets/images/user-4.png" alt="user-image" class="img-circle img-inline userpic-32" width="28" />-->
							<span>
								<strong>${operator.userid}   </strong>
                              <!--<i class="fa-angle-down"></i>-->
							</span>
          </a>

        </li>

        <li style="height: 50px" >
          <a href="${ctx}/admin/outLogin" style="padding:20px 20px 15px 20px">
            退出
          </a>
        </li>

      </ul>

    </nav>
    <!--page content-->

    <div class="iframeContent">
      <iframe id="evendor" name="evendor" style="min-width:1371px;overflow-x:hidden;overflow-y: hidden" scrolling="no" frameborder="0" width="100%"  src="${ctx}/static/admin/home.html">

      </iframe>
    </div>
    <script>
      //			$(".iframeContent").css("height",$(".main-content").css("height"));
      $("#evendor").load(function(){
        var mainheight = $(this).contents().find(".main-content").height()+250;
        var mainWidth = $(this).contents().find(".main-content").width();
        console.log(mainWidth);
        if($(this).contents().find("frameset")){
           mainheight = $(this).contents().find(".main-content").height()+350;
        }
        if(mainWidth>1000){
          $(this).contents().find(".main-content").css("min-width","1300px")
        }
        $(this).height(mainheight);
      });
    </script>
    <!--page content end-->
    <!-- Main Footer -->
    <!-- Choose between footer styles: "footer-type-1" or "footer-type-2" -->
    <!-- Add class "sticky" to  always stick the footer to the end of page (if page contents is small) -->
    <!-- Or class "fixed" to  always fix the footer to the end of page -->
    <footer class="main-footer sticky footer-type-1" style="height:60px;">

      <div class="footer-inner">

        <!-- Add your copyright text here -->



        <!-- Go to Top Link, just add rel="go-top" to any link to add this functionality -->
        <div class="go-up">

          <a href="#" rel="go-top">
            <i class="fa-angle-up"></i>
          </a>

        </div>

      </div>

    </footer>
  </div>





</div>


<div class="page-loading-overlay">
  <div class="loader-2"></div>
</div>




<!-- Bottom Scripts -->
<script src="${ctx}/static/admin/assets/js/bootstrap.min.js"></script>
<script src="${ctx}/static/admin/assets/js/TweenMax.min.js"></script>
<script src="${ctx}/static/admin/assets/js/resizeable.js"></script>
<script src="${ctx}/static/admin/assets/js/joinable.js"></script>
<script src="${ctx}/static/admin/assets/js/xenon-api.js"></script>
<script src="${ctx}/static/admin/assets/js/xenon-toggles.js"></script>
<!-- Imported scripts on this page -->
<script src="${ctx}/static/admin/assets/js/xenon-widgets.js"></script>
<script src="${ctx}/static/admin/assets/js/devexpress-web-14.1/js/globalize.min.js"></script>
<script src="${ctx}/static/admin/assets/js/devexpress-web-14.1/js/dx.chartjs.js"></script>
<script src="${ctx}/static/admin/assets/js/toastr/toastr.min.js"></script>


<!-- JavaScripts initializations and stuff -->
<script src="${ctx}/static/admin/assets/js/xenon-custom.js"></script>

</body>
</html>