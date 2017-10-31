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
	<nav class="breadcrumb">
		<i class="Hui-iconfont">&#xe67f;</i> 首页 <span class="c-gray en">&gt;</span> 系统管理 <span  class="c-error"><span class="c-gray en">&gt;</span> 权限管理 </span>
		<a class="btn btn-success radius r" href="javascript:location.replace(location.href);" title="刷新" ><i class="Hui-iconfont">&#xe68f;</i></a>
	</nav>
	<div class="page-container">
		<form action="${ctx }/back/role/list" method="post">
		<input type="hidden" name="pageNo" value="${page.pageNo }" id="pageNo">
		<div class="operation_column">
			<span class="va-m">角色名：</span><span class="search-box"><i class="Hui-iconfont search_icon">&#xe709;</i><input type="text" class="input-text radius select_user" placeholder="请输入相关角色" name="name" value="${name }"></span><button type="submit" class="btn btn-danger radius">搜索</button><a href="javascript:;" onclick="admin_add('新增角色','${ctx}/back/role/addOrEdit','500','350')" class="btn btn-danger-outline radius f-r"><i class="Hui-iconfont">&#xe600;</i> 新增角色</a>
		</div>
		</form>
		<table class="table table-border table-bordered table-bg table-striped">
			<thead>
				<tr class="text-c">
					<th width="col-sm-1">序号</th>
					<th width="col-sm-2">角色名称</th>
					<th width="col-sm-2">操作</th>
				</tr>
			</thead>
			<tbody>
				<c:choose>
		        	<c:when test="${not empty page.list }">
		        		<c:forEach items="${page.list }" var="role" varStatus="status">
		        			<tr class="text-c">
					            <td>${status.count }</td>
					            <td>${role.name }</td>
					            <td class="td-manage">
									<a title="修改" href="javascript:;" onclick="admin_edit('角色修改','${ctx}/back/role/addOrEdit?id=${role.id }','500','350')" class="ml-5" ><i class="i-font-size-mid Hui-iconfont">&#xe692;</i></a>
									<a title="权限编辑" href="javascript:;" onclick="admin_edit('权限编辑','${ctx}/back/role/editRoleMenu?id=${role.id }','430','700')" class="ml-5" ><i class="i-font-size-mid Hui-iconfont">&#xe6df;</i></a>
									<c:if test="${role.id!=1}" >
										<a title="删除" href="javascript:;" onclick="admindel('${ctx}/back/role/ajax/delete?id=${role.id }',this)" class="ml-5" ><i class="i-font-size-mid Hui-iconfont">&#xe6e2;</i></a>
									</c:if>
								</td>
					        </tr>
		        		</c:forEach>
		        	</c:when>
		        	<c:otherwise>
		        		<tr class="text-c">
		            		<td colspan="4">暂无角色...</td>
		            	</tr>	
		        	</c:otherwise>
		        </c:choose>
			</tbody>
		</table>
		<div class="clearfix">
			<div class="pagers f-r mr-20" style="padding-top: 3px">共 ${page.totalCount } 条记录</div>
			<div id="divPager"  class="pagers f-r"></div>
		</div>
	</div>
	<script type="text/javascript">
		demo(${page.totalPage},${page.pageNo});
	</script>
</body>
</html>