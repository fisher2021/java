<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ include file="../common/taglibs.jsp" %>

<!DOCTYPE html>
<html>
<head>
    <%@ include file="../common/head.jsp" %>
    <link rel="stylesheet" type="text/css" href="${ctx }/static/back/lib/zTree/v3/css/zTreeStyle/zTreeStyle.css" >
    <title>系统管理-权限菜单</title>
    <style>
        body{
            min-width: 1000px;
        }
    </style>
</head>
<body>
    <nav class="breadcrumb">
        <i class="Hui-iconfont">&#xe67f;</i> 首页 <span class="c-gray en">&gt;</span> 系统管理 <span  class="c-error"><span class="c-gray en">&gt;</span> 菜单管理 </span>
        <a class="btn btn-success radius r"  href="javascript:location.replace(location.href);" title="刷新" ><i class="Hui-iconfont">&#xe68f;</i></a>
    </nav>
    <div class="pos-a f-l aside ztree_aside">
        <ul id="tree-menu" class="ztree"></ul>
    </div>
    <section class="Hui-article-box iframe-box">
        <iframe id="tree-frame" frameborder="0"  src="${ctx}/back/menu/children?id=0"></iframe>
    </section>
    <script type="text/javascript" src="${ctx }/static/back/lib/zTree/v3/js/jquery.ztree.all-3.5.min.js"></script>
    <script type="text/javascript" src="${ctx }/static/back/admin/h-ui.admin/js/menu-tree.js"></script>
    <script type="text/javascript">
    	var zNodes =[
            { id:0, pId:-1, name:"菜单目录", open:true, _href:"${ctx}/back/menu/children?id=0"},
	         <c:forEach items="${menuList }" var="menu">
	 		 <c:if test="${empty menu.parent }">
	 			 { id:${menu.id }, pId:0, name:"${menu.name }",_href:"${ctx}/back/menu/children?id=${menu.id }"},
	 		 </c:if>
	 		 <c:if test="${not empty menu.parent }">
	 			 { id:${menu.id }, pId:${menu.parent.id }, name:"${menu.name }",_href:"${ctx}/back/menu/children?id=${menu.id }"},
	 		 </c:if>
	 		 </c:forEach>
         ];
    </script>
</body>
</html>