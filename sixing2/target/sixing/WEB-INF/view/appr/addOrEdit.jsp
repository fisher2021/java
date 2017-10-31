<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ include file="../common/taglibs.jsp" %>

<!DOCTYPE HTML>
<html>
<head>
    <%@ include file="../common/head.jsp" %>
    <title>民主评议添加</title>
    <style>
        .orgtable{
            margin: 20px 100px;
        }
    </style>
</head>
<body>
<div class="page-container layer-container">
    <form class="form form-horizontal" id="form-submit" enctype="multipart/form-data" method="post">
        <c:if test="${not empty appr }">
            <input type="hidden" name="id" value="${appr.id }">
        </c:if>
        <div class="row cl">
            <label class="form-label col-xs-4 col-sm-3">民主评议名称：</label>
            <div class="formControls col-xs-7 col-sm-8">
                <input type="text" class="input-text" autocomplete="off" value="${appr.title }" placeholder="请输入民主评议名称" id="title" name="title"/>
            </div>
        </div>
        <div class="row cl cont">
            <label class="form-label col-xs-4 col-sm-3">简介：</label>
            <div class="formControls col-xs-7 col-sm-8">
                <input type="text" class="input-text" autocomplete="off" value="${appr.content }" placeholder="请输入民主评议简介" id="content" name="content"/>
            </div>
        </div>
        <div class="row cl">
            <label class="form-label col-xs-4 col-sm-3">被评人：</label>
            <div class="formControls col-xs-7 col-sm-8">
                <div id="InputsWrapper">
                    <div><input type="text" name="option" id="field_1" value="" class="input-text" autocomplete="off" placeholder="请输入被评人" style="width: 70%"/>
                        <a href="#" rel="external nofollow" rel="external nofollow" rel="external nofollow" class="removeclass"><input type='button' class="btn" value='删除'></a>
                    </div>
                </div>
                <a href="#" rel="external nofollow" rel="external nofollow" rel="external nofollow" id="AddMoreFileBox" class="btn btn-info">添加更多的选项输入框</a></span></p>
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
        <c:if test="${sessionScope.get('logedOperator').org.level == branch}">
            <input type="hidden" name="orgs" value="${sessionScope.get('logedOperator').org.id }">
        </c:if>
        <div class="row cl">
            <div class="col-xs-7 col-sm-6 col-xs-offset-5 col-sm-offset-5">
                <button type="reset" class="btn btn-success size-M radius" id="reset">重置</button>
                <button type="button" class="btn btn-danger radius ml-50" id="submit">提交</button>
            </div>
        </div>
    </form>
</div>
<script type="text/javascript" src="${ctx }/static/back/admin/h-ui.admin/js/form.js"></script>
<script type="text/javascript" src="${ctx }/static/back/admin/h-ui.admin/js/checked.js"></script>
<!--请在下方写此页面业务相关的脚本-->
<script type="text/javascript">
    var uploadDomain = "${uploadDomain}";
    $().ready(function() {
        // 在键盘按下并释放及提交后验证提交表单
        $("#form-submit").validate({
            rules: {
                title: {
                    required:true,
                    isNullStr:true
                },
                option: {
                    required:true,
                },
                content:{
                    required:true,
                }
            },
            messages: {
                title: "请输入标题",
                option: "请输入被评人",
                content: "请输入备注"
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
        url: "${ctx }/back/appr/ajax/addOrEdit", //请求url
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
    //动态输入框
    var MaxInputs    = 8; //maximum input boxes allowed
    var InputsWrapper  = $("#InputsWrapper"); //Input boxes wrapper ID
    var AddButton    = $("#AddMoreFileBox"); //Add button ID
    var x = InputsWrapper.length; //initlal text box count
    var FieldCount=1; //to keep track of text box added
    $(AddButton).click(function (e) //on add input button click
    {
        if(x <= MaxInputs) //max input box allowed
        {
            FieldCount++; //text box added increment
            //add input box
            $(InputsWrapper).append('<div><input type="text" name="option" id="field_'+ FieldCount +'" value="" class="input-text" autocomplete="off" placeholder="请输入被评人" style="width: 70%"/>' +
                    '<a href="#" rel="external nofollow" rel="external nofollow" rel="external nofollow" class="removeclass"> <input type="button" class="btn" value="删除"></a></div>');

            x++; //text box increment
        }
        if(x > MaxInputs){
            alert("最多可添加9项");
        }
        return false;
    });
    $("body").on("click",".removeclass", function(e){ //user click on remove text
        if( x > 1 ) {
            $(this).parent('div').remove(); //remove text box
            x--; //decrement textbox
        }
        return false;
    })
</script>
<!--/请在上方写此页面业务相关的脚本-->
</body>
</html>
<%--<div class="row cl">--%>
<%--<div class="col-xs-12 col-sm-10 col-xs-offset-1 col-sm-offset-1">--%>
<%--<div class="authority-menu">--%>
<%--<c:forEach items="${orgList }" var="org">--%>
<%--<c:if test="${empty org.parent }">--%>
<%--<div class="parent-box">--%>
<%--<span class="authority-menu-icon authority-menu-icon-down"></span>--%>
<%--<span class="parent-title va-m">${org.name }</span>--%>
<%--<input type="checkbox" value="${org.id }" name="orgs" <c:forEach items="${orgChecked }" var="org_checked"><c:if test="${org_checked.id==org.id }">checked</c:if></c:forEach>/>--%>
<%--</div>--%>
<%--<div class="child-panel">--%>
<%--<c:forEach items="${orgList }" var="first_child">--%>
<%--<c:if test="${not empty first_child.parent && org.id==first_child.parent.id}">--%>
<%--<span class="authority-menu-icon authority-menu-icon-down"></span>--%>
<%--<span class="parent-title va-m">${first_child.name }</span>--%>
<%--<input type="checkbox" value="${first_child.id }" name="orgs" <c:forEach items="${orgChecked }" var="org_checked"><c:if test="${org_checked.id==first_child.id }">checked</c:if></c:forEach>/>--%>
<%--</c:if>--%>
<%--</c:forEach>--%>
<%--</div>--%>
<%--<div class="child-panel">--%>
<%--<c:forEach items="${orgList }" var="second_child">--%>
<%--<c:if test="${not empty second_child.parent.parent && org.id==second_child.parent.parent.id}">--%>
<%--<p>${second_child.name } <input type="checkbox" value="${second_child.id }" name="orgs" <c:forEach items="${orgChecked }" var="org_checked"><c:if test="${org_checked.id==second_child.id }">checked</c:if></c:forEach>/></p>--%>
<%--</c:if>--%>
<%--</c:forEach>--%>
<%--</div>--%>
<%--</c:if>--%>
<%--</c:forEach>--%>
<%--</div>--%>
<%--</div>--%>
<%--</div>--%>