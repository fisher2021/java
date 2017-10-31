<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ include file="../common/taglibs.jsp" %>

<!DOCTYPE HTML>
<html>
<head>
	<%@ include file="../common/head.jsp" %>
	<title>会议室</title>
	<style>
		body{
			min-width: 1000px;
		}
	</style>
</head>
<body>
	<nav class="breadcrumb">
		<i class="Hui-iconfont">&#xe67f;</i> 首页 <span class="c-gray en">&gt;</span> 用户管理 <span  class="c-error"><span class="c-gray en">&gt;</span> 会议室管理 </span>
		<a class="btn btn-success radius r" style="line-height:1.6em;margin-top:3px" href="javascript:location.replace(location.href);" title="刷新" ><i class="Hui-iconfont">&#xe68f;</i></a>
	</nav>
	<div class="page-container">
		<form action="${ctx}/back/room/page" method="post">
		<input type="hidden" name="pageNo" value="${page.pageNo }" id="pageNo">
		<div class="operation_column">
            <c:if test="${logedOperator.org.level != 'PARTY_BRANCH'}">
            <span class="va-m">会议室名称：</span><span class="search-box"><i class="Hui-iconfont search_icon">&#xe709;</i>
			<input type="text" class="input-text radius select_user" placeholder="请输入关键字" name="name" value="${form.name }" style="width: 140px"></span>
            <span class="va-m">组织关系：</span>
            <span class="search-box mr-20">
                <select class="select input-text radius va-m" style="width: 200px" name="org" id="org">
                    <option value="">全部</option>
                    <c:forEach items="${orgs }" var="org">
                        <c:if test="${org.level.name() eq 'PARTY_BRANCH'}">
                            <option value="${org.id }" <c:if test="${org.id == form.org }">selected</c:if>>${org.name }</option>
                        </c:if>
                    </c:forEach>
                </select>
            </span>
			<button type="submit" class="btn btn-danger radius">搜索</button>
            </c:if>
			<a href="javascript:;" onclick="admin_add('新增会议室','${ctx }/back/room/addOrEdit','600','430')" class="btn btn-danger-outline radius f-r"><i class="Hui-iconfont">&#xe600;</i> 新增会议室</a>
		</div>
		</form>
		<table class="table table-border table-bordered table-bg table-striped">
			<thead>
				<tr class="text-c">
					<th width="col-sm-1">序号</th>
					<th width="col-sm-1">会议室名称</th>
                    <th width="col-sm-1" style="width: 60%">成员</th>
					<th width="col-sm-2">操作</th>
				</tr>
			</thead>
			<tbody>
				<c:choose>
		        	<c:when test="${not empty page.list }">
		        		<c:forEach items="${page.list }" var="room" varStatus="status">
		        			<tr class="text-c">
								<td>${status.count }</td>
								<td>${room.org.name}${room.name }</td>
                                <td>
                                    <c:forEach items="${room.user }" var="user" varStatus="status">
                                        <c:if test="${empty user.deleted||!user.deleted}">
                                            ${user.nickname }
                                            <c:if test="${!status.last}">、</c:if>
                                        </c:if>
                                    </c:forEach>
                                </td>
					            <td class="td-manage">
									<a title="编辑会议室成员" href="javascript:;" onclick="admin_edit('编辑 ${room.name} 会议室成员','${ctx }/back/room/userPage?roomId=${room.id }',1000,600)" class="ml-5" style="text-decoration:none"><i class="Hui-iconfont i-font-size-mid">&#xe62b;</i></a>
									<%--<a title="编辑会议室" href="javascript:;" onclick="admin_add('编辑会议室','${ctx }/back/room/addOrEdit?id=${room.id }','600','400')" class="ml-5" style="text-decoration:none"><i class="Hui-iconfont i-font-size-mid">&#xe6ab;</i></a>--%>
                                    <c:if test="${room.type == 'DXZH'}">
                                        <a title="删除" href="javascript:;" onclick="admindel('${ctx }/back/room/ajax/delete?id=${room.id }&groupId=${room.groupId }',this)" class="ml-5" style="text-decoration:none"><i class="Hui-iconfont i-font-size-mid">&#xe6e2;</i></a>
                                    </c:if>
								</td>
					        </tr>
		        		</c:forEach>
		        	</c:when>
		        	<c:otherwise>
		        		<tr class="text-c">
		            		<td colspan="6">暂无会议室...</td>
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