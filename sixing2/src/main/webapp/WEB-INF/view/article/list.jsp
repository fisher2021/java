<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ include file="../common/taglibs.jsp" %>

<!DOCTYPE HTML>
<html>
<head>
	<%@ include file="../common/head.jsp" %>
	<title>文章管理</title>
	<style>
		body{
			min-width: 1000px;
		}
	</style>
</head>
<body>
	<nav class="breadcrumb">
		<i class="Hui-iconfont">&#xe67f;</i> 首页 <span class="c-gray en">&gt;</span> 资讯管理
		<span  class="c-error"><span class="c-gray en">&gt;</span><c:if test="${dictId == 'gzgf'}">工作规范</c:if><c:if test="${dictId == 'lzjs'}">廉政建设</c:if><c:if test="${dictId != 'gzgf' && dictId != 'lzjs'}">${firstDicts[0].parent.dictName }</c:if>管理</span>
		<a class="btn btn-success radius r" style="line-height:1.6em;margin-top:3px" href="javascript:location.replace(location.href);" title="刷新" ><i class="Hui-iconfont">&#xe68f;</i></a>
	</nav>
	<div class="page-container">
		<form action="${ctx }/back/article/list?dictId=${dictId }" method="post" id="form-submit">
		<input type="hidden" name="pageNo" value="${page.pageNo }" id="pageNo">
		<div class="operation_column">
			<span class="va-m">标题：</span><span class="search-box"><i class="Hui-iconfont search_icon">&#xe709;</i>
			<input type="text" class="input-text radius select_user" placeholder="请输入标题关键字" name="title" value="${form.title }" style="width: 140px"></span>
            <c:choose>
                <c:when test="${sessionScope.get('logedOperator').org.level == branch && dictId =='tzgg'}"></c:when>
                <c:otherwise>
                    <c:if test="${dictId != 'gzgf' && dictId != 'lzjs'}">
                        <span class="va-m">频道：</span>
                        <span class="search-box mr-20">
                        <select class="select input-text radius va-m" style="min-width: 100px;" name="channelDictId1" id="channelDictId1" onchange="change()">
                            <option value="">请选择频道</option>
                            <c:forEach items="${firstDicts }" var="firstDicts">
                                <option value="${firstDicts.dictId }" <c:if test="${firstDicts.dictId == form.channelDictId1 }">selected</c:if>>${firstDicts.dictName }</option>
                            </c:forEach>
                        </select>
                    </span>
                    </c:if>
                    <c:if test="${secondDicts != null }">
                    <span class="search-box mr-20">
                        <select class="select input-text radius va-m" style="min-width: 100px" name="channelDictId2">
                            <option value="">请选择子频道</option>
                            <c:forEach items="${secondDicts }" var="secondDicts">
                                <option value="${secondDicts.dictId }" class="dict2 type_${secondDicts.parent.dictId } " style="display: none" <c:if test="${secondDicts.dictId == form.channelDictId2 }">selected</c:if>>${secondDicts.dictName }</option>
                            </c:forEach>
                        </select>
                    </span>
                    </c:if>
                </c:otherwise>
            </c:choose>
			<span class="va-m">发布时间：</span><span class="search-box"><i class="Hui-iconfont search_icon">&#xe709;</i>
			<input type="text" class="input-text radius select_user" placeholder="请输入开始时间" readonly onclick="WdatePicker({lang:'zh-cn',dateFmt:'yyyy-MM-dd',maxDate:'#F{$dp.$D(\'endDate\')||\'new Date()\'}'})" id="startDate" style="margin-right: 0;width: 130px" name="startTime" value="${form.startTime }"></span>
            -
            <span class="search-box"><i class="Hui-iconfont search_icon">&#xe709;</i>
			<input type="text" class="input-text radius select_user" placeholder="请输入结束时间" readonly onclick="WdatePicker({lang:'zh-cn',dateFmt:'yyyy-MM-dd',minDate:'#F{$dp.$D(\'startDate\')}',maxDate:new Date()})" id="endDate" name="endTime" style="width: 130px" value="${form.endTime }"></span>
            <button type="submit" class="btn btn-danger radius">搜索</button>
			<a href="javascript:;" onclick="admin_add('新增<c:if test="${dictId == 'gzgf'}">工作规范</c:if><c:if test="${dictId != 'gzgf' }">${firstDicts[0].parent.dictName }</c:if>','${ctx }/back/article/addOrEdit?dictId=${dictId }',1000,600)" class="btn btn-danger-outline radius f-r"><i class="Hui-iconfont">&#xe600;</i> 新增</a>
		</div>
		</form>
		<form id="deleteMore">
		<table class="table table-border table-bordered table-bg table-striped">
			<thead>
				<tr class="text-c">
					<th width="col-sm-1"><input type="checkbox" /></th>
					<th width="col-sm-2">编号</th>
					<th width="col-sm-2" style="width: 450px">文章标题</th>
					<th width="col-sm-1">缩略图</th>
					<th width="col-sm-1">所属频道</th>
					<th width="col-sm-2">发布人</th>
					<th width="col-sm-2">阅读数</th>
					<th width="col-sm-2">发布时间</th>
					<th width="col-sm-2">操作</th>
				</tr>
			</thead>
			<tbody>
				<c:choose>
		        	<c:when test="${not empty page.list }">
		        		<c:forEach items="${page.list }" var="article" varStatus="status">
		        			<tr class="text-c">
					            <td><input type="checkbox" name="ids" value="${article.id }"/></td>
                                <td>${article.id }</td>
					            <td style="max-width: 500px" class="text-l">${article.title }</td>
								<td><c:if test="${not empty article.imgUrl }"><img src="${ctx }${article.imgUrl }" alt="加载失败" width="80px" height="80px"></c:if></td>
					            <td>${article.type.dictName }</td>
								<td>${article.author }</td>
								<td>${article.count }</td>
					            <td>${fn:substring(article.createTime, 0, 10) }</td>
					            <td class="td-manage">
                                    <c:if test="${not empty form.channelDictId1 || not empty form.channelDictId2 || dictId=='gzgf' || dictId=='lzjs'}">
                                    <%--<c:if test="${article.id != page.list.get(0).id }">--%>
                                    <a title="向上排序" href="javascript:;" onclick="changeRank('${ctx}/back/article/rank?id=${article.id }&type=1&channel1=${form.channelDictId1}&channel2=${form.channelDictId2}&dictId=${dictId}')" class="ml-5" ><i class="Hui-iconfont i-font-size-mid">&#xe6dc;</i></a>
                                    <%--</c:if>--%>
                                    <%--<c:if test="${!status.last }">--%>
                                    <a title="向下排序" href="javascript:;" onclick="changeRank('${ctx}/back/article/rank?id=${article.id }&type=2&channel1=${form.channelDictId1}&channel2=${form.channelDictId2}&dictId=${dictId}')" class="ml-5" ><i class="Hui-iconfont i-font-size-mid">&#xe6de;</i></a>
                                    <%--</c:if>--%>
                                    </c:if>
                                    <a title="详情" href="javascript:;" onclick="admin_edit('详情','${ctx }/back/article/details?id=${article.id }',1000,600)" class="ml-5" style="text-decoration:none"><i class="Hui-iconfont i-font-size-mid">&#xe616;</i></a>
									<a title="编辑" href="javascript:;" onclick="admin_edit('编辑','${ctx }/back/article/addOrEdit?id=${article.id }&dictId=${dictId }',1000,600)" class="ml-5" style="text-decoration:none"><i class="Hui-iconfont i-font-size-mid">&#xe6df;</i></a>
									<a title="删除" href="javascript:;" onclick="admindel('${ctx }/back/article/ajax/delete?id=${article.id }',this)" class="ml-5" style="text-decoration:none"><i class="Hui-iconfont i-font-size-mid">&#xe6e2;</i></a>
								</td>
					        </tr>
		        		</c:forEach>
		        	</c:when>
		        	<c:otherwise>
		        		<tr class="text-c">
		            		<td colspan="9">暂无文章...</td>
		            	</tr>	
		        	</c:otherwise>
		        </c:choose>
			</tbody>
		</table>
		</form>
        <div class="btn_column radius">
            <button type="button" class="btn btn-danger radius" onclick="deleteMore('${ctx}','${ctx }/back/article/ajax/deleteBatch')">批量删除</button>
        </div>
		<div class="clearfix">
			<div class="pagers f-r mr-20" style="padding-top: 3px">共 ${page.totalCount } 条记录</div>
			<div id="divPager"  class="pagers f-r"></div>
		</div>
	</div>
	<script type="text/javascript">
		demo(${page.totalPage},${page.pageNo});
		function change() {
			var id = $("#channelDictId1").val();
			$(".dict2").css("display","none");
			$(".type_"+id).css("display","block");
		}
	</script>
</body>
</html>