<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ include file="../common/taglibs.jsp" %>

<!DOCTYPE HTML>
<html>
<head>
    <%@ include file="../common/head.jsp" %>
	<title>党务日历添加</title>
</head>
<body>
<article class="page-container layer-container">
    <form class="form form-horizontal" id="form-submit" enctype="multipart/form-data" method="post">
        <c:if test="${not empty calendar }">
            <input type="hidden" name="id" value="${calendar.id }">
        </c:if>
        <div class="row cl">
            <label class="form-label col-xs-4 col-sm-3">指派时间：</label>
            <div class="formControls col-xs-7 col-sm-8">
                <input type="text" class="input-text radius select_user" placeholder="请选择指派时间" readonly onclick="WdatePicker({lang:'zh-cn',dateFmt:'yyyy-MM-dd'})" id="doDate" style="margin-right: 0;" name="doDate" value="${fn:substring(calendar.doDate, 0, 10) }"></span>
            </div>
        </div>
        <div class="row cl">
            <label class="form-label col-xs-4 col-sm-3">主题：</label>
            <div class="formControls col-xs-7 col-sm-8">
                <input type="text" class="input-text" autocomplete="off" value="${calendar.subject }" placeholder="请输入主题"
                       id="subject" name="subject"/>
            </div>
        </div>
        <div class="row cl cont">
            <label class="form-label col-xs-4 col-sm-3">详情：</label>
            <div class="formControls col-xs-7 col-sm-8">
                <textarea id="content" name="content" style="width:100%;height:150px">${calendar.content }</textarea>
            </div>
        </div>
        <c:if test="${sessionScope.get('logedOperator').org.level != branch}">
            <div class="row cl">
                <label class="form-label col-xs-4 col-sm-3">指派组织：</label>
                <div class="formControls col-xs-7 col-sm-8">
                    <table class="table table-border table-bordered table-bg table-striped">
                        <thead>
                        <tr class="text-c">
                            <th width="col-sm-1"><input type="checkbox"/></th>
                            <th width="col-sm-2">组织</th>
                        </tr>
                        </thead>
                        <tbody>
                        <c:forEach items="${orgList }" var="org">
                            <tr class="text-c">
                                <td><input type="checkbox" value="${org.id }" name="orgs"
                                        <c:forEach items="${orgChecked }" var="org_checked">
                                            <c:if test="${org_checked.id==org.id }">checked</c:if>
                                        </c:forEach>/><br></td>
                                <td>${org.name }</td>
                            </tr>
                            <span class="parent-title va-m"></span>
                        </c:forEach>
                        </tbody>
                    </table>
                </div>
            </div>
        </c:if>
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
    $().ready(function () {
        // 在键盘按下并释放及提交后验证提交表单
        $("#form-submit").validate({
            rules: {
                subject: {
                    required: true,
                    maxlength: 15,
                },
                content: {
                    required: true,
                    maxlength: 250,
                },
                doDate: {
                    required: true,
                }
            },
            messages: {
                content: "请输入详情",
                doDate:"",
            },
        });
        $("#submit").click(function () {
            if ($("#form-submit").valid()) {
                $("#submit").attr("disabled", true);
                $("form").ajaxSubmit(ajaxFormOption);
            }
        });
    });

    var ajaxFormOption = {
        type: "post",  //提交方式
        dataType: "json",
        url: "${ctx }/back/calendar  /ajax/addOrEdit", //请求url
        success: function (data) { //提交成功的回调函数
            layer.msg(data.msg, {icon: 1, time: 1000}, function () {
                if (data.status == 1) {
                    window.parent.location.reload(true);
                }
            });
        }
    };
    //空格
    jQuery.validator.addMethod("isNullStr", function (value, element) {
        var length = value.replace(/\s/g, "").length;
        return this.optional(element) || (length != 0);
    }, "请输入有效字符");
    //网址
    jQuery.validator.addMethod("isUrl", function (value, element) {
        var url = /^(http|ftp|https):\/\/\S+$/;
        return this.optional(element) || (url.test(value));
    }, "请输入正确网址");
</script>
<!--/请在上方写此页面业务相关的脚本-->
</body>
</html>