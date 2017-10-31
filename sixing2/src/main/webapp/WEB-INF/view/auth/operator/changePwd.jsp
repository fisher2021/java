<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
	<head>
		<base href="<%=basePath%>">

		<title>操作员编辑</title>

		<meta http-equiv="pragma" content="no-cache">
		<meta http-equiv="cache-control" content="no-cache">
		<meta http-equiv="expires" content="0">
		<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
		<meta http-equiv="description" content="This is my page">
		<!--
	<link rel="stylesheet" type="text/css" href="styles.css">
	-->
		<link href="<%=path%>/static/auth/css/common/layout.css" rel="stylesheet"
			  type="text/css" />
		<Script src="<%=path%>/static/common/js/jquery-1.9.1.min.js"></Script>
		<script type="text/javascript" src="<%=path %>/static/common/js/base.js"></script>
		<script>
			function subForm(){
				if($("#repwd").val()==$("#pwd").val()){
					$("#dataform").submit();
				}else{
					alert("两次密码不一致")
				}
			}
		</script>
	</head>

	<body>
		<form action="<%=path%>/auth/operator/saveOperator" id="dataform" method="post">
			<!-- 隐藏字段功能信息 -->
			<input type="hidden" name="id" value="${operator.id }" />
				<div class="edit_editinfo_property">
					<div class="edit_editinfo_property_lable">
						登录名：
					</div>
					<div class="edit_editinfo_property_value">
						<input type="text" name="userid" readOnly = readOnly value="${ operator.userid}" />
					</div>
				</div>
				<div class="edit_editinfo_property">
					<div class="edit_editinfo_property_lable">
					</div>
					<div class="edit_editinfo_property_value">
					</div>
				</div>
				<div class="edit_editinfo_property">
					<div class="edit_editinfo_property_lable">
						登录密码：
					</div>
					<div class="edit_editinfo_property_value">
						<input type="password" id="pwd" name="pwd" value="" />
					</div>
				</div>
				<div class="edit_editinfo_property">
					<div class="edit_editinfo_property_lable">
					</div>
					<div class="edit_editinfo_property_value">
					</div>
				</div>
				<div class="edit_editinfo_property">
					<div class="edit_editinfo_property_lable">
						再次输入密码：
					</div>
					<div class="edit_editinfo_property_value">
						<input type="password" id="repwd" name="repwd" value="" />
					</div>
				</div>
			<div class="container_search_btn" id="container_search_btn"
				name="searchTitle" title="">
				<input type="button" value="保存" onClick="subForm()" />
			</div>
		</form>
	</body>
</html>
