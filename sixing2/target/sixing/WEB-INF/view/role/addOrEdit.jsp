<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ include file="../common/taglibs.jsp" %>

<!DOCTYPE HTML>
<html>
<head>
	<%@ include file="../common/head.jsp" %>
    <title>系统管理-新增角色</title>
</head>
<body>
	<article class="page-container layer-container">
		<form class="form form-horizontal" id="form-submit">
			<c:if test="${not empty role }">
				<input type="hidden" name="id" value="${role.id }">
			</c:if>
			<div class="row cl">
				<label class="form-label col-xs-4 col-sm-3">角色名称：</label>
				<div class="formControls col-xs-6 col-sm-8">
					<input type="text" class="input-text" autocomplete="off" value="${role.name }" placeholder="请输入角色名称" id="name" name="name">
				</div>
			</div>
			<div class="row cl">
				<div class="col-xs-9 col-sm-8 col-xs-offset-2 col-sm-offset-3">
					<button type="reset" class="btn btn-success size-M radius" id="reset" >重置</button>
					<button type="button" class="btn btn-danger radius ml-50" id="submit" >提交</button>
				</div>
			</div>
		</form>
	</article>
	<!--请在下方写此页面业务相关的脚本-->
	<script type="text/javascript">
		$().ready(function() {
			// 在键盘按下并释放及提交后验证提交表单
			$("#form-submit").validate({
				rules: {
					name: {
						required:true,
						isNullStr:true
					}
				},
				messages: {
					name: "请输入角色名称"
				}
			});
			$("#submit").click(function(){
				if($("#form-submit").valid()){
					subForm("${ctx}","${ctx}/back/role/ajax/addOrEdit");
				}
			});
			//空格
			jQuery.validator.addMethod("isNullStr", function(value, element) {
			    var length = value.replace(/\s/g,"").length;
			    return this.optional(element) || (length!=0);
			}, "请输入有效字符");
		});
	</script>
</body>
</html>