<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ include file="../common/taglibs.jsp" %>

<!DOCTYPE HTML>
<html>
<head>
	<%@ include file="../common/head.jsp" %>
	<title>工作展示</title>
	<style>
		body{
			min-width: 1000px;
		}
	</style>
</head>
<body>
	<nav class="breadcrumb">
		<i class="Hui-iconfont">&#xe67f;</i> 首页 <span class="c-gray en">&gt;</span> 展示汇报管理 <span  class="c-error"><span class="c-gray en">&gt;</span> 工作展示管理 </span>
		<a class="btn btn-success radius r" style="line-height:1.6em;margin-top:3px" href="javascript:location.replace(location.href);" title="刷新" ><i class="Hui-iconfont">&#xe68f;</i></a>
	</nav>
	<div class="page-container">
		<form action="${ctx }/back/job/list?dictId=6" method="post">
		<input type="hidden" name="pageNo" value="${page.pageNo }" id="pageNo">
		<div class="operation_column">
			<span class="va-m">标题：</span><span class="search-box"><i class="Hui-iconfont search_icon">&#xe709;</i>
			<input type="text" class="input-text radius select_user" placeholder="请输入主题关键字" name="subject" value="${form.subject }" style="width: 140px"></span>
			<%--<span class="va-m">组织机构：</span>--%>
			<%--<span class="search-box mr-20" onclick="tree_layer(this,'${ctx}')" id="change-to">--%>
			    <%--<i class="Hui-iconfont search_icon">&#xe709;</i>--%>
			    <%--<input type="text" name="orgName" value="${form.orgName }" class="input-text radius select_user" placeholder="请选择组织机构" style="width: 140px" readonly>--%>
                <%--<input type="hidden" name="orgId" value="${form.orgId }" >--%>
		    <%--</span>--%>
			<span class="va-m">活动时间：</span><span class="search-box"><i class="Hui-iconfont search_icon">&#xe709;</i>
			<input type="text" class="input-text radius select_user" placeholder="请输入开始时间" readonly onclick="WdatePicker({lang:'zh-cn',dateFmt:'yyyy-MM-dd',maxDate:'#F{$dp.$D(\'endDate\')||\'new Date()\'}'})" id="startDate" style="margin-right: 0;width: 130px" name="startTime" value="${form.startTime }"></span>
            -
            <span class="search-box"><i class="Hui-iconfont search_icon">&#xe709;</i>
			<input type="text" class="input-text radius select_user" placeholder="请输入结束时间" readonly onclick="WdatePicker({lang:'zh-cn',dateFmt:'yyyy-MM-dd',minDate:'#F{$dp.$D(\'startDate\')}',maxDate:new Date()})" id="endDate" style="width: 130px" name="endTime" value="${form.endTime }"></span>
            <button type="submit" class="btn btn-danger radius">搜索</button>
            <a href="javascript:;" onclick="admin_add('新增工作展示','${ctx }/back/job/addOrEdit','1000','600')"
               class="btn btn-danger-outline radius f-r"><i class="Hui-iconfont">&#xe600;</i> 新增</a>
        </div>
		</form>

		<form id="deleteMore">
		<table class="table table-border table-bordered table-bg table-striped">
			<thead>
				<tr class="text-c">
					<th width="col-sm-1"><input type="checkbox" /></th>
					<th width="col-sm-1">序号</th>
					<th width="col-sm-1">展示人</th>
					<%--<th width="col-sm-1">组织关系</th>--%>
					<th width="col-sm-1">活动主题</th>
					<th width="col-sm-2">活动时间</th>
					<th width="col-sm-2">操作</th>
				</tr>
			</thead>
			<tbody>
				<c:choose>
		        	<c:when test="${not empty page.list }">
		        		<c:forEach items="${page.list }" var="job" varStatus="status">
		        			<tr class="text-c">
					            <td><input type="checkbox" name="ids" value="${job.id }"/></td>
								<td>${status.count }</td>
					            <td><c:choose>
                                    <c:when test="${not empty job.user }">
                                        ${job.user.nickname }</td>
                                    </c:when>
                                    <c:otherwise>
                                        <c:forEach items="${orgList }" var="org">
                                            <c:if test="${org.id == job.orgId }">${org.name }</c:if>
                                        </c:forEach>
                                    </c:otherwise>
                                </c:choose>
								<%--<td><c:forEach items="${job.user.orgs }" var="org" varStatus="status">--%>
                                    <%--${org.level.desc }<c:if test="${!status.last}">、</c:if>--%>
                                <%--</c:forEach></td>--%>
								<td style="width: 500px">${job.subject }</td>
								<td><fmt:formatDate value="${job.activityDate }" pattern="yyyy-MM-dd HH:mm"/></td>
					            <td class="td-manage">
						            <a title="详情" href="javascript:;" onclick="admin_edit('详情','${ctx }/back/job/details?id=${job.id }',800,500)" class="ml-5" style="text-decoration:none"><i class="Hui-iconfont i-font-size-mid">&#xe616;</i></a>
                                    <a title="编辑" href="javascript:;" onclick="admin_edit('编辑','${ctx }/back/job/addOrEdit?id=${job.id }',1000,600)" class="ml-5" style="text-decoration:none"><i class="Hui-iconfont i-font-size-mid">&#xe6df;</i></a>
                                    <a title="导出Word" href="${ctx }/back/job/exportword?id=${job.id }" ><i class="Hui-iconfont i-font-size-mid">&#xe644;</i></a>
                                    <a title="删除" href="javascript:;" onclick="admindel('${ctx }/back/job/ajax/delete?id=${job.id }',this)" class="ml-5" style="text-decoration:none"><i class="Hui-iconfont i-font-size-mid">&#xe6e2;</i></a>
								</td>
					        </tr>
		        		</c:forEach>
		        	</c:when>
		        	<c:otherwise>
		        		<tr class="text-c">
		            		<td colspan="6">暂无工作展示...</td>
		            	</tr>	
		        	</c:otherwise>
		        </c:choose>
			</tbody>
		</table>
            <div class="btn_column radius">
                <button type="button" class="btn btn-danger radius" onclick="deleteMore('${ctx}','${ctx }/back/job/ajax/deleteBatch')">批量删除</button>
            </div>
		</form>
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