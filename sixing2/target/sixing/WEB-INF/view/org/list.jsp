<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ include file="../common/taglibs.jsp" %><!DOCTYPE HTML>
<html>
<head>
    <%@ include file="../common/head.jsp" %>
</head>
<body>
<form action="${ctx}/back/org/page" method="post">
<input type="hidden" name="pageNo" value="${page.pageNo }" id="pageNo">
    <nav class="breadcrumb">
        <i class="Hui-iconfont">&#xe67f;</i> 首页 <span class="c-gray en">&gt;</span> 组织管理 <span  class="c-error"><span class="c-gray en">&gt;</span> 组织架构管理 </span>
        <a class="btn btn-success radius r" style="line-height:1.6em;margin-top:3px" href="javascript:location.replace(location.href);" title="刷新" ><i class="Hui-iconfont">&#xe68f;</i></a>
    </nav>

    <div class="page-container">
        <div class="operation_column">
            <span class="va-m">组织名称：</span>
            <span class="search-box">
                <i class="Hui-iconfont search_icon">&#xe709;</i>
                <input type="text" name="name" value="${form.name}" class="input-text radius select_user" placeholder="请输入组织名称">
            </span>
            <span class="va-m">组织类型：</span>
            <span class="search-box">
                <select class="select input-text radius va-m" style="min-width: 100px" name="type">
                    <option value="">全部</option>
                    <c:forEach items="${type }" var="org">
                        <c:if test="${org.ordinal()!=0 && org.ordinal()!=1}">
                        <option value="${org }" <c:if test="${form.type == org}">selected</c:if>>
                            <c:if test="${org.ordinal() == 2}">一级组织</c:if>
                            <c:if test="${org.ordinal() == 3}">二级组织</c:if>
                            <c:if test="${org.ordinal() == 4}">三级组织</c:if>
                        </option>
                        </c:if>
                    </c:forEach>
                </select>
            </span>
            <button type="submit" class="btn btn-danger radius mr-20">搜索</button>
            <a href="javascript:;" onclick="admin_edit('新增','${ctx}/back/org/info/0',600,400)" class="btn btn-danger-outline radius f-r"><i class="Hui-iconfont">&#xe600;</i> 新增组织</a>
        </div>
        <table class="table table-border table-bordered table-bg table-striped">
            <thead>
                <tr class="text-c">
                    <th width="col-sm-1">序号</th>
                    <th width="col-sm-1">组织名称</th>
                    <th width="col-sm-1">所属</th>
                    <th width="col-sm-2">操作</th>
                </tr>
            </thead>
            <tbody>
                <c:choose>
                    <c:when test="${not empty page.list }">
                        <c:forEach items="${page.list }" var="org" varStatus="status">
                            <tr class="text-c">
                                <td>${status.count }</td>
                                <td>${org.name }</td>
                                <td>${org.parent.name }</td>
                                <td>
                                    <a title="编辑" href="javascript:;" onclick="admin_edit('编辑','${ctx}/back/org/info/${org.id}',600,400)" class="ml-5" ><i class="Hui-iconfont i-font-size-mid">&#xe6df;</i></a>
                                    <a title="删除" href="javascript:;" onclick="admindel('${ctx}/back/org/delete/'+${org.id},this)" class="ml-5" ><i class="Hui-iconfont i-font-size-mid">&#xe6e2;</i></a>
                                </td>
                            </tr>
                        </c:forEach>
                    </c:when>
                    <c:otherwise>
                        <tr class="text-c">
                            <td colspan="5">暂无组织信息...</td>
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
</form>
</body>
<script type="text/javascript">
    demo(${page.totalPage},${page.pageNo});
</script>
</html>