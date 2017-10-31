<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ include file="../common/taglibs.jsp" %>

<!DOCTYPE HTML>
<html>
<head>
    <%@ include file="../common/head.jsp" %>
	<title>党员信息管理-编辑信息</title>
</head>
<body>
    <article class="page-container layer-container">
        <form class="form form-horizontal" id="form-submit" enctype="multipart/form-data">
        	<c:if test="${not empty user }">
				<input type="hidden" name="id" value="${user.id }" id="id">
			</c:if>
            <div class="row cl">
                <label class="form-label col-xs-4 col-sm-3">姓名：</label>
                <div class="formControls col-xs-7 col-sm-8">
                    <input type="text" name="nickname" class="input-text" autocomplete="off" value="${user.nickname }" placeholder="请输入姓名" id="nickname"  />
                </div>
            </div>
            <div class="row cl">
                <label class="form-label col-xs-4 col-sm-3">头像：</label>
                <div class="formControls col-xs-7 col-sm-8">
				<span class=" form-group">
				  <input type="hidden" name="image" value="${user.image }">
				  <input class="input-text upload-url " type="text"  placeholder="点击上传 注意尺寸"  />
				  <input type="file" multiple name="file" class="input-file" style="width: 100%;"/>
				</span>
                </div>
            </div>
            <div class="row cl member-dis">
                <label class="form-label col-xs-4 col-sm-3">性别：</label>
                <div class="formControls col-xs-7 col-sm-8">
                    <input type="radio" name="sex" value="男" <c:if test="${user.sex == '男' }">checked</c:if>>男&nbsp;
                    <input type="radio" name="sex" value="女" <c:if test="${user.sex =='女' }">checked</c:if>>女
                </div>
            </div>
            <div class="row cl">
                <label class="form-label col-xs-4 col-sm-3">籍贯：</label>
                <div class="formControls col-xs-7 col-sm-8">
                    <input type="text" name="nativePlace" class="input-text" autocomplete="off" value="${user.nativePlace }" placeholder="请输入籍贯" id="nativePlace"  />
                </div>
            </div>
            <div class="row cl">
                <label class="form-label col-xs-4 col-sm-3">民族：</label>
                <div class="formControls col-xs-7 col-sm-8">
                    <input type="text" name="nation" class="input-text" autocomplete="off" value="${user.nation }" placeholder="请输入民族" id="nation"  />
                </div>
            </div>
            <div class="row cl">
                <label class="form-label col-xs-4 col-sm-3">岗位：</label>
                <div class="formControls col-xs-7 col-sm-8">
                    <input type="text" name="job" class="input-text" autocomplete="off" value="${user.job }" placeholder="请输入岗位" id="job"  />
                </div>
            </div>
            <div class="row cl">
                <label class="form-label col-xs-4 col-sm-3">学历：</label>
                <div class="formControls col-xs-7 col-sm-8">
                    <select name="education" id="education">
                        <option value="">请选择学历</option>
                        <c:forEach items="${education }" var="education">
                            <option value="${education }" <c:if test="${education == user.education }">selected</c:if>>${education.desc }</option>
                        </c:forEach>
                    </select>
                </div>
            </div>
            <div class="row cl">
                <label class="form-label col-xs-4 col-sm-3">困难党员：</label>
                <div class="formControls col-xs-7 col-sm-8">
                    <input type="radio" class="mr-5" name="difficult" id="yes" value="true"
                           <c:if test="${user.difficult }">checked</c:if>/><label class="mr-10" for="yes">是</label>
                    <input type="radio" class="mr-5" name="difficult" id="no" value="false"
                           <c:if test="${not user.difficult }">checked</c:if>/><label class="mr-10" for="no">否</label>
                </div>
            </div>
            <div class="row cl">
                <label class="form-label col-xs-4 col-sm-3">出生年月：</label>
                <div class="formControls col-xs-7 col-sm-8">
                    <input type="text" class="input-text radius select_user" placeholder="请输入出生年月" readonly onclick="WdatePicker({lang:'zh-cn',dateFmt:'yyyy-MM'})" id="birth" style="margin-right: 0;" name="birth" value="${fn:substring(user.birth, 0, 7) }"></span>
                </div>
            </div>
            <div class="row cl">
                <label class="form-label col-xs-4 col-sm-3">入党时间：</label>
                <div class="formControls col-xs-7 col-sm-8">
                    <input type="text" class="input-text radius select_user" placeholder="请输入入党时间" readonly onclick="WdatePicker({lang:'zh-cn',dateFmt:'yyyy-MM'})" id="partyTime" style="margin-right: 0;" name="partyTime" value="${fn:substring(user.partyTime, 0, 7) }"></span>
                </div>
            </div>
            <div class="row cl">
                <label class="form-label col-xs-4 col-sm-3">党内职务：</label>
                <div class="formControls col-xs-7 col-sm-8">
                    <input type="text" class="input-text" autocomplete="off" value="${user.duty }" placeholder="请输入党内职务" id="duty" name="duty" />
                </div>
            </div>
            <div class="row cl">
                <label class="form-label col-xs-4 col-sm-3">联系电话：</label>
                <div class="formControls col-xs-7 col-sm-8">
                    <input type="text" class="input-text" autocomplete="off" value="${user.phone }" placeholder="请输入联系电话" id="phone" name="phone" />
                </div>
            </div>
            <%--<div class="row cl">--%>
                <%--<label class="form-label col-xs-4 col-sm-3">身份证号：</label>--%>
                <%--<div class="formControls col-xs-7 col-sm-8">--%>
                    <%--<input type="text" class="input-text" autocomplete="off" value="${user.idCard }" placeholder="请输入身份证号" id="idCard" name="idCard" />--%>
                <%--</div>--%>
            <%--</div>--%>
            <%--<div class="row cl">--%>
                <%--<label class="form-label col-xs-4 col-sm-3">现居住地：</label>--%>
                <%--<div class="formControls col-xs-7 col-sm-8">--%>
                    <%--<input type="text" class="input-text" autocomplete="off" value="${user.address }" placeholder="请输入现居住地" id="address" name="address" />--%>
                <%--</div>--%>
            <%--</div>--%>
            <%--<div class="row cl">--%>
                <%--<label class="form-label col-xs-4 col-sm-3">紧急联系人姓名：</label>--%>
                <%--<div class="formControls col-xs-7 col-sm-8">--%>
                    <%--<input type="text" class="input-text" autocomplete="off" value="${user.contact }" placeholder="请输入紧急联系人姓名" id="contact" name="contact" />--%>
                <%--</div>--%>
            <%--</div>--%>
            <%--<div class="row cl">--%>
                <%--<label class="form-label col-xs-4 col-sm-3">紧急联系人电话：</label>--%>
                <%--<div class="formControls col-xs-7 col-sm-8">--%>
                    <%--<input type="text" class="input-text" autocomplete="off" value="${user.contactMobile }" placeholder="请输入紧急联系人电话" id="contactMobile" name="contactMobile" />--%>
                <%--</div>--%>
            <%--</div>--%>
            <div class="row cl cont">
                <label class="form-label col-xs-4 col-sm-3">党内培训记录：</label>
                <div class="formControls col-xs-7 col-sm-8">
                    <textarea id="train" name="train" style="width:100%;height:50px">${user.train }</textarea>
                </div>
            </div>
            <div class="row cl cont">
                <label class="form-label col-xs-4 col-sm-3">奖励记录：</label>
                <div class="formControls col-xs-7 col-sm-8">
                    <textarea id="award" name="award" style="width:100%;height:50px">${user.award }</textarea>
                </div>
            </div>
            <div class="row cl cont">
                <label class="form-label col-xs-4 col-sm-3">惩罚记录：</label>
                <div class="formControls col-xs-7 col-sm-8">
                    <textarea id="punishment" name="punishment" style="width:100%;height:50px">${user.punishment }</textarea>
                </div>
            </div>
            <div class="row cl">
                <div class="col-xs-9 col-sm-8 col-xs-offset-3 col-sm-offset-3">
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
                    nickname: {
                        required: true
                    },
                    phone: {
                        isMobile:true
                    },
                },
                messages: {
                    nickname: {
                        required: "请输入姓名",
                    },
                },
            });
            // 手机号码验证
            jQuery.validator.addMethod("isMobile", function(value, element) {
                var length = value.length;
                return this.optional(element) || (length == 11 && /^(((13[0-9]{1})|(15[0-35-9]{1})|(17[0-9]{1})|(18[0-9]{1}))+\d{8})$/.test(value));
            }, "请正确填写您的手机号码。");
            $("#submit").click(function(){
                if($("#form-submit").valid()){
                    $("form").ajaxSubmit(ajaxFormOption);
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
   			url: "${ctx }/back/partymember/ajax/addOrEdit", //请求url
   			success: function (data) { //提交成功的回调函数  
                layer.msg(data.msg,{icon:1,time:1000},function () {
                    if(data.status==1) {
                        window.parent.location.reload(true);
                    }
                });
			}
		};
      //空格
		jQuery.validator.addMethod("isNullStr", function(value, element) {
		    var length = value.replace(/\s/g,"").length;
		    return this.optional(element) || (length!=0);
		}, "请输入有效字符");
    </script>
</body>
</html>