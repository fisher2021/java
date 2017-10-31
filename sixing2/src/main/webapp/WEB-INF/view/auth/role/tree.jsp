<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="c"  uri="http://java.sun.com/jsp/jstl/core"%>
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
			src="<%=basePath%>static/auth/js/common/role/roleTree.js"></script>
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
   function saveOperRole(){
    var tree = Ext.getCmp("treeId");
    var nodes=(tree.getChecked());
    if(nodes.length==0){
     alert("至少选择一个角色");
     return;
    }

     var roles="id="+oper;
     for(var i=0;i<nodes.length;i++  ){
      var node=nodes[i]
      roles+="&roles["+i+"].id="+node.id;
     }
     	 jQuery.ajax({type:"POST", url:path+"/auth/operator/udpateOperatorRoll",data:roles, beforeSend:function () {
	}, success:function (result) {
	 if(result=="suceess"){
	alert("操作成功");
	 }else{
	  alert("操作失败");
	 }
		
	}, error:function (result) {
		alert("\u4fdd\u5b58\u5931\u8d25");
	}});
   } 

		</script>
		<script type="text/javascript">
		var path='<%=path%>';
		var oper="${oper.id}";
		</script>
	</head>

	<body>
		<div id="tree-div"></div>
		<input type="button" value="确定"  onclick="saveOperRole()"/>
		<input type="button" value="关闭"  onclick="window.close()"/>
	</body>
</html>
