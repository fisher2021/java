<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ include file="../common/taglibs.jsp" %>

<!DOCTYPE HTML>
<html>
<head>
	<%@ include file="../common/head.jsp" %>
	<title>系统管理-权限编辑</title>
</head>
<body>
	<article class="page-container layer-container">
		<form class="form form-horizontal" id="form-submit">
			<input type="hidden" name="id" value="${role.id }">
			<div class="row cl">
                <div class="col-xs-9 col-sm-2 col-xs-offset-2 col-sm-offset-2">
		            <div class="authority-menu">
		            	<c:forEach items="${menuList }" var="menu">
		            		<c:if test="${empty menu.parent }">
		            			<div class="parent-box">
				                    <span class="authority-menu-icon authority-menu-icon-down"></span>
				                    <span class="parent-title va-m">${menu.name }</span>
				                    <input type="checkbox" value="${menu.id }" name="menus" <c:forEach items="${role.menuList }" var="menu_checked"><c:if test="${menu_checked.id==menu.id }">checked</c:if></c:forEach>/>
		                		</div>
		                		<div class="child-panel">
		                		<c:forEach items="${menuList }" var="menu_child">
		                			<c:if test="${not empty menu_child.parent && menu.id==menu_child.parent.id}">
		                				<p>${menu_child.name } <input type="checkbox" value="${menu_child.id }" name="menus" <c:forEach items="${role.menuList }" var="menu_checked"><c:if test="${menu_checked.id==menu_child.id }">checked</c:if></c:forEach>/></p>
		                			</c:if>
		                		</c:forEach>
		                		</div>
		            		</c:if>
		            	</c:forEach>
		            </div>
              	</div>
            </div>
            <div class="row cl">
                <div class="col-xs-9 col-sm-8 col-xs-offset-2 col-sm-offset-2">
                    <button type="reset" class="btn btn-success size-M radius" id="reset">重置</button>
                    <button type="button" class="btn btn-danger radius ml-20" id="submit">提交</button>
                </div>
            </div>
		</form>
	</article>
	<script type="text/javascript" src="${ctx }/static/back/admin/h-ui.admin/js/checked.js"></script>
	<!--请在下方写此页面业务相关的脚本-->
	<script type="text/javascript">
		$("#submit").click(function(){
	    	subForm('${ctx}','${ctx}/back/role/ajax/editRoleMenu');
	    })
	</script>
	<!--/请在上方写此页面业务相关的脚本-->
</body>
</html>