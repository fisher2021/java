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
		<link rel="stylesheet" href="${ctx}/static/admin/assets/css/bootstrap.css">
		<link rel="stylesheet" href="${ctx}/static/admin/assets/css/xenon-core.css">
		<link href="<%=path%>/static/auth/css/common/layout.css" rel="stylesheet"
			  type="text/css" />
		<Script src="<%=path%>/static/common/js/jquery-1.9.1.min.js"></Script>
		<script type="text/javascript" src="<%=path %>/static/common/js/base.js"></script>
		<style>
			.edit_editinfo_property_value input{width: 700px;height:32px}
			.btn{width: 100px}
		</style>
	</head>

	<body class="page-body" style="width: 100%">
	<div class="main-content">
		<div class="panel panel-default">
			<div class="panel-heading">
				<h3 class="panel-title"> 新增操作员</h3>
			</div>
			<div class="panel-body">
				<form class="form-horizontal" action="<%=path%>/auth/operator/saveOperator" id="dataform" method="post">
					<!-- 隐藏字段功能信息 -->
					<input type="hidden" name="id" value="${operator.id }" />
					<div class="form-group  edit_editinfo_property">
						<div class="edit_editinfo_property_lable">
							登录名：
						</div>
						<div class="edit_editinfo_property_value">
							<input type="text" class="form-control" name="userid" value="${ operator.userid}" />
						</div>
					</div>
					<div class="form-group  edit_editinfo_property">
						<div class="edit_editinfo_property_lable">
							登录密码：
						</div>
						<div class="edit_editinfo_property_value">
							<input class="form-control" type="text" name="pwd" value="*" />
						</div>
					</div>
					<div class="form-group edit_editinfo_property">
						<div class="edit_editinfo_property_lable">
							操作员名称：
						</div>
						<div class="edit_editinfo_property_value">
							<input class="form-control" type="text" name="operatorname" value="${ operator.operatorname}" />
						</div>
					</div>
					<div class="form-group edit_editinfo_property">
						<div class="edit_editinfo_property_lable">
							操作员描述：
						</div>
						<div class="edit_editinfo_property_value">
							<input class="form-control" type="text" name="operatordesc" value="${ operator.operatordesc}" />
						</div>
					</div>

					<div class="form-group" id="container_search_btn" name="searchTitle" title="" style="text-align: center">
						<input class="btn btn-info" type="button" value="保存" onClick="submitForm()" />
						<input class="btn btn-info form-control" type="button" value="返回" onClick="javascript:history.go(-1);" style="width: 100px"/>
					</div>
				</form>
				</div>
			</div>
		</div>

	</body>
</html>
