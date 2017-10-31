<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ include file="../common/taglibs.jsp" %>

<!DOCTYPE HTML>
<html>
<head>
    <%@ include file="../common/head.jsp" %>
    <title>系统管理-权限管理</title>
    <style>
        body{
            min-width: 1000px;
        }
    </style>
</head>
<body>
<div class="page-container">
    <div class="operation_column">
       <a href="javascript:;" onclick="admin_add('新增菜单','${ctx}/back/menu/addOrEdit?parentId=${menu.id }','500','520')" class="btn btn-danger-outline radius f-r"><i class="Hui-iconfont">&#xe600;</i> 新增菜单</a>
    </div>
    <table class="table table-border table-bordered table-bg table-striped">
        <thead>
        <tr class="text-c">
            <th width="col-sm-1">序号</th>
            <th width="col-sm-2">菜单编码</th>
            <th width="col-sm-2">菜单名称</th>
            <th width="col-sm-2">操作</th>
        </tr>
        </thead>
        <tbody>
        <c:choose>
        	<c:when test="${not empty children }">
        		<c:forEach items="${children }" var="child" varStatus="status">
        			<tr class="text-c">
			            <td>${child.rank }</td>
			            <td>${child.code }</td>
			            <td>${child.name }</td>
			            <td class="td-manage">
			                <a title="编辑" href="javascript:;" onclick="admin_edit('菜单编辑','${ctx}/back/menu/addOrEdit?id=${child.id }','500','520')" class="ml-5" ><i class="Hui-iconfont i-font-size-mid">&#xe6df;</i></a>
			                <a title="删除" href="javascript:;" onclick="admin_del(this,'${ctx}/back/menu/ajax/delete?id=${child.id }','${ctx }')" class="ml-5" ><i class="Hui-iconfont i-font-size-mid">&#xe6e2;</i></a>
			            </td>
			        </tr>
        		</c:forEach>
        	</c:when>
        	<c:otherwise>
        		<tr class="text-c">
            		<td colspan="4">暂无子菜单...</td>
            	</tr>	
        	</c:otherwise>
        </c:choose>
        </tbody>
    </table>
</div>
</body>
</html>