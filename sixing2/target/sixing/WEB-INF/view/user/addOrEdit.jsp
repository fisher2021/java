<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ include file="../common/taglibs.jsp" %>

<!DOCTYPE HTML>
<html>
<head>
    <%@ include file="../common/head.jsp" %>
	<title>用户管理-新增用户</title>
</head>
<body>
    <article class="page-container layer-container">
        <form class="form form-horizontal" id="form-submit" enctype="multipart/form-data">
        	<c:if test="${not empty user }">
				<input type="hidden" name="id" value="${user.id }" id="id">
			</c:if>
            <c:if test="${sessionScope.get('logedOperator').org.level != branch}">
            <div class="row cl member-dis">
                <label class="form-label col-xs-4 col-sm-3">组织关系：</label>
                <div class="formControls col-xs-7 col-sm-8">
                    <div id="InputsWrapper">
                        <c:if test="${(not empty user)&&(not empty user.orgs)}">
                            <c:forEach items="${user.orgs }" var="org" varStatus="status">
                                <div class="orgdiv">
                                    <span class="search-box mr-20" onclick="tree_layer(this,'${ctx}')" id="field_${status.count}">
                                        <input type="text" name="orgName" value="${org.name}" class="input-text radius select_user" placeholder="请选择组织关系" readonly style="width: 350px">
                                        <input type="hidden" name="org" value="${org.id}">
                                    </span>
                                    <a href="#" rel="external nofollow" rel="external nofollow" rel="external nofollow" class="removeclass"><input type='button' class="btn" value='删除'></a>
                                </div>
                            </c:forEach>
                        </c:if>
                        <c:if test="${(empty user)||(empty user.orgs)}">
                            <div class="orgdiv">
                                    <span class="search-box mr-20" onclick="tree_layer(this,'${ctx}')" id="field_4">
                                        <input type="text" name="orgName" value="${org.name}" class="input-text radius select_user" placeholder="请选择组织关系" readonly style="width: 350px">
                                        <input type="hidden" name="org" value="${org.id}">
                                    </span>
                                <a href="#" rel="external nofollow" rel="external nofollow" rel="external nofollow" class="removeclass"><input type='button' class="btn" value='删除'></a>
                            </div>
                        </c:if>
                    </div>
                    <a href="#" rel="external nofollow" rel="external nofollow" rel="external nofollow" id="AddMoreFileBox" class="btn btn-info">添加组织</a></span></p>
                </div>
            </div>
            </c:if>
            <c:if test="${sessionScope.get('logedOperator').org.level == branch}">
                <div class="row cl member-dis">
                    <label class="form-label col-xs-4 col-sm-3">组织关系：</label>
                    <div class="formControls col-xs-7 col-sm-8">
                        <input type="text" name="orgName" value="${sessionScope.get('logedOperator').org.name }" class="input-text radius select_user" placeholder="请选择组织关系" readonly style="width: 350px">
                        <input type="hidden" name="org" value="${sessionScope.get('logedOperator').org.id }">
                    </div>
                </div>
            </c:if>
            <c:if test="${empty user }">
                <div class="row cl">
                    <label class="form-label col-xs-4 col-sm-3" >用户名（手机号）：</label>
                    <div class="formControls col-xs-7 col-sm-8">
                        <input type="text" class="input-text radius" style="width:180px;" autocomplete="off" value="${user.account }" placeholder="请输入用户名（手机号）" id="account" name="account" />
                    </div>
                </div>
                <div class="row cl member-num">
                    <label class="form-label col-xs-4 col-sm-3" >密码：</label>
                    <div class="formControls col-xs-7 col-sm-8">
                        <input type="text" onfocus="this.type='password'" class="input-text radius" style="width:180px;" autocomplete="off"  placeholder="请输入密码"  name="password" id="password"/>
                    </div>
                </div>
            </c:if>
            <div class="row cl member-dis">
                <label class="form-label col-xs-4 col-sm-3">是否是领导：</label>
                <div class="formControls col-xs-7 col-sm-8">
                    <select name="leader" id="leader" class="radius" style="width:180px;">
                        <option value="0" <c:if test="${!user.leader}">selected</c:if>>否</option>
                        <option value="1" <c:if test="${user.leader}">selected</c:if>>是</option>
                    </select>
                </div>
            </div>
            <div class="row cl">
                <div class="col-xs-9 col-sm-8 col-xs-offset-3 col-sm-offset-4">
                    <button type="reset" class="btn btn-success size-M radius" id="reset">重置</button>
                    <button type="button" class="btn btn-danger radius ml-50" id="submit">提交</button>
                </div>
            </div>
        </form>
    </article>
 	<script type="text/javascript" src="${ctx }/static/back/admin/h-ui.admin/js/form.js"></script>
    <!--请在下方写此页面业务相关的脚本-->
    <script type="text/javascript">
        $().ready(function() {
            // 在键盘按下并释放及提交后验证提交表单
            $("#form-submit").validate({
                rules: {
                    account: {
                        required: true,
                        isMobile:true
                    },
                    leader: {
                        required: true,
                    },
                    password: {
                        required: true,
                        stringCheck:true
                    },
                    orgName:{
						required:true,
					}
                },
                messages: {
                    account: {
                        required: "请输入手机号",
                        isPhone: "请输入正确的电话号码"
                    },
                    orgName:"",
                    leader:"请选择是否是领导",
                    password:{
                        required:"请输入密码",
                        stringCheck: "密码为6-12位英文、数字、下划线"
                    }
                }
            });
            // 手机号码验证
            jQuery.validator.addMethod("isMobile", function(value, element) {
                var length = value.length;
                return this.optional(element) || (length == 11 && /^(((13[0-9]{1})|(15[0-35-9]{1})|(17[0-9]{1})|(18[0-9]{1}))+\d{8})$/.test(value));
            }, "请正确填写您的手机号码。");
            // 字符验证，只能包含英文、数字、下划线等字符。
            jQuery.validator.addMethod("stringCheck", function(value, element) {
                return this.optional(element) || /^[a-zA-Z0-9_]{6,12}$/.test(value);
            }, "只能包含中文、英文、数字、下划线等字符");
            $("#submit").click(function(){
                if($("#form-submit").valid()){
                    $("#submit").attr("disabled",true);
                	var account = $("#account").val();
					var id = $("#id").val();
					if(id == undefined){
						//执行新增
						$.ajax({
							url:"${ctx}/back/user/ajax/only",
							type:"post",
							data:{"account" : account},
							dataType:"json",
							success:function(data){
								if(data.data){
									$("form").ajaxSubmit(ajaxFormOption);
								}else {
									layer.msg("用户名已存在！",{icon:3,time:2000});
									$("#phone").focus();
                                    $("#submit").attr("disabled",false);
								}
							}
						})
					}else{
						//执行保存
						$("form").ajaxSubmit(ajaxFormOption);
					}
                }
            });
            
            if($("#common-user").is(":checked")){
                $(".member-num").css("display","none");
                $(".member-dis").css("display","none");
            }else{
                $(".member-num").css("display","block");
                $(".member-dis").css("display","block");
            }
            
            $("input:radio").click(function(){
            	if($("#common-user").is(":checked")){
                    $(".member-num").css("display","none");
                    $(".member-dis").css("display","none");
                }else{
                    $(".member-num").css("display","block");
                    $(".member-dis").css("display","block");
                }
            });
        });
        
        var ajaxFormOption = {
   			type: "post",  //提交方式 
   			dataType:"json",
   			url: "${ctx }/back/user/ajax/addOrEdit", //请求url
   			success: function (data) { //提交成功的回调函数  
                layer.msg(data.msg,{icon:1,time:1000},function () {
                    if(data.status==1) {
                        window.parent.location.reload(true);
                    }else {
                        $("#submit").attr("disabled",false);
                    }
                });
			}
		};
      //空格
		jQuery.validator.addMethod("isNullStr", function(value, element) {
		    var length = value.replace(/\s/g,"").length;
		    return this.optional(element) || (length!=0);
		}, "请输入有效字符");
        //动态输入框
        var MaxInputs    = 2; //maximum input boxes allowed
        var InputsWrapper  = $("#InputsWrapper"); //Input boxes wrapper ID
        var AddButton    = $("#AddMoreFileBox"); //Add button ID
//        var x = InputsWrapper.length; //initlal text box count
        var x = $('.orgdiv').length;
        var FieldCount=5; //to keep track of text box added
        $(AddButton).click(function (e) //on add input button click
        {
            if(x > MaxInputs){
                alert("最多可添加3项");
                return false;
            }else {
                FieldCount++; //text box added increment
                //add input box
                var url = "'${ctx}'";
                $(InputsWrapper).append('<div class="orgdiv"><span class="search-box mr-20" onclick="tree_layer(this,'+url+')" id="field_'+ FieldCount +'"> ' +
                    '<input type="text" name="orgName" class="input-text radius select_user" placeholder="请选择组织关系" readonly style="width: 350px"> ' +
                    '<input type="hidden" name="org"></span> ' +
                    '<a href="#" rel="external nofollow" rel="external nofollow" rel="external nofollow" class="removeclass"><input type="button" class="btn" value="删除"></a></div>');
                x++; //text box increment
            }
        });
        $("body").on("click",".removeclass", function(e){ //user click on remove text
            if( x > 1 ) {
                $(this).parent('div').remove(); //remove text box
                x--; //decrement textbox
            }
            return false;
        })
    </script>
</body>
</html>