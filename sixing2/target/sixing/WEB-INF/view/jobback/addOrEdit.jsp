<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ include file="../common/taglibs.jsp" %>

<!DOCTYPE HTML>
<html>
<head>
    <%@ include file="../common/head.jsp" %>
	<title>工作展示添加</title>
</head>
<body>
<article class="page-container layer-container">
    <form class="form form-horizontal" id="form-submit" enctype="multipart/form-data" method="post">
        <c:if test="${not empty jobBack }">
            <input type="hidden" name="id" value="${jobBack.id }">
        </c:if>
        <div class="row cl">
            <label class="form-label col-xs-4 col-sm-3">活动图片一：</label>
            <div class="formControls col-xs-7 col-sm-8">
				<span class=" form-group">
				   <input type="hidden" name="image1" value="${jobBack.image1 }">
				   <input class="input-text upload-url " type="text"  placeholder="点击上传 注意尺寸" readonly />
				   <input type="file" multiple name="file1" class="input-file" style="width: 100%;"/>
				</span>
            </div>
        </div>
        <div class="row cl">
            <label class="form-label col-xs-4 col-sm-3">图一描述：</label>
            <div class="formControls col-xs-7 col-sm-8">
                <input type="text" class="input-text" autocomplete="off" value="${jobBack.desc1 }" placeholder="请输入图一描述"
                       id="desc1" name="desc1"/>
            </div>
        </div>
        <%--<c:if test="${not empty jobBack.image2 ||  empty jobBack}">--%>
        <div class="row cl">
            <label class="form-label col-xs-4 col-sm-3">活动图片二：</label>
            <div class="formControls col-xs-7 col-sm-8">
				<span class=" form-group">
				  <input type="hidden" name="image2" value="${jobBack.image2 }">
				  <input class="input-text upload-url " type="text"  placeholder="点击上传 注意尺寸" readonly />
				  <input type="file" multiple name="file2" class="input-file" style="width: 100%;"/>
				</span>
            </div>
        </div>
        <div class="row cl">
            <label class="form-label col-xs-4 col-sm-3">图二描述：</label>
            <div class="formControls col-xs-7 col-sm-8">
                <input type="text" class="input-text" autocomplete="off" value="${jobBack.desc2 }" placeholder="请输入图二描述"
                       id="desc2" name="desc2"/>
            </div>
        </div>
        <%--</c:if>--%>
        <div class="year row cl">
            <label class="form-label col-xs-4 col-sm-3">活动时间：</label>
            <div class="formControls col-xs-7 col-sm-8">
                <input type="text" class="input-text radius select_user" placeholder="请选择活动时间" readonly
                       onclick="WdatePicker({lang:'zh-cn',dateFmt:'yyyy-MM-dd HH:mm'})" id="activityDate" style="margin-right: 0;"
                       name="activityDate" value="${fn:substring(jobBack.activityDate, 0, 16) }"></span>
            </div>
        </div>
        <div class="row cl">
            <label class="form-label col-xs-4 col-sm-3">活动主题：</label>
            <div class="formControls col-xs-7 col-sm-8">
                <input type="text" class="input-text" autocomplete="off" value="${jobBack.subject }" placeholder="请输入活动主题"
                       id="subject" name="subject"/>
            </div>
        </div>
        <div class="row cl cont">
            <label class="form-label col-xs-4 col-sm-3">开展活动及事项详细描述：</label>
            <div class="formControls col-xs-7 col-sm-8">
                <textarea id="content" name="content" style="width:100%;height:300px">${jobBack.content }</textarea>
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
                	subject: {
						required:true,
                        maxlength: 50,
					},
                    content:{
						required:true,
                        maxlength: 1000,
					},
                    file1:{
                        required:${empty jobBack.image1},
                    },
                    desc1:{
                        required:true,
                        maxlength: 250,
                    },
                    desc2:{
                        maxlength: 250,
                    },
                    activityDate:{
                        required:true,
                    },
                },
                messages: {
                    file: "请至少上传一张图片",
                    activityDate:"",
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
   			 url: "${ctx }/back/job/ajax/addOrEdit", //请求url
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