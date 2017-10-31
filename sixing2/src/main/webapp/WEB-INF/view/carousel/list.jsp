<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ include file="../common/taglibs.jsp" %>

<!DOCTYPE HTML>
<html>
<head>
	<%@ include file="../common/head.jsp" %>
	<title>轮播图管理</title>
	<style>
		body{
			min-width: 1000px;
		}
	</style>
</head>
<body>
	<nav class="breadcrumb">
		<i class="Hui-iconfont">&#xe67f;</i> 首页 <span class="c-gray en">&gt;</span> 资讯管理<span  class="c-error"><span class="c-gray en">&gt;</span> 轮播图管理 </span>
		<a class="btn btn-success radius r" style="line-height:1.6em;margin-top:3px" href="javascript:location.replace(location.href);" title="刷新" ><i class="Hui-iconfont">&#xe68f;</i></a>
	</nav>
	<div class="page-container">
		<form action="${ctx }/back/carousel/list" method="post">
		<input type="hidden" name="pageNo" value="${page.pageNo }" id="pageNo">
		<div class="operation_column">
			<span class="va-m">标题：</span><span class="search-box"><i class="Hui-iconfont search_icon">&#xe709;</i>
			<input type="text" class="input-text radius select_user" placeholder="请输入标题" name="title" value="${form.title }"></span>
			<button type="submit" class="btn btn-danger radius">搜索</button>
			<a href="javascript:;" onclick="admin_add('新增轮播图','${ctx }/back/carousel/addOrEdit','1000','600')" class="btn btn-danger-outline radius f-r"><i class="Hui-iconfont">&#xe600;</i> 新增轮播图</a>
		</div>
		</form>

		<form id="deleteMore">
		<table class="table table-border table-bordered table-bg table-striped">
			<thead>
				<tr class="text-c">
					<th width="col-sm-1"><input type="checkbox"/></th>
					<th width="col-sm-1">序号</th>
					<th width="col-sm-1">标题</th>
					<th width="col-sm-1">图片</th>
					<th width="col-sm-2">链接地址</th>
					<th width="col-sm-1">类别</th>
                    <th width="col-sm-2">发布人</th>
                    <th width="col-sm-1">排序</th>
					<th width="col-sm-2">操作</th>
				</tr>
			</thead>
			<tbody>
				<c:choose>
		        	<c:when test="${not empty page.list }">
		        		<c:forEach items="${page.list }" var="carousel" varStatus="status">
		        			<tr class="text-c">
					            <td><input type="checkbox" name="ids" value="${carousel.id }"/></td>
								<td>${status.count }</td>
					            <td>${carousel.title }</td>
					            <td><c:if test="${not empty carousel.imageUrl }"><img src="${ctx }${carousel.imageUrl }" alt="加载失败" width="80px" height="80px"></c:if></td>
					            <td>${carousel.targetUrl }</td>
								<td><c:if test="${carousel.type.id == 1}">党建要闻</c:if><c:if test="${carousel.type.id == 3}">理论学习</c:if><c:if test="${carousel.type.id == 54}">专题学习</c:if></td>
                                <td>${carousel.author }</td>
                                <td>${carousel.rank }</td>
					            <td class="td-manage">
									<a title="编辑" href="javascript:;" onclick="admin_edit('编辑','${ctx }/back/carousel/addOrEdit?id=${carousel.id }','1000','600')" class="ml-5" style="text-decoration:none"><i class="Hui-iconfont i-font-size-mid">&#xe6df;</i></a>
									<a title="删除" href="javascript:;" onclick="admindel('${ctx }/back/carousel/ajax/delete?id=${carousel.id }',this)" class="ml-5" style="text-decoration:none"><i class="Hui-iconfont i-font-size-mid">&#xe6e2;</i></a>
								</td>
					        </tr>
		        		</c:forEach>
		        	</c:when>
		        	<c:otherwise>
		        		<tr class="text-c">
		            		<td colspan="6">暂无轮播图...</td>
		            	</tr>	
		        	</c:otherwise>
		        </c:choose>
			</tbody>
		</table>
		</form>
        <div class="btn_column radius">
            <button type="button" class="btn btn-danger radius" onclick="deleteMore('${ctx}','${ctx }/back/carousel/ajax/deleteBatch')">批量删除</button>
        </div>
		<div class="clearfix">
			<div class="pagers f-r mr-20" style="padding-top: 3px">共 ${page.totalCount } 条记录</div>
			<div id="divPager"  class="pagers f-r"></div>
		</div>
	</div>
	<script type="text/javascript" src="${ctx }/static/back/admin/h-ui.admin/js/mine.js"></script>
	<script type="text/javascript" src="${ctx }/static/back/admin/h-ui.admin/js/common.js"></script>
	<script type="text/javascript">
		demo(${page.totalPage},${page.pageNo});
	</script>
</body>
</html>