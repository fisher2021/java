<%--
  Created by IntelliJ IDEA.
  User: zhangshao
  Date: 2016/5/14
  Time: 18:08
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ include file="../../common/taglibs.jsp" %>
<!DOCTYPE html>
<html lang="en">
<head>
	<meta charset="UTF-8">
	<meta http-equiv="X-UA-Compatible" content="IE=edge">
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
	<meta name="viewport" content="width=device-width, initial-scale=1.0" />
	<meta name="description" content="Xenon Boostrap Admin Panel" />
	<meta name="author" content="" />

	<title>角色管理</title>

	<!--    <link rel="stylesheet" href="http://fonts.useso.com/css?family=Arimo:400,700,400italic">-->
	<link rel="stylesheet" href="${ctx}/static/admin/assets/css/fonts/linecons/css/linecons.css">
	<link rel="stylesheet" href="${ctx}/static/admin/assets/css/fonts/fontawesome/css/font-awesome.min.css">
	<link rel="stylesheet" href="${ctx}/static/admin/assets/css/bootstrap.css">
	<link rel="stylesheet" href="${ctx}/static/admin/assets/css/xenon-core.css">
	<link rel="stylesheet" href="${ctx}/static/admin/assets/css/xenon-forms.css">
	<link rel="stylesheet" href="${ctx}/static/admin/assets/css/xenon-components.css">
	<link rel="stylesheet" href="${ctx}/static/admin/assets/css/xenon-skins.css">
	<link rel="stylesheet" href="${ctx}/static/admin/css/common/custom.css">

	<script src="${ctx}/static/admin/assets/js/jquery-1.11.1.min.js"></script>

	<!-- HTML5 shim and Respond.js IE8 support of HTML5 elements and media queries -->
	<!--[if lt IE 9]>
	<script src="https://oss.maxcdn.com/html5shiv/3.7.2/html5shiv.min.js"></script>
	<script src="https://oss.maxcdn.com/respond/1.4.2/respond.min.js"></script>
	<![endif]-->

	<link href="${ctx}/static/common/js/ext-3.2.0/resources/css/ext-all.css" type="text/css" rel="stylesheet">

</head>
<body class="page-body" style="width: 100%;">
<!--search-->
<div class="main-content">
	<section class="roleList panel panel-default">
		<div class="row marg0 panel-body">
			<div class="row">
				<div class="col-sm-11"></div>
				<div class="col-sm-1">
					<button class="btn btn-pink" onclick="window.location.href='editRole'">
						<i class="fa-plus"></i>
						<span>新增角色</span>
					</button>
				</div>
			</div>


			<table class="table table-bordered table-striped box" id="example-2">
				<thead>
				<tr>
					<th class="no-sorting">
						编号
					</th>
					<th>角色编码</th>
					<th>角色名称</th>
					<th>创建时间</th>
					<th>操作</th>
				</tr>
				</thead>
				<tbody class="middle-align">
				<c:forEach  items="${list}" var="role" varStatus="i">
					<tr>
						<td>${ i.count }</td>
						<td><c:out value="${role.roleCode}" /></td>
						<td><c:out value="${role.roleName}" /></td>
						<td>${role.createTime}</td>
						<td>
							<a href="editRole?id=${role.id}" class="btn btn-secondary btn-sm btn-icon icon-left">
								详情
							</a>
							<a href="#" onclick="openTree('${role.id}');" class="btn btn-turquoise btn-sm btn-icon icon-left">
								权限编辑
							</a>
							<a delUrl="delRole"  delId="${role.id}"  href="javascript:;" class="btn btn-warning btn-sm btn-icon icon-left remove-btn">
								删除
							</a>
						</td>
					</tr>
				</c:forEach>


				</tbody>
			</table>
		</div>
	</section>


	<div class="modal fade" id="addRole">
		<div class="modal-dialog">
			<div class="modal-content">
				<div class="modal-header">
					<button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button>
					<h4 class="modal-title" style="color: #40bbea">权限编辑</h4>
				</div>
				<form method="" action="" id="myform1">
					<input type="hidden" name="id" class="acId">
					<div class="modal-body ofh" >

						<div id="tree-div"></div>

					</div>
					<div class="modal-footer">
						<button type="button" class="btn btn-white" data-dismiss="modal">确认</button>
					</div>
				</form>
			</div>
		</div>
	</div>

</div>

<!-- Imported styles on this page -->
<link rel="stylesheet" href="${ctx}/static/admin/assets/js/daterangepicker/daterangepicker-bs3.css">
<link rel="stylesheet" href="${ctx}/static/admin/assets/js/select2/select2.css">
<link rel="stylesheet" href="${ctx}/static/admin/assets/js/select2/select2-bootstrap.css">
<link rel="stylesheet" href="${ctx}/static/admin/assets/js/multiselect/css/multi-select.css">

<!-- Bottom Scripts -->
<script src="${ctx}/static/admin/assets/js/bootstrap.min.js"></script>
<script src="${ctx}/static/admin/assets/js/TweenMax.min.js"></script>
<script src="${ctx}/static/admin/assets/js/resizeable.js"></script>
<script src="${ctx}/static/admin/assets/js/joinable.js"></script>
<script src="${ctx}/static/admin/assets/js/xenon-api.js"></script>
<script src="${ctx}/static/admin/assets/js/xenon-toggles.js"></script>
<script src="${ctx}/static/admin/assets/js/moment.min.js"></script>


<!-- Imported scripts on this page -->
<script src="${ctx}/static/admin/assets/js/jquery-validate/jquery.validate.min.js"></script>
<script src="${ctx}/static/admin/assets/js/daterangepicker/daterangepicker.js"></script>
<script src="${ctx}/static/admin/assets/js/datepicker/bootstrap-datepicker.js"></script>
<script src="${ctx}/static/admin/assets/js/timepicker/bootstrap-timepicker.min.js"></script>
<script src="${ctx}/static/admin/assets/js/colorpicker/bootstrap-colorpicker.min.js"></script>
<script src="${ctx}/static/admin/assets/js/select2/select2.min.js"></script>
<script src="${ctx}/static/admin/assets/js/jquery-ui/jquery-ui.min.js"></script>
<script src="${ctx}/static/admin/assets/js/selectboxit/jquery.selectBoxIt.min.js"></script>
<script src="${ctx}/static/admin/assets/js/tagsinput/bootstrap-tagsinput.min.js"></script>
<script src="${ctx}/static/admin/assets/js/typeahead.bundle.js"></script>
<script src="${ctx}/static/admin/assets/js/handlebars.min.js"></script>
<script src="${ctx}/static/admin/assets/js/multiselect/js/jquery.multi-select.js"></script>

<!-- JavaScripts initializations and stuff -->
<script src="${ctx}/static/admin/assets/js/xenon-custom.js"></script>

<script src="${ctx}/static/admin/js/common/common.js"></script>
<script src="${ctx}/static/common/js/base.js"></script>
<script src="${ctx}/static/common/js/checkbox.js"></script>


<script type="text/javascript" src="${ctx}/static/common/js/ext-3.2.0/adapter/ext/ext-base.js" charset="utf-8"></script>
<script type="text/javascript" src="${ctx}/static/common/js/ext-3.2.0/ext-all.js" charset="utf-8"></script>

<script>
	var roleid=0;
	$(document).ready(function() {
		createCheckboxDefaultValue("roleStatus", null, "roleStatus", "status.dictId", "${statuss}", "dictId");
		removeData();
	})

	function openTree(id){
		$("#tree-div").empty();
		jQuery('#addRole').modal('show', {backdrop: 'static'});
		roleid=id;

		tree = new Ext.tree.TreePanel({
			useArrows: true,
			id:"treeId",
			el:"tree-div",
			autoScroll: true,
			animate: true,
			enableDD:false,// 是否支持拖放
			bodyStyle:'background-color:transparent' ,
			containerScroll: true,
			border: false,
			onlyLeafCheckable: false,//对树所有结点都可选
			loader: new Ext.tree.TreeLoader({
				dataUrl:'${ctx}/auth/menu/getMenuJsonTreeCheckedNode.action'
			})
		});
		tree.on('beforeload',function(node){
			if(node.id=="-1"){
				tree.loader.dataUrl = "${ctx}/auth/menu/getMenuJsonTreeCheckedNode.action?roleid="+roleid;
			}else{
				tree.loader.dataUrl = "${ctx}/auth/menu/getMenuJsonTreeCheckedNode.action?roleid="+roleid+"&id="+node.id;
			}
		});
		//节点选中事件
		tree.on('checkChange',function(node){
			var nodeChecked=node.attributes.checked;
			var data="menuid="+node.id+"&"+"roleid="+roleid;
			var url="";
			if(nodeChecked){
				//保存
				url ="${ctx}/auth/roleMenu/saveRoleMenu";
			}else{
				//删除
				url="${ctx}/auth/roleMenu/delRoleMenu";
			}
			sendPostMethod(url,data);
			casecadeParentAndChilds(node,nodeChecked);
		});
		var root = new Ext.tree.AsyncTreeNode({
			text: '菜单管理',
			id:'-1'
		});
		tree.setRootNode(root);
		//默认展开第一层级
		tree.getRootNode().expand(false,true);
		tree.render();
	}
	function flushTree(id,nodeId) {
		var tree = Ext.getCmp(id);
		var treenode=tree.getNodeById (nodeId);
		//展开路径,并在回调函数里面选择该节点
		//tree.getRootNode().reload();
		tree.getSelectionModel().select(treenode);
		tree.expandPath(treenode.getPath(),'treeId',function(bSucess,oLastNode){
		});
	}
	//级联操作子节点
	function  casecadeChilds(node,state){
//是否具有子节点 级联操作
		if (node.hasChildNodes()) {
			for (var j = 0; j < node.childNodes.length; j++) {
				node.childNodes[j].ui.toggleCheck(state);
				node.childNodes[j].attributes.checked=state;
				var data="menuid="+node.childNodes[j].id+"&"+"roleid="+roleid;
				var url="";
				if(state){
					//保存
					url ="${ctx}/auth/roleMenu/saveRoleMenu";
				}else{
					//删除
					url="${ctx}/auth/roleMenu/delRoleMenu";
				}
				sendPostMethod(url,data);
				casecadeChilds(node.childNodes[j],state);
			}
		}
	}
	//级联操作父对象
	function  casecadeParent(node,state){
//是否具有父节点 级联操作
		var parentNode=node.parentNode;
		if(parentNode!=null&&parentNode.id!="-1"){
			//如果子节点选中 则父节点选中
			if(state){
				parentNode.ui.toggleCheck(state);
				parentNode.attributes.checked=state;
				var data="menuid="+parentNode.id+"&"+"roleid="+roleid;
				var url="";
				if(state){
					//保存
					url ="${ctx}/auth/roleMenu/saveRoleMenu";
				}else{
					//删除
					url="${ctx}/auth/roleMenu/delRoleMenu";
				}
				sendPostMethod(url,data);
				casecadeParent(parentNode,state);
			}
		}
	}
	//级联操作父子节点
	function casecadeParentAndChilds(node,nodeChecked)
	{
		tree.suspendEvents(); //暂停所有监听事件的执行
		//级联选中父节点
		casecadeParent(node,nodeChecked);
		//级联选中子节点
		casecadeChilds(node,nodeChecked);
		tree.resumeEvents();  //重新开始所有监听事件的执行
	}

	//发送ajax请求
	function sendPostMethod(url,data){
		jQuery.ajax({type:"POST", url:url, data:data, beforeSend:function () {
		}, success:function (result) {
			if(result=="suceess"){
				// alert("已保存");
			}else{
				// alert("保存失败");
			}
		}, error:function (result) {
			//alert("保存失败");
		}});
	}
</script>
</body>
</html>
