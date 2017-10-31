<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ include file="../common/taglibs.jsp" %>

<!DOCTYPE HTML>
<html>
<head>
    <%@ include file="../common/head.jsp" %>
    <title>系统管理-操作员管理</title>
    <style>
        body{
            min-width: 1000px;
        }
    </style>
</head>
<body>
    <nav class="breadcrumb">
        <i class="Hui-iconfont">&#xe67f;</i> 首页 <span class="c-gray en">&gt;</span> 系统管理 <span  class="c-error"><span class="c-gray en">&gt;</span> 操作员管理 </span>
        <a class="btn btn-success radius r" href="javascript:location.replace(location.href);" title="刷新" ><i class="Hui-iconfont">&#xe68f;</i></a>
    </nav>
    <div class="page-container">
    	<form action="${ctx }/back/operator/list" method="post">
		<input type="hidden" name="pageNo" value="${page.pageNo }" id="pageNo">
        <div class="operation_column">
            <span class="va-m">姓名：</span><span class="search-box"><i class="Hui-iconfont search_icon">&#xe709;</i><input type="text" class="input-text radius select_user" placeholder="请输入姓名" name="name" value="${form.name }"></span>
            <c:if test="${sessionScope.get('logedOperator').org.level != branch}">
                <span class="va-m">组织关系：</span>
                    <span class="search-box mr-20" onclick="tree_layer(this,'${ctx}')" id="change-to">
                    <i class="Hui-iconfont search_icon">&#xe709;</i>
                    <input type="text" name="orgName" value="${form.orgName }" class="input-text radius select_user" placeholder="请选择组织关系" readonly style="width: 325px">
                    <input type="hidden" name="orgId" value="${form.orgId }" >
                </span>
            </c:if>
            <button type="submit" class="btn btn-danger radius">搜索</button>
            <a href="javascript:;" onclick="admin_add('新增账号','${ctx}/back/operator/addOrEdit','500','600')" class="btn btn-danger-outline radius f-r"><i class="Hui-iconfont">&#xe600;</i> 新增账号</a>
        </div>
        </form>
        <table class="table table-border table-bordered table-bg table-striped">
            <thead>
            <tr class="text-c">
                <th width="col-sm-1">序号</th>
                <th width="col-sm-2">账号</th>
                <th width="col-sm-2">姓名</th>
                <th width="col-sm-2">角色</th>
                <th width="col-sm-2">组织</th>
                <th width="col-sm-2">操作</th>
            </tr>
            </thead>
            <tbody>
            	<c:choose>
		        	<c:when test="${not empty page.list }">
		        		<c:forEach items="${page.list }" var="operator" varStatus="status">
		        			<tr class="text-c">
					            <td>${status.count }</td>
					            <td>${operator.account }</td>
					            <td>${operator.name }</td>
					            <td>${operator.role.name }</td>
                                <td>${operator.org.name }</td>
					            <td class="td-manage">
									<a title="编辑" href="javascript:;" onclick="admin_edit('管理员账号修改','${ctx}/back/operator/addOrEdit?id=${operator.id }','500','600')" class="ml-5" ><i class="Hui-iconfont i-font-size-mid">&#xe6df;</i></a>
									<a title="重置密码" href="javascript:;" onclick="admin_edit('重置密码','${ctx}/back/operator/resetPwd?id=${operator.id }','480','300')" class="ml-5" ><i class="Hui-iconfont i-font-size-mid">&#xe63f;</i></a>
                    				<c:if test="${not (operator.id==sessionScope.logedOperator.id) }">
                    				<a title="删除" href="javascript:;" onclick="admindel('${ctx}/back/operator/ajax/delete?id=${operator.id }',this)" class="ml-5" ><i class="Hui-iconfont i-font-size-mid">&#xe6e2;</i></a>
                    				</c:if>
                    			</td>
					        </tr>
		        		</c:forEach>
		        	</c:when>
		        	<c:otherwise>
		        		<tr class="text-c">
		            		<td colspan="5">暂无账号...</td>
		            	</tr>	
		        	</c:otherwise>
		        </c:choose>  
            </tbody>
        </table>
        <div class="clearfix">
            <div class="pagers f-r mr-20 total-page">共 ${page.totalCount } 条记录</div>
            <div id="divPager"  class="pagers f-r"></div>
        </div>
    </div>
    <script type="text/javascript">
		demo(${page.totalPage},${page.pageNo});
    </script>
</body>
</html>