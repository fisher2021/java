<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ include file="../common/taglibs.jsp" %>

<!DOCTYPE HTML>
<html>
<head>
    <%@ include file="../common/head.jsp" %>
	<title>党费修改</title>
</head>
<body>
<article class="page-container layer-container">
    <form class="form form-horizontal" id="form-submit" enctype="multipart/form-data" method="post">
        <c:if test="${not empty dues }">
            <input type="hidden" name="id" value="${dues.id }">
        </c:if>
        <div class="row cl">
            <label class="form-label col-xs-4 col-sm-3">姓名：</label>
            <div class="formControls col-xs-7 col-sm-8">
                <input type="text" class="input-text" autocomplete="off" value="${dues.user.nickname }" placeholder="请输入姓名"
                       id="user.nickname" name="user.nickname" readonly/>
            </div>
        </div>
        <div class="row cl">
            <label class="form-label col-xs-4 col-sm-3">应缴费：</label>
            <div class="formControls col-xs-7 col-sm-8">
                <input type="text" class="input-text" autocomplete="off" value="<fmt:formatNumber type="number" value="${dues.amount }" pattern="0.00" maxFractionDigits="2"/>" placeholder="请输入应缴费"
                       id="amount" name="amount"/>
            </div>
        </div>
        <div class="row cl">
            <label class="form-label col-xs-4 col-sm-3">实缴费：</label>
            <div class="formControls col-xs-7 col-sm-8">
                <input type="text" class="input-text" autocomplete="off" value="<fmt:formatNumber type="number" value="${dues.feeReceived }" pattern="0.00" maxFractionDigits="2"/>" placeholder="请输入实缴费"
                       id="feeReceived" name="feeReceived"/>
            </div>
        </div>
        <div class="row cl">
            <label class="form-label col-xs-4 col-sm-3">年份：</label>
            <div class="formControls col-xs-7 col-sm-8">
                <input type="text" class="input-text radius select_user" placeholder="请输入年份" readonly onclick="WdatePicker({lang:'zh-cn',dateFmt:'yyyy-MM'})" id="year" style="margin-right: 0;" name="year" value="${fn:substring(dues.year, 0, 7) }"></span>
            </div>
        </div>
        <div class="row cl member-dis">
            <label class="form-label col-xs-4 col-sm-3">状态：</label>
            <div class="formControls col-xs-7 col-sm-8">
                <input type="radio" name="status" value="0" <c:if test="${!dues.status}">checked</c:if>>未缴&nbsp;
                <input type="radio" name="status" value="1" <c:if test="${dues.status}">checked</c:if>>已缴
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
                    amount: {
						required:true,
					},
                    feeReceived:{
						required:true,
					},
                    year:{
                        required:true,
                    }
                },
                messages: {
                    amount: "请输入应缴费",
                    feeReceived: "请输入实缴费",
                    year: "",
                }
            });
            $("#submit").click(function(){
                if($("#form-submit").valid()){
                    $("#submit").attr("disabled",true);
                	$("form").ajaxSubmit(ajaxFormOption);
                }
            });
        });
        var ajaxFormOption = {
   			 type: "post",  //提交方式 
   			 dataType:"json",
   			 url: "${ctx }/back/dues/ajax/addOrEdit", //请求url
   			 success: function (data) { //提交成功的回调函数
                 layer.msg(data.msg,{icon:1,time:1000},function () {
                     if(data.status==1) {
                         window.parent.location.reload(true);
                     }
                 });
   			 }
   		};
    </script>
    <!--/请在上方写此页面业务相关的脚本-->
</body>
</html>