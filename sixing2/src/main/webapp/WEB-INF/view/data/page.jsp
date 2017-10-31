<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ include file="../common/taglibs.jsp" %>

<!DOCTYPE HTML>
<html>
<head>
	<%@ include file="../common/head.jsp" %>
	<title>资料下载</title>
	<style>
		body{
			min-width: 1000px;
		}
	</style>
</head>
<body>
	<nav class="breadcrumb">
		<i class="Hui-iconfont">&#xe67f;</i> 首页 <span class="c-gray en">&gt;</span> 资料下载管理 <span  class="c-error"><span class="c-gray en">&gt;</span> 资料下载管理 </span>
		<a class="btn btn-success radius r" style="line-height:1.6em;margin-top:3px" href="javascript:location.replace(location.href);" title="刷新" ><i class="Hui-iconfont">&#xe68f;</i></a>
	</nav>
	<div class="page-container">
		<form action="${ctx }/back/file/page" method="post">
		<input type="hidden" name="pageNo" value="${page.pageNo }" id="pageNo">
		<div class="operation_column">
            <span class="va-m">标题：</span><span class="search-box"><i class="Hui-iconfont search_icon">&#xe709;</i>
			<input type="text" class="input-text radius select_user" placeholder="请输入标题关键字" name="title" value="${form.title }"></span>
            <span class="va-m">上传时间：</span><span class="search-box"><i class="Hui-iconfont search_icon">&#xe709;</i>
			<input type="text" class="input-text radius select_user" placeholder="请输入开始时间" readonly onclick="WdatePicker({lang:'zh-cn',dateFmt:'yyyy-MM-dd',maxDate:'#F{$dp.$D(\'endDate\')||\'new Date()\'}'})" id="startDate" style="margin-right: 0;" name="startTime" value="${form.startTime }"></span>
            -
            <span class="search-box"><i class="Hui-iconfont search_icon">&#xe709;</i>
			<input type="text" class="input-text radius select_user" placeholder="请输入结束时间" readonly onclick="WdatePicker({lang:'zh-cn',dateFmt:'yyyy-MM-dd',minDate:'#F{$dp.$D(\'startDate\')}',maxDate:new Date()})" id="endDate" name="endTime" value="${form.endTime }"></span>
            <button type="submit" class="btn btn-danger radius">搜索</button>
			<a href="javascript:;" onclick="admin_add('新增资料','${ctx }/back/data/addOrEdit','800','500')" class="btn btn-danger-outline radius f-r"><i class="Hui-iconfont">&#xe600;</i> 新增</a>
		</div>
		</form>
		<form id="deleteMore">
		<table class="table table-border table-bordered table-bg table-striped">
			<thead>
				<tr class="text-c">
					<th width="col-sm-1">序号</th>
                    <th width="col-sm-1">标题</th>
					<th width="col-sm-2">上传时间</th>
					<th width="col-sm-2">操作</th>
				</tr>
			</thead>
			<tbody>
				<c:choose>
		        	<c:when test="${not empty page.list }">
		        		<c:forEach items="${page.list }" var="file" varStatus="status">
		        			<tr class="text-c">
								<td>${status.count }</td>
                                <td>${file.title }</td>
								<td>${file.createTime }</td>
					            <td class="td-manage">
						            <%--<a title="详情" href="javascript:;" onclick="admin_edit('详情','${ctx }/back/file/info?id=${backlog.id }',800,500)" class="ml-5" style="text-decoration:none"><i class="Hui-iconfont i-font-size-mid">&#xe616;</i></a>--%>
									<a title="编辑指派对象" href="javascript:;" onclick="admin_edit('编辑','${ctx }/back/data/userPage?fileId=${file.id }',1000,600)" class="ml-5" style="text-decoration:none"><i class="Hui-iconfont i-font-size-mid">&#xe62b;</i></a>
									<a title="删除" href="javascript:;" onclick="admindel('${ctx }/back/data/ajax/delete?id=${file.id }',this)" class="ml-5" style="text-decoration:none"><i class="Hui-iconfont i-font-size-mid">&#xe6e2;</i></a>
								</td>
					        </tr>
		        		</c:forEach>
		        	</c:when>
		        	<c:otherwise>
		        		<tr class="text-c">
		            		<td colspan="6">暂无资料...</td>
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