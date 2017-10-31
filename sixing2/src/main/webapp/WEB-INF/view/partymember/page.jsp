<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ include file="../common/taglibs.jsp" %>

<!DOCTYPE HTML>
<html>
<head>
	<%@ include file="../common/head.jsp" %>
	<title>党员信息管理</title>
	<style>
		body{
			min-width: 1000px;
		}
	</style>
</head>
<body>
    <nav class="breadcrumb">
        <i class="Hui-iconfont">&#xe67f;</i> 首页 <span class="c-gray en">&gt;</span> 组织管理
        <span  class="c-error"><span class="c-gray en">&gt;</span> 党员信息管理 </span>
        <a class="btn btn-success radius r" href="javascript:location.replace(location.href);" title="刷新" ><i class="Hui-iconfont">&#xe68f;</i></a>
    </nav>
    <div class="page-container">
    	<form action="${ctx }/back/partymember/page" method="post">
    	<input type="hidden" name="pageNo" value="${page.pageNo }" id="pageNo">
        <div class="operation_column">
            <span class="va-m">姓名：</span>
            <span class="search-box"><i class="Hui-iconfont search_icon">&#xe709;</i><input type="text" class="input-text radius select_user" placeholder="请输入姓名关键字" name="nickname" value=${form.nickname }></span>
            <c:if test="${sessionScope.get('logedOperator').org.level != branch}">
            <span class="va-m">组织关系：</span>
			<span class="search-box mr-20" onclick="tree_layer(this,'${ctx}')" id="change-to">
			    <i class="Hui-iconfont search_icon">&#xe709;</i>
			    <input type="text" name="orgName" value="${form.orgName }" class="input-text radius select_user" placeholder="请选择组织关系" readonly>
                <input type="hidden" name="orgId" value="${form.orgId }" >
		    </span>
            </c:if>
            <button type="submit" class="btn btn-danger radius">搜索</button>
        </div>
        </form>
        <form id="deleteMore">
        <table class="table table-border table-bordered table-bg table-striped">
            <thead>
            <tr class="text-c">
                <th width="col-sm-1">序号</th>
                <th width="col-sm-1">头像</th>
                <th width="col-sm-1">姓名</th>
                <th width="col-sm-1">性别</th>
                <th width="col-sm-1">手机号码</th>
                <th width="col-sm-1">籍贯</th>
                <th width="col-sm-1">民族</th>
                <th width="col-sm-1">岗位</th>
                <th width="col-sm-1">学历</th>
                <th width="col-sm-2">操作</th>
            </tr>
            </thead>
            <tbody>
            	<c:choose>
		        	<c:when test="${not empty page.list }">
		        		<c:forEach items="${page.list }" var="user" varStatus="status">
		        			<tr class="text-c">
                                <td>${status.count }</td>
                                <td><c:if test="${not empty user.image }"><img src="${ctx }${user.image }" alt="加载失败" width="80px" height="80px"></c:if></td>
					            <td>${user.nickname }</td>
                                <td>${user.sex }</td>
                                <td>${user.phone }</td>
                                <td>${user.nativePlace }</td>
                                <td>${user.nation }</td>
                                <td>${user.job }</td>
                                <td>${user.education.desc }</td>
					            <td class="td-manage">
						            <a title="编辑" href="javascript:;" onclick="admin_edit('用户修改','${ctx}/back/partymember/addOrEdit?id=${user.id }','1000','600')" class="ml-5" ><i class="Hui-iconfont i-font-size-mid">&#xe6df;</i></a>
                    			</td>
					        </tr>
		        		</c:forEach>
		        	</c:when>
		        	<c:otherwise>
		        		<tr class="text-c">
		            		<td colspan="10">暂无党员信息...</td>
		            	</tr>	
		        	</c:otherwise>
		        </c:choose>  
            </tbody>
        </table>
        </form>
        <div class="clearfix">
            <div class="pagers f-r mr-20" >共 ${page.totalCount } 条记录</div>
            <div id="divPager"  class="pagers f-r"></div>
        </div>

    </div>
    <script type="text/javascript">
   		demo(${page.totalPage},${page.pageNo});
    </script>
</body>
</html>