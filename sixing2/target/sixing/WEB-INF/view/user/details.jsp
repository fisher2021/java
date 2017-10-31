<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ include file="../common/taglibs.jsp" %>

<!DOCTYPE HTML>
<html>
<head>
    <%@ include file="../common/head.jsp" %>
	<title>用户管理-用户详情</title>
</head>
<body>
    <article class="page-container layer-container">
        <form class="form form-horizontal" id="form-submit" enctype="multipart/form-data">
            <div class="row cl">
                <label class="form-label col-xs-4 col-sm-3">手机号：</label>
                <div class="formControls col-xs-7 col-sm-8">
                    <input type="text" class="input-text" autocomplete="off" value="${user.phone }" placeholder="请输入手机号"  id="phone" name="phone" readonly/>
                </div>
            </div>
            <div class="row cl">
                <label class="form-label col-xs-4 col-sm-3">昵称：</label>
                <div class="formControls col-xs-7 col-sm-8">
                    <input type="text" class="input-text" autocomplete="off" value="${userInfo.nickname }" placeholder="请输入昵称" name="nickname" readonly/>
                </div>
            </div>
            <div class="row cl">
                <label class="form-label col-xs-4 col-sm-3">头像：</label>
                <div class="formControls col-xs-7 col-sm-8">
                	<img src="${userInfo.headImage }" alt="用户头像"/>
                </div>
            </div>
            <div class="row cl">
                <label class="form-label col-xs-4 col-sm-3">用户类型：</label>
                <div class="formControls col-xs-7 col-sm-8">
                    <input type="radio" name="member" class="mr-5" id="common-user" value="false" <c:if test="${(empty userInfo) || (!userInfo.member) }">checked</c:if> disabled/><label for="common-user" class="mr-10">普通用户</label>
                    <input type="radio" name="member" class="mr-5" id="vip-user" value="true" <c:if test="${userInfo.member }">checked</c:if> disabled/><label for="vip-user" class="mr-10">会员用户</label>
                </div>
            </div>
            <c:if test="${userInfo.member }">
            <div class="row cl member-num">
                <label class="form-label col-xs-4 col-sm-3">会员号：</label>
                <div class="formControls col-xs-7 col-sm-8">
                    <input type="text" class="input-text" autocomplete="off" value="${userInfo.memberNumber }" name="memberNumber" readonly/>
                </div>
            </div>
            </c:if>
            <c:if test="${userInfo.member }">
            <div class="row cl member-dis">
				<label class="form-label col-xs-4 col-sm-3">会员所属区域：</label>
				<div class="formControls col-xs-7 col-sm-8">
					<input type="text" class="input-text" autocomplete="off" value="${userInfo.district.name }" readonly/>
				</div>
			</div>
			</c:if>
        </form>
    </article>
    <script type="text/javascript" src="${ctx }/static/back/lib/jquery.validation/1.14.0/jquery.validate.js"></script>
 	<script type="text/javascript" src="${ctx }/static/back/admin/h-ui.admin/js/form.js"></script>
    <!--请在下方写此页面业务相关的脚本-->
    <script type="text/javascript">
        
    </script>
</body>
</html>