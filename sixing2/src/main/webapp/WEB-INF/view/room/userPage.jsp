<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ include file="../common/taglibs.jsp" %>

<!DOCTYPE HTML>
<html>
<head>
	<%@ include file="../common/head.jsp" %>
	<title>会议室管理</title>
	<style>
		body{
			min-width: 950px;
		}
        .operate{
            display: none;
        }
	</style>
</head>
<body>
    <div class="page-container">
    	<form action="${ctx }/back/room/userPage?roomId=${room.id }" method="post" id="form-submit">
    	<input type="hidden" name="pageNo" value="${page.pageNo }" id="pageNo">
        <div class="operation_column">
            <span class="va-m">姓名：</span><span class="search-box"><i class="Hui-iconfont search_icon">&#xe709;</i><input type="text" class="input-text radius select_user" placeholder="请输入姓名关键字" name="nickname" value=${form.nickname }></span>
            <c:if test="${sessionScope.get('logedOperator').org.level != branch}">
            <span class="va-m">组织关系：</span>
            <span class="search-box mr-20" onclick="tree_layer(this,'${ctx}')" id="change-to">
			    <i class="Hui-iconfont search_icon">&#xe709;</i>
			    <input type="text" name="orgName" value="${form.orgName }" class="input-text radius select_user" placeholder="请选择组织关系" readonly>
                <input type="hidden" name="orgId" value="${form.orgId }" >
		    </span>
            </c:if>
            <span class="va-m">状态：</span>
            <span class="search-box">
                <select class="select input-text radius va-m" style="min-width: 100px" name="userHave" id="userHave">
                        <option value="0" <c:if test="${!form.userHave}" >selected</c:if> >未添加</option>
                    <option value="1" <c:if test="${form.userHave}" >selected</c:if> >已添加</option>
                </select>
            </span>
            <button type="submit" class="btn btn-danger radius">搜索</button>
        </div>
        </form>
        <form id="addMore">
        <table class="table table-border table-bordered table-bg table-striped">
            <thead>
            <tr class="text-c">
                <th width="col-sm-1" <c:if test="${form.userHave }"> class="operate"</c:if>><input type="checkbox" /></th>
                <th width="col-sm-1">序号</th>
                <th width="col-sm-1">组织关系</th>
                <th width="col-sm-1">姓名</th>
                <th width="col-sm-1">用户名</th>
                <th width="col-sm-2" <c:if test="${!form.userHave }"> class="operate"</c:if> >操作</th>
            </tr>
            </thead>
            <tbody>
            	<c:choose>
		        	<c:when test="${not empty page.list }">
		        		<c:forEach items="${page.list }" var="user" varStatus="status">
		        			<tr class="text-c">
                                <td <c:if test="${form.userHave }"> class="operate"</c:if>><input type="checkbox" name="ids" value="${user.id }"/></td>
                                <td>${status.count }</td>
                                <td><c:forEach items="${user.orgs }" var="org" varStatus="status">
                                    ${org.name }
                                    <c:if test="${!status.last}">、</c:if>
                                    </c:forEach>
                                </td>
					            <td>${user.nickname }</td>
                                <td>${user.account }</td>
					            <td  class="<c:if test="${!form.userHave }"> operate</c:if> td-manage">
                                    <c:if test="${user.account == room.owner}">群主不可删除</c:if>
                                    <c:if test="${user.account != room.owner}">
                                        <a title="删除" href="javascript:;" onclick="admindel('${ctx}/back/room/ajax/deleteUser?id=${user.id }&roomId=${room.id }',this)" class="ml-5" ><i class="Hui-iconfont i-font-size-mid">&#xe6e2;</i></a>
                                        <a title="转让群主" href="javascript:;" onclick="newOwner('${ctx}/back/room/newGroupOwner?roomId=${room.id }&account=${user.account }')" class="ml-5" style="text-decoration:none"><i class="Hui-iconfont i-font-size-mid">&#xe6ab;</i></a>
                                    </c:if>
                    			</td>
					        </tr>
		        		</c:forEach>
		        	</c:when>
		        	<c:otherwise>
		        		<tr class="text-c">
		            		<td colspan="5">暂无用户信息...</td>
		            	</tr>	
		        	</c:otherwise>
		        </c:choose>  
            </tbody>
        </table>
        </form>
        <div class="btn_column radius <c:if test="${form.userHave }"> operate</c:if>">
            <button type="button" class="btn btn-danger radius" onclick="addMore('${ctx}','${ctx }/back/room/ajax/addBatch?roomId=${room.id }')">添加会议室成员</button>
        </div>
        <div class="clearfix">
            <div class="pagers f-r mr-20" >共 ${page.totalCount } 条记录</div>
            <div id="divPager"  class="pagers f-r"></div>
        </div>

    </div>
    <script type="text/javascript">
   		demo(${page.totalPage},${page.pageNo});
    </script>
</body>
</html>