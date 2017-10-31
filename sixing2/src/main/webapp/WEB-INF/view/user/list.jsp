<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ include file="../common/taglibs.jsp" %>

<!DOCTYPE HTML>
<html>
<head>
	<%@ include file="../common/head.jsp" %>
	<title>APP用户管理</title>
	<style>
		body{
			min-width: 1000px;
		}
	</style>
</head>
<body>
    <nav class="breadcrumb">
        <i class="Hui-iconfont">&#xe67f;</i> 首页 <span class="c-gray en">&gt;</span> 用户管理
        <span  class="c-error"><span class="c-gray en">&gt;</span> APP用户管理 </span>
        <a class="btn btn-success radius r" href="javascript:location.replace(location.href);" title="刷新" ><i class="Hui-iconfont">&#xe68f;</i></a>
    </nav>
    <div class="page-container">
    	<form action="${ctx }/back/user/list" method="post" id="form-submit">
    	<input type="hidden" name="pageNo" value="${page.pageNo }" id="pageNo">
        <div class="operation_column">
            <span class="va-m">姓名：</span><span class="search-box"><i class="Hui-iconfont search_icon">&#xe709;</i><input type="text" class="input-text radius select_user" placeholder="请输入姓名关键字" name="nickname" value=${form.nickname }></span>
            <c:if test="${sessionScope.get('logedOperator').org.level != branch}">
                <span class="va-m">组织关系：</span>
                    <span class="search-box mr-20" onclick="tree_layer(this,'${ctx}')" id="change-to">
                    <i class="Hui-iconfont search_icon">&#xe709;</i>
                    <input type="text" name="orgName" value="${form.orgName }" class="input-text radius select_user" placeholder="请选择组织关系" readonly style="width: 325px">
                    <input type="hidden" name="orgId" value="${form.orgId }" >
                </span>
            </c:if>
            <button type="submit" class="btn btn-danger radius">搜索</button>
            <a href="javascript:;" onclick="admin_add('新增用户','${ctx }/back/user/addOrEdit','1000','600')" class="btn btn-danger-outline radius f-r"><i class="Hui-iconfont">&#xe600;</i> 新增用户</a>
        </div>
        </form>
        <c:if test="${sessionScope.get('logedOperator').org.level != branch}">
        <div class="btn_column radius">
            <button type="button" class="btn btn-danger radius" onclick="admin_add('批量生成用户','${ctx }/back/user/addBatch','620','300')">批量导入</button>
            <a href="${ctx }/file/users.xlsx" class="btn btn-danger radius"><i class="Hui-iconfont">&#xe641;</i> 下载模板</a>
            <a class="btn btn-success radius r" style="line-height:1.6em;margin-top:3px;" href="${ctx }/back/user/export?orgId=${form.orgId }" title="批量导出" >批量导出</a>
        </div>
        </c:if>
        <form id="deleteMore">
        <table class="table table-border table-bordered table-bg table-striped">
            <thead>
            <tr class="text-c">
                <th width="col-sm-1">序号</th>
                <th width="col-sm-1">组织关系</th>
                <th width="col-sm-1">头像</th>
                <th width="col-sm-1">姓名</th>
                <th width="col-sm-1">用户名</th>
                <th width="col-sm-1">积分</th>
                <th width="col-sm-2">操作</th>
            </tr>
            </thead>
            <tbody>
            	<c:choose>
		        	<c:when test="${not empty page.list }">
		        		<c:forEach items="${page.list }" var="user" varStatus="status">
		        			<tr class="text-c">
                                <td>${status.count }</td>
                                <td>
                                    <c:forEach items="${user.orgs }" var="org" varStatus="s">
                                        ${org.name }
                                        <c:if test="${!s.last}">、</c:if>
                                     </c:forEach>
                                </td>
                                <td><c:if test="${not empty user.image }"><img src="${ctx }${user.image }" alt="加载失败" width="80px" height="80px"></c:if></td>
					            <td>${user.nickname }</td>
                                <td>${user.account }</td>
                                <td>${user.points }</td>
					            <td class="td-manage">
                                    <c:if test="${not empty level}">
                                        <a title="向上排序" href="javascript:;" onclick="changeRank('${ctx}/back/user/rank?id=${user.id }&type=1&orgId=${form.orgId }&level=${level}')" class="ml-5" ><i class="Hui-iconfont i-font-size-mid">&#xe6dc;</i></a>
                                        <a title="向下排序" href="javascript:;" onclick="changeRank('${ctx}/back/user/rank?id=${user.id }&type=2&orgId=${form.orgId }&level=${level}')" class="ml-5" ><i class="Hui-iconfont i-font-size-mid">&#xe6de;</i></a>
                                    </c:if>
                                    <a title="编辑" href="javascript:;" onclick="admin_edit('修改用户${user.nickname}的信息','${ctx}/back/user/addOrEdit?id=${user.id }','1000','600')" class="ml-5" ><i class="Hui-iconfont i-font-size-mid">&#xe6df;</i></a>
                                    <a title="重置密码" href="javascript:;" onclick="admin_edit('重置密码','${ctx}/back/user/resetPwd?id=${user.id }','480','300')" class="ml-5" ><i class="Hui-iconfont i-font-size-mid">&#xe63f;</i></a>
                                    <a title="删除" href="javascript:;" onclick="admindelall('${ctx}/back/user/ajax/delete?id=${user.id }',this)" class="ml-5" ><i class="Hui-iconfont i-font-size-mid">&#xe6e2;</i></a>
                    			</td>
					        </tr>
		        		</c:forEach>
		        	</c:when>
		        	<c:otherwise>
		        		<tr class="text-c">
		            		<td colspan="7">暂无用户信息...</td>
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
        function admindelall(url,obj){
            layer.confirm('确认删除该用户及其所有相关信息？',function(index){
                //此处请求后台程序，下方是成功后的前台处理……
                $.postJSON(url,null,function(data) {
                    $(obj).parents("tr").remove();
                });
            });
        }
    </script>
</body>
</html>