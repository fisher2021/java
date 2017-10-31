<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ include file="../common/taglibs.jsp" %>

<!DOCTYPE HTML>
<html>
<head>
	<%@ include file="../common/head.jsp" %>
	<title>民主评议</title>
	<style>
		body{
			min-width: 1000px;
		}
	</style>
</head>
<body>
	<nav class="breadcrumb">
		<i class="Hui-iconfont">&#xe67f;</i> 首页 <span class="c-gray en">&gt;</span> 评议投票管理 <span  class="c-error">
		<span class="c-gray en">&gt;</span> 民主评议管理 </span>
		<a class="btn btn-success radius r" style="line-height:1.6em;margin-top:3px" href="javascript:location.replace(location.href);" title="刷新" ><i class="Hui-iconfont">&#xe68f;</i></a>
	</nav>
	<div class="page-container">
		<form action="${ctx }/back/appr/page" method="post">
		<input type="hidden" name="pageNo" value="${page.pageNo }" id="pageNo">
		<div class="operation_column">
			<span class="va-m">评议名称：</span><span class="search-box"><i class="Hui-iconfont search_icon">&#xe709;</i>
			<input type="text" class="input-text radius select_user" placeholder="请输入评议名称关键字" name="title" value="${form.title }" ></span>
			<span class="va-m">创建时间：</span><span class="search-box"><i class="Hui-iconfont search_icon">&#xe709;</i>
			<input type="text" class="input-text radius select_user" placeholder="请输入开始时间" readonly onclick="WdatePicker({lang:'zh-cn',dateFmt:'yyyy-MM-dd',maxDate:'#F{$dp.$D(\'endDate\')||\'new Date()\'}'})" id="startDate" style="margin-right: 0;width: 130px" name="startTime" value="${form.startTime }"></span>
            -
            <span class="search-box"><i class="Hui-iconfont search_icon">&#xe709;</i>
			<input type="text" class="input-text radius select_user" placeholder="请输入结束时间" readonly onclick="WdatePicker({lang:'zh-cn',dateFmt:'yyyy-MM-dd',minDate:'#F{$dp.$D(\'startDate\')}',maxDate:new Date()})" id="endDate" style="width: 130px" name="endTime" value="${form.endTime }"></span>
            <button type="submit" class="btn btn-danger radius">搜索</button>
			<a href="javascript:;" onclick="admin_add('新增民主评议','${ctx }/back/appr/addOrEdit',1000,600)" class="btn btn-danger-outline radius f-r"><i class="Hui-iconfont">&#xe600;</i> 新增</a>
		</div>
		</form>
		<%--<div class="btn_column radius">--%>
			<%--<button type="button" class="btn btn-danger radius" onclick="deleteMore('${ctx}','${ctx }/back/appr/ajax/deleteBatch')">批量删除</button>--%>
		<%--</div>--%>
		<form id="deleteMore">
		<table class="table table-border table-bordered table-bg table-striped">
			<thead>
				<tr class="text-c">
					<th width="col-sm-1">编号</th>
					<th width="col-sm-1">评议名称</th>
					<th width="col-sm-1">被评人</th>
					<th width="col-sm-2">简介</th>
					<th width="col-sm-2">创建时间</th>
					<th width="col-sm-2">操作</th>
				</tr>
			</thead>
			<tbody>
				<c:choose>
		        	<c:when test="${not empty page.list }">
		        		<c:forEach items="${page.list }" var="appr" varStatus="status">
		        			<tr class="text-c">
								<td>${appr.id }</td>
					            <td>${appr.title }</td>
								<td>
									<c:forEach items="${appr.choices }" var="choice" varStatus="status">
										${choice.userName }
										<c:if test="${!status.last}">、</c:if>
									</c:forEach>
								</td>
								<td>${appr.content }</td>
								<td>${appr.createTime }</td>
					            <td class="td-manage">
						            <a title="详情" href="javascript:;" onclick="admin_edit('详情','${ctx }/back/appr/info?id=${appr.id }',1000,600)" class="ml-5" style="text-decoration:none"><i class="Hui-iconfont i-font-size-mid">&#xe616;</i></a>
									<%--<a title="编辑" href="javascript:;" onclick="admin_edit('编辑','${ctx }/back/appr/addOrEdit?id=${appr.id }','1',document.body.clientWidth-500,document.body.clientHeight-50)" class="ml-5" style="text-decoration:none"><i class="Hui-iconfont">&#xe6df;</i></a>--%>
									<a title="删除" href="javascript:;" onclick="admindel('${ctx }/back/appr/ajax/delete?id=${appr.id }',this)" class="ml-5" style="text-decoration:none"><i class="Hui-iconfont i-font-size-mid">&#xe6e2;</i></a>
								</td>
					        </tr>
		        		</c:forEach>
		        	</c:when>
		        	<c:otherwise>
		        		<tr class="text-c">
		            		<td colspan="6">暂无民主评议...</td>
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