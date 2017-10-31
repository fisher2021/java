<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ include file="../common/taglibs.jsp" %>

<!DOCTYPE HTML>
<html>
<head>
    <%@ include file="../common/head.jsp" %>
    <title>安卓版本添加</title>
</head>
<body>
<article class="page-container layer-container">
    <form class="form form-horizontal" id="form-submit" enctype="multipart/form-data" method="post">
        <c:if test="${not empty appVersion }">
            <input type="hidden" name="id" value="${appVersion.id }">
        </c:if>
        <div class="row cl">
            <label class="form-label col-xs-4 col-sm-3">APP类型：</label>
            <div class="formControls col-xs-6 col-sm-8">
                <select name="type" id="type" >
                    <option value="1">苹果</option>
                    <option value="0">安卓</option>
                </select>
            </div>
        </div>
        <div class="row cl">
            <label class="form-label col-xs-4 col-sm-3">版本号：</label>
            <div class="formControls col-xs-7 col-sm-8">
                <input type="text" class="input-text" autocomplete="off" value="${appVersion.version }" placeholder="请输入版本号" id="version" name="version"/>
            </div>
        </div>
        <div class="row cl">
            <label class="form-label col-xs-4 col-sm-3">版本码：</label>
            <div class="formControls col-xs-7 col-sm-8">
                <input type="text" class="input-text" autocomplete="off" value="${appVersion.code }" placeholder="请输入版本码" id="code" name="code"/>
            </div>
        </div>
        <div class="row cl cont">
            <label class="form-label col-xs-4 col-sm-3">更新日志：</label>
            <div class="formControls col-xs-7 col-sm-8">
                <textarea id="content" name="content" style="width:100%;height:100px"  placeholder="请输入更新日志" >${appVersion.content }</textarea>
            </div>
        </div>
        <div class="row cl link">
            <label class="form-label col-xs-4 col-sm-3">路径：</label>
            <div class="formControls col-xs-7 col-sm-8">
                <input type="text" class="input-text" autocomplete="off" value="${appVersion.url }" placeholder="请输入路径" id="url" name="url"/>
            </div>
        </div>
        <div class="row cl">
            <div class="col-xs-9 col-sm-7 col-xs-offset-3 col-sm-offset-4">
                <button type="reset" class="btn btn-success size-M radius" id="reset">重置</button>
                <button type="button" class="btn btn-danger radius ml-50" id="submit">发布</button>
            </div>
        </div>
    </form><br/>
</article>
<script type="text/javascript" src="${ctx }/static/back/admin/h-ui.admin/js/form.js"></script>
<!--请在下方写此页面业务相关的脚本-->
<script type="text/javascript">
    $().ready(function() {
        // 在键盘按下并释放及提交后验证提交表单
        $("#form-submit").validate({
            rules: {
                version:{
                    required:true,
                    maxlength:250
                },
                code:{
                    required:true,
                    maxlength:250
                },
                content:{
                    required:true,
                    maxlength:250
                },
                url:{
                    required:true
                }
            }
        });
        $("#submit").click(function(){
            if($("#form-submit").valid()){
                $("form").ajaxSubmit(ajaxFormOption);
            }
        });
    });

    var ajaxFormOption = {
        type: "post",  //提交方式
        dataType:"json",
        url: "${ctx }/back/appVersion/ajax/addOrEdit", //请求url
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