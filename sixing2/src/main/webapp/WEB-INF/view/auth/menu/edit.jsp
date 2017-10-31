<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
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

		<title>菜单编辑</title>

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
		<link rel="stylesheet" href="${ctx}/static/admin/assets/css/bootstrap.css">
			<Script src="<%=path%>/static/common/js/jquery-1.9.1.min.js"></Script>
		<script type="text/javascript" src="<%=path %>/static/common/js/base.js"></script>
		<style>
			.easyui-validatebox{border:1px solid #e4e4e4}
		</style>
	</head>

	<body class="panel-body">
	<div class="main-content">
		<section class="search-env">
			<div class="row">
				<div class="col-sm-12">
					<div class="panel panel-default">
						<div class="panel-heading">
							<h3 class="panel-title">新增菜单</h3>
						</div>
						<div class="panel-body">
							<form action="<%=path%>/auth/menu/saveMenu" id="dataform" method="post" class="form-horizontal">
								<!-- 隐藏字段功能信息 -->
								<input type="hidden" name="id" value="${menu.id }" />
								<!--父菜单 -->
								<input type="hidden" name="parentMenu.id" value="${menu.parentMenu.id }" />
								<div class="form-group edit_editinfo_property">
									<label class="edit_editinfo_property_lable">
										菜单编码：
									</label>
									<div class="edit_editinfo_property_value">
										<input type="text" name="menuCode" value="${ menu.menuCode}"
											   class="easyui-validatebox" required="true"
											   validType="length[1,255]" missingMessage="功能名称不能为空!"  style="width: 450px;height: 30px"/>
										<span>*</span>
									</div>
								</div>
								<div class="form-group edit_editinfo_property">
									<label class="edit_editinfo_property_lable">
										菜单名称：
									</label>
									<div class="edit_editinfo_property_value">
										<input class="form-control" type="text" name="menuName" value="${ menu.menuName}" style="width: 450px;height: 30px"/>
									</div>
								</div>
								<div class="form-group edit_editinfo_property">
									<label class="edit_editinfo_property_lable">
										菜单显示名称：
									</label>
									<div class="edit_editinfo_property_value">
										<input class="form-control" type="text" name="menuLabel" value="${ menu.menuLabel}" style="width: 450px;height: 30px"/>
									</div>
								</div>
								<div class="form-group edit_editinfo_property">
									<label class="edit_editinfo_property_lable">
										执行路径：
									</label>
									<div class="edit_editinfo_property_value">
										<input type="text" class="form-control" name="url" value="${ menu.url}" style="width: 450px;height: 30px"/>
									</div>
								</div>
								<div class="form-group edit_editinfo_property">
									<label class="edit_editinfo_property_lable">
										菜单样式：
									</label>
									<div class="edit_editinfo_property_value">
										<input type="text" class="form-control" name="menuClass" value="${ menu.menuClass}" style="width: 450px;height: 30px"/>
									</div>
								</div>
								<div class="form-group edit_editinfo_property">
									<label class="edit_editinfo_property_lable">
										是否新窗口：
									</label>
									<div class="edit_editinfo_property_value">
										<select name="blank" class="form-control" style="height: 30px;width: 450px">
											<option value="0" <c:if test="${ menu.blank=='0'}">selected</c:if>>否</option>
											<option value="1"  <c:if test="${ menu.blank=='1'}">selected </c:if>>是</option>
										</select>
									</div>
								</div>
								<div class="form-group edit_editinfo_property" id="container_search_btn" name="searchTitle" title="" style="text-align: center">
									<input class="btn btn-info form-control" type="button" value="保存" onClick="submitForm()" style="width: 100px"/>
									<input class="btn btn-info form-control" type="button" value="返回" onClick="javascript:history.go(-1);" style="width: 100px"/>
								</div>
							</form>
					    </div>
					</div>
				</div>
			</div>
		</section>
	</div>
	</body>
</html>
