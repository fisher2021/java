<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ include file="../common/taglibs.jsp" %>

<!DOCTYPE HTML>
<html>
<head>
	<%@ include file="../common/head.jsp" %>
	<title>系统消息</title>
	<style>
		body{
			min-width: 1000px;
		}
	</style>
</head>
<body>
	<nav class="breadcrumb">
		<i class="Hui-iconfont">&#xe67f;</i> 首页 <span class="c-gray en">&gt;</span> 系统消息管理 <span  class="c-error">
		<span class="c-gray en">&gt;</span> 系统消息管理 </span>
		<a class="btn btn-success radius r" style="line-height:1.6em;margin-top:3px" href="javascript:location.replace(location.href);" title="刷新" ><i class="Hui-iconfont">&#xe68f;</i></a>
	</nav>
	<div class="page-container">
		<form action="${ctx }/back/message/page" method="post">
		<input type="hidden" name="pageNo" value="${page.pageNo }" id="pageNo">
		<div class="operation_column">
			<span class="va-m">标题：</span><span class="search-box"><i class="Hui-iconfont search_icon">&#xe709;</i>
			<input type="text" class="input-text radius select_user" placeholder="请输入标题关键字" name="title" value="${form.title }"></span>
			<button type="submit" class="btn btn-danger radius">搜索</button>
            <a href="javascript:;" onclick="admin_add('发送系统消息','${ctx }/back/message/addOrEdit','800','450')" class="btn btn-danger-outline radius f-r"><i class="Hui-iconfont">&#xe600;</i> 发送系统消息</a>
        </div>
		</form>
		<%--<div class="btn_column radius">--%>
			<%--<button type="button" class="btn btn-danger radius" onclick="deleteMore('${ctx}','${ctx }/back/report/ajax/deleteBatch')">批量删除</button>--%>
		<%--</div>--%>
		<form id="deleteMore">
		<table class="table table-border table-bordered table-bg table-striped">
			<thead>
				<tr class="text-c">
					<%--<th width="col-sm-1"><input type="checkbox" /></th>--%>
					<th width="col-sm-1">序号</th>
					<th width="col-sm-1">消息标题</th>
					<th width="col-sm-1" style="width: 500px">消息内容</th>
					<th width="col-sm-2">推送时间</th>
					<th width="col-sm-2">操作</th>
				</tr>
			</thead>
			<tbody>
				<c:choose>
		        	<c:when test="${not empty page.list }">
		        		<c:forEach items="${page.list }" var="message" varStatus="status">
		        			<tr class="text-c">
					            <%--<td><input type="checkbox" name="ids" value="${report.id }"/></td>--%>
								<td>${status.count }</td>
					            <td>${message.title }</td>
								<td class="text-l">${message.content }</td>
                                <td>${message.createTime }</td>
					            <td class="td-manage">
                                     <%--<a title="编辑指派组织" href="javascript:;" onclick="admin_edit('编辑','${ctx }/back/message/orgPage?messageId=${message.id }',1000,600)" class="ml-5" style="text-decoration:none"><i class="Hui-iconfont">&#xe62b;</i></a>--%>
                                     <a title="详情" href="javascript:;" onclick="admin_edit('详情','${ctx }/back/message/info?id=${message.id }',1000,600)" class="ml-5" style="text-decoration:none"><i class="Hui-iconfont i-font-size-mid">&#xe616;</i></a>
                                     <a title="删除" href="javascript:;" onclick="admindel('${ctx }/back/message/ajax/delete?id=${message.id }',this)" class="ml-5" style="text-decoration:none"><i class="Hui-iconfont i-font-size-mid">&#xe6e2;</i></a>
                                     <%--<a title="发送" href="javascript:;" onclick="push('${ctx }/back/message/ajax/push?id=${message.id }',this)" class="ml-5" style="text-decoration:none"><i class="Hui-iconfont">&#xe603;</i></a>--%>
								</td>
					        </tr>
		        		</c:forEach>
		        	</c:when>
		        	<c:otherwise>
		        		<tr class="text-c">
		            		<td colspan="6">暂无系统消息...</td>
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
        function push(url,obj) {
             layer.confirm('确认要发送吗？',function(index){
                 //此处请求后台程序，下方是成功后的前台处理……
                 $.postJSON(url,null,function(data) {
                     layer.msg(data.msg,{icon:1,time:2000},function () {
                         if(data.status==1) {
                             window.location.reload(true);
                         }
                     });
                });
            });
        }
	</script>
</body>
</html>