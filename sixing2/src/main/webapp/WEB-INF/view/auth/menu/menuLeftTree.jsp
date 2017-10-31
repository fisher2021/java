<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
	<head>
		<base href="<%=basePath%>">
		<title>菜单树</title>
		<meta http-equiv="pragma" content="no-cache">
		<meta http-equiv="cache-control" content="no-cache">
		<meta http-equiv="expires" content="0">
		<link href="<%=basePath%>static/common/js/ext-3.2.0/resources/css/ext-all.css"
			  type="text/css" rel="stylesheet">
		<script type="text/javascript"
				src="<%=basePath%>static/common/js/ext-3.2.0/adapter/ext/ext-base.js"
				charset="utf-8"></script>
		<script type="text/javascript"
				src="<%=basePath%>static/common/js/ext-3.2.0/ext-all.js" charset="utf-8"></script>
		<Script src="<%=path%>/static/common/js/jquery-1.9.1.min.js"></Script>
		<script type="text/javascript"
			src="<%=basePath%>/static/auth/js/common/menu/menuLeftTree.js"></script>
		<script type="text/javascript">
		//刷新树	展开指定树的 某个节点
	function flushTree(id,nodeId) {
		var tree = Ext.getCmp(id);
		var treenode=tree.getNodeById (nodeId);
		//展开路径,并在回调函数里面选择该节点
		//tree.getRootNode().reload();
		tree.getSelectionModel().select(treenode);
		tree.expandPath(treenode.getPath(),'treeId',function(bSucess,oLastNode){
		});
}
		</script>
		<script type="text/javascript">
		var path='<%=path%>';
		</script>
	</head>

	<body>
		<div id="tree-div"></div>
	</body>
</html>
