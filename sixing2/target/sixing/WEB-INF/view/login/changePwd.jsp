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
		<div class="row cl">
			<label class="form-label col-xs-4 col-sm-3">原密码：</label>
			<div class="formControls col-xs-7 col-sm-8">
				<input type="password" class="input-text" autocomplete="off" placeholder="请输入原密码" name="oldPwd" id="oldPwd"/>
			</div>
		</div>
		<div class="row cl">
			<label class="form-label col-xs-4 col-sm-3">新密码：</label>
			<div class="formControls col-xs-7 col-sm-8">
				<input type="password" class="input-text" autocomplete="off" placeholder="请输入新密码" name="password" id="password"/>
			</div>
		</div>
		<div class="row cl">
			<label class="form-label col-xs-4 col-sm-3">确认新密码：</label>
			<div class="formControls col-xs-7 col-sm-8">
				<input type="password" class="input-text" autocomplete="off" placeholder="请确认新密码" name="passwordVer" />
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
					oldPwd:"required",
					password: {
						required:true,
						stringCheck:true
					},
					passwordVer:{
						required:true,
						equalTo: "#password"
					}
				},
				messages: {
					oldPwd:"请输入原密码",
					password:{
						required:"请确认新密码",
						stringCheck: "密码为6-12位英文、数字、下划线"
					},
					passwordVer: {
						required:"请确认新密码",
						equalTo: "两次密码输入不一致"
					}
				}
			});     
		    // 字符验证，只能包含英文、数字、下划线等字符。    
		    jQuery.validator.addMethod("stringCheck", function(value, element) {       
		         return this.optional(element) || /^[a-zA-Z0-9_]{6,12}$/.test(value);       
		    }, "只能包含中文、英文、数字、下划线等字符"); 
			
			$("#submit").click(function(){
				if($("#form-submit").valid()){
					$("#submit").attr("disabled",true);
					$.ajax({
						url:"${ctx}/back/admin/ajax/changePwd",
						data:$('form').serialize(),
						type:"post",
						dataType:"json",
						success:function(data){
                            if(data.data){
                                layer.msg('密码修改成功，请重新登录!',{icon:1,time:1000});
                                <%--window.open("${ctx}/back/admin/logout","_top");--%>
                                setTimeout("window.open(${ctx}'/back/admin/logout','_top')",1000);
                            }else{
                                layer.msg(data.msg,{icon:2,time:2000});
//                                setTimeout("window.parent.location.reload(true)",1000);
                            }
						}
					})
				}
			})
		});
	</script>
	<!--/请在上方写此页面业务相关的脚本-->
</body>
</html>