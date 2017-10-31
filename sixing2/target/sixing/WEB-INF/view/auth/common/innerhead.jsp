<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<c:set var="ctx" value="${pageContext.request.contextPath}"/>
<!-- 头部内容开始 -->
	<div class="adminhead">
		<div class="adminhead1">后台管理系统</div>
		<div class="adminhead2">
			<b><a href="${ctx }/auth/operator/changePwd" target="framecont">修改密码</a></b>&nbsp;&nbsp;<b>${sessionScope.operator.operatorname}</b>  【<a href="${ctx}/auth/login/outLogin">退出</a>】
		</div>
	</div>
<!-- 头部内容结束 -->