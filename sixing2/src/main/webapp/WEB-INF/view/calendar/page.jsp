<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ include file="../common/taglibs.jsp" %>

<!DOCTYPE HTML>
<html>
<head>
	<%@ include file="../common/head.jsp" %>
	<title>党务日历管理</title>
	<style>
		body{
			min-width: 1000px;
		}
	</style>
</head>
<body>
	<nav class="breadcrumb">
		<i class="Hui-iconfont">&#xe67f;</i> 首页 <span class="c-gray en">&gt;</span> 党务日历管理 <span  class="c-error"><span class="c-gray en">&gt;</span> 党务日历管理 </span>
		<a class="btn btn-success radius r" style="line-height:1.6em;margin-top:3px" href="javascript:location.replace(location.href);" title="刷新" ><i class="Hui-iconfont">&#xe68f;</i></a>
	</nav>
	<div class="page-container">
		<form action="${ctx }/back/calendar/page" method="post">
		<input type="hidden" name="pageNo" value="${page.pageNo }" id="pageNo">
		<div class="operation_column">
			<span class="va-m">主题：</span>
            <input type="text" class="input-text radius select_user" placeholder="请输入主题关键字" name="subject" value="${form.subject }"></span>
			<button type="submit" class="btn btn-danger radius">搜索</button>
			<a href="javascript:;" onclick="admin_add('新增党务日历','${ctx }/back/calendar/addOrEdit','1000','600')" class="btn btn-danger-outline radius f-r"><i class="Hui-iconfont">&#xe600;</i> 新增</a>
		</div>
		</form>
		<form id="deleteMore">
		<table class="table table-border table-bordered table-bg table-striped">
			<thead>
				<tr class="text-c">
					<th width="col-sm-1">序号</th>
                    <th width="col-sm-1">主题</th>
					<th width="col-sm-2">指派时间</th>
					<th width="col-sm-2">操作</th>
				</tr>
			</thead>
			<tbody>
				<c:choose>
		        	<c:when test="${not empty page.list }">
		        		<c:forEach items="${page.list }" var="calendar" varStatus="status">
		        			<tr class="text-c">
								<td>${status.count }</td>
                                <td>${calendar.subject }</td>
								<td>${calendar.doDate }</td>
					            <td class="td-manage">
						            <a title="详情" href="javascript:;" onclick="admin_edit('详情','${ctx }/back/calendar/info?id=${calendar.id }',1000,600)" class="ml-5" style="text-decoration:none"><i class="Hui-iconfont i-font-size-mid">&#xe616;</i></a>
									<a title="编辑指派对象" href="javascript:;" onclick="admin_edit('编辑','${ctx }/back/calendar/userPage?calendarId=${calendar.id }',1000,600)" class="ml-5" style="text-decoration:none"><i class="Hui-iconfont i-font-size-mid">&#xe62b;</i></a>
									<a title="删除" href="javascript:;" onclick="admindel('${ctx }/back/calendar/ajax/delete?id=${calendar.id }',this)" class="ml-5" style="text-decoration:none"><i class="Hui-iconfont i-font-size-mid">&#xe6e2;</i></a>
								</td>
					        </tr>
		        		</c:forEach>
		        	</c:when>
		        	<c:otherwise>
		        		<tr class="text-c">
		            		<td colspan="6">暂无党务日历...</td>
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