<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ include file="../common/taglibs.jsp" %>

<!DOCTYPE HTML>
<html>
<head>
	<%@ include file="../common/head.jsp" %>
	<title>系统管理-添加账号</title>
</head>
<body>
	<article class="page-container layer-container">
		<form class="form form-horizontal" id="form-submit">
			<c:if test="${not empty operator }">
				<input type="hidden" name="id" value="${operator.id }" id="id">
			</c:if>
			<div class="row cl">
				<label class="form-label col-xs-4 col-sm-3">账号：</label>
				<div class="formControls col-xs-6 col-sm-8">
					<input type="text" class="input-text" autocomplete="off" value="${operator.account }" placeholder="请输入账号" id="account" name="account">
				</div>
			</div>
			<div class="row cl">
				<label class="form-label col-xs-4 col-sm-3">姓名：</label>
				<div class="formControls col-xs-6 col-sm-8">
					<input type="text" class="input-text" autocomplete="off" value="${operator.name }" placeholder="请输入姓名" id="name" name="name">
				</div>
			</div>
			<c:if test="${empty operator }">
			<div class="row cl">
				<label class="form-label col-xs-4 col-sm-3">密码：</label>
				<div class="formControls col-xs-6 col-sm-8">
					<input type="text" class="input-text" autocomplete="off" placeholder="请输入密码" id="password" name="password" onfocus="this.type='password'">
				</div>
			</div>
			</c:if>
			<div class="row cl">
				<label class="form-label col-xs-4 col-sm-3">角色：</label>
				<div class="formControls col-xs-6 col-sm-8">
					<select class="select" name="role.id">
						<option value="0">请选择角色</option>
						<c:forEach items="${roleList }" var="role">
							<option value="${role.id }" <c:if test="${role.id == operator.role.id}">selected</c:if>>${role.name }</option>
						</c:forEach>
					</select>
				</div>
			</div>
            <div class="row cl member-dis">
                <label class="form-label col-xs-4 col-sm-3">组织关系：</label>
                <div class="formControls col-xs-7 col-sm-8">
                    <span class="search-box mr-20" onclick="tree_layer(this,'${ctx}')" id="field_1">
                        <input type="text" name="orgName" value="${operator.org.name }" class="input-text select_user" placeholder="请选择组织关系" readonly style="width: 210px">
                        <input type="hidden" name="org.id" value="${operator.org.id }">
                    </span>
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
					account: {
						required:true,
						isNullStr:true
					},
					name:{
						required:true,
						isNullStr:true
					},
					password: {
						required:true,
						stringCheck:true
					},
                    'role.id':{
                        required:true,
                    },
                    orgName:{
                        required:true,
                    }
				},
				messages: {
					account: "请输入账号",
					name: "请输入昵称",
					password:{
						required:"请输入密码",
						stringCheck: "密码为6-12位英文、数字、下划线"
					},
                    'role.id':"",
                    orgName:"",

				}
			});
			// 字符验证，只能包含英文、数字、下划线等字符。    
		    jQuery.validator.addMethod("stringCheck", function(value, element) {       
		         return this.optional(element) || /^[a-zA-Z0-9_]{6,12}$/.test(value);       
		    }, "只能包含英文、数字、下划线等字符");

			$("#submit").click(function(){
				if($("#form-submit").valid()){
					$("#submit").attr("disabled",true);
					var account = $("#account").val();
					var id = $("#id").val();
					if(id == undefined){
						data = {"account":account};
					}else{
						data = {"account":account,"id":id};
					}
					$.ajax({
						url:"${ctx}/back/operator/ajax/only",
						type:"post",
						data:data,
						dataType:"json",
						success:function(data){
							if(data.data){
								subForm("${ctx}","${ctx}/back/operator/ajax/addOrEdit")
							}else{
								layer.msg("账号已存在！",{icon:5,time:1000});
								$("#account").focus();
								$("#submit").attr("disabled",false);
							}
						}
					})
				}
			})
			//空格
			jQuery.validator.addMethod("isNullStr", function(value, element) {
			    var length = value.replace(/\s/g,"").length;
			    return this.optional(element) || (length!=0);
			}, "请输入有效字符");
		});
	</script>
	<!--/请在上方写此页面业务相关的脚本-->
</body>
</html>