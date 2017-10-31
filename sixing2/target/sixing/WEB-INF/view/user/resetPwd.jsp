<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ include file="../common/taglibs.jsp" %>

<!DOCTYPE HTML>
<html>
<head>
	<%@ include file="../common/head.jsp" %>
	<title>修改密码</title>
</head>
<body>
	<article class="page-container layer-container">
		<form class="form form-horizontal" id="form-submit">
		<input type="hidden" name="id" id="id" value="${user.id }">
			<input type="hidden" name="account" id="account" value="${user.account }">
		<div class="row cl">
			<label class="form-label col-xs-4 col-sm-3">密码：</label>
			<div class="formControls col-xs-7 col-sm-8">
				<input type="password" class="input-text input-width radius" style="width:180px;" autocomplete="off" placeholder="请输入密码" name="password" id="password"/>
			</div>
		</div>
		<div class="row cl modify-row">
			<div class="col-xs-9 col-sm-8 col-xs-offset-3 col-sm-offset-3">
				<button type="reset" class="btn btn-success size-M radius" id="reset">重置</button>
				<button type="button" class="btn btn-danger radius ml-50" id="submit">提交</button>
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
					password: {
						required:true,
						stringCheck:true
					}
				},
				messages: {
					password:{
						required:"请确认新密码",
						stringCheck: "密码为6-12位英文、数字、下划线"
					}
				}
			});     
		    // 字符验证，只能包含英文、数字、下划线等字符。    
		    jQuery.validator.addMethod("stringCheck", function(value, element) {       
		         return this.optional(element) || /^[a-zA-Z0-9_]{6,12}$/.test(value);       
		    }, "只能包含中文、英文、数字、下划线等字符"); 
			
			$("#submit").click(function(){
				if($("#form-submit").valid()){
					$.ajax({
						url:"${ctx}/back/user/ajax/resetPwd",
						data:$('form').serialize(),
						type:"post",
						dataType:"json",
						success:function(data){
							layer.msg(data.msg,{icon:1,time:1000},function () {
								if(data.status==1) {
									window.parent.location.reload(true);
								}
							});
						}
					})
				}
			})
		});
	</script>
	<!--/请在上方写此页面业务相关的脚本-->
</body>
</html>