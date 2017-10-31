<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ include file="../common/taglibs.jsp" %>

<!DOCTYPE HTML>
<html>
<head>
	<%@ include file="../common/head.jsp" %>
	<title>意见反馈</title>
	<style>
		body{
			min-width: 1000px;
		}
	</style>
</head>
<body>
	<nav class="breadcrumb">
		<i class="Hui-iconfont">&#xe67f;</i> 首页 <span class="c-gray en">&gt;</span> 反馈汇报管理 <span  class="c-error"><span class="c-gray en">&gt;</span> 意见反馈管理 </span>
		<a class="btn btn-success radius r" style="line-height:1.6em;margin-top:3px" href="javascript:location.replace(location.href);" title="刷新" ><i class="Hui-iconfont">&#xe68f;</i></a>
	</nav>
	<div class="page-container">
		<form action="${ctx }/back/advice/list?dictId=6" method="post">
		<input type="hidden" name="pageNo" value="${page.pageNo }" id="pageNo">
		<div class="operation_column">
			<span class="va-m">反馈内容：</span><span class="search-box"><i class="Hui-iconfont search_icon">&#xe709;</i>
			<input type="text" class="input-text radius select_user" placeholder="请输入反馈内容关键字" name="content" value="${form.content }"></span>
			<%--<span class="search-box mr-20">--%>
				<%--<select class="select input-text radius va-m" style="min-width: 100px" name="orgId">--%>
                    <%--<option value="">请选择组织关系</option>--%>
					<%--<c:forEach items="${orgs }" var="orgs">--%>
						<%--<option value="${orgs.id }" <c:if test="${orgs.id == form.orgId }">selected</c:if>>${orgs.name }</option>--%>
					<%--</c:forEach>--%>
				<%--</select>--%>
			<%--</span>--%>
			<%--<span class="va-m">反馈时间：</span><span class="search-box"><i class="Hui-iconfont search_icon">&#xe709;</i>--%>
			<%--<input type="text" class="input-text radius select_user" placeholder="请输入开始时间" readonly onclick="WdatePicker({lang:'zh-cn',dateFmt:'yyyy-MM-dd',maxDate:'#F{$dp.$D(\'endDate\')||\'new Date()\'}'})" id="startDate" style="margin-right: 0;" name="startTime" value="${form.startTime }"></span>--%>
            <%-----%>
            <%--<span class="search-box"><i class="Hui-iconfont search_icon">&#xe709;</i>--%>
			<%--<input type="text" class="input-text radius select_user" placeholder="请输入结束时间" readonly onclick="WdatePicker({lang:'zh-cn',dateFmt:'yyyy-MM-dd',minDate:'#F{$dp.$D(\'startDate\')}',maxDate:new Date()})" id="endDate" name="endTime" value="${form.endTime }"></span>--%>
            <button type="submit" class="btn btn-danger radius">搜索</button>
			</div>
		</form>
		<form id="deleteMore">
		<table class="table table-border table-bordered table-bg table-striped">
			<thead>
				<tr class="text-c">
					<th width="col-sm-1">序号</th>
					<th width="col-sm-1">反馈人</th>
					<th width="col-sm-1">组织关系</th>
					<th width="col-sm-3">反馈内容</th>
					<th width="col-sm-1">反馈时间</th>
					<th width="col-sm-1">操作</th>
				</tr>
			</thead>
			<tbody>
				<c:choose>
		        	<c:when test="${not empty page.list }">
		        		<c:forEach items="${page.list }" var="advice" varStatus="status">
		        			<tr class="text-c">
								<td>${status.count }</td>
					            <td>${advice.user.nickname }</td>
								<td><c:forEach items="${advice.user.orgs }" var="org" varStatus="status">
                                    ${org.level.desc }<c:if test="${!status.last}">、</c:if>
                                </c:forEach></td>
								<td class="text-l" style="width: 500px">${advice.content }</td>
								<td>${fn:substring(advice.createTime, 0, 16)}</td>
					            <td class="td-manage">
						            <a title="删除" href="javascript:;" onclick="admindel('${ctx }/back/advice/ajax/delete?id=${advice.id }',this)" class="ml-5" style="text-decoration:none"><i class="Hui-iconfont i-font-size-mid">&#xe6e2;</i></a>
								</td>
					        </tr>
		        		</c:forEach>
		        	</c:when>
		        	<c:otherwise>
		        		<tr class="text-c">
		            		<td colspan="6">暂无意见反馈...</td>
		            	</tr>	
		        	</c:otherwise>
		        </c:choose>
			</tbody>
		</table>
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