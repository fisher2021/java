<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ include file="../common/taglibs.jsp" %>
<!DOCTYPE HTML>
<html>
<head>
    <%@ include file="../common/head.jsp" %>
    <title>组织详情</title>
    <style>
        .layer-container .form>div:last-child{
            margin-top: 30px;
        }
    </style>
</head>
<body>
<article class="page-container layer-container">
    <form class="form form-horizontal" id="form-submit" action="${ctx}/back/org/add" method="post">
        <c:if test="${not empty info }">
            <input type="hidden" name="id" value="${info.id }" id="id">
        </c:if>
        <div class="row cl">
            <label class="form-label col-xs-4 col-sm-3">组织名称：</label>
            <div class="formControls col-xs-7 col-sm-8">
                <input type="text" name="name" id="name" class="input-text" placeholder="请输入组织名称" autocomplete="off" value="${info.name}" />
            </div>
        </div>
        <c:if test="${empty info}">
            <div class="row cl">
                <label class="form-label col-xs-4 col-sm-3"><span class="c-red">*</span> 上级组织：</label>
                <div class="formControls col-xs-7 col-sm-8">
                     <span class="search-box mr-20" onclick="org_tree(this,'${ctx}')" id="change-to">
                    <input type="text" name="parent.name" value="${form.orgName }" class="input-text radius select_user" placeholder="请选择组织关系" readonly style="width: 325px">
                    <input type="hidden" name="parent.id" value="${form.orgId }" >
                </span>
                   <%-- <input type="text" class="input-text mb-10" autocomplete="off" value="${parent.name }"  readonly  name="parent.name" >
                    <input type="hidden" name="parent.id" value="${parent.id }">--%>
                </div>
            </div>
        </c:if>
        <c:if test="${not empty info}">
            <div class="row cl">
                <label class="form-label col-xs-4 col-sm-3"><span class="c-red">*</span> 组织级别：</label>
                <div class="formControls col-xs-7 col-sm-8">
                    <input type="text" class="input-text mb-10" autocomplete="off" value="${info.level.desc}"  readonly >
                </div>
            </div>
        </c:if>
        <div class="row cl">
            <label class="form-label col-xs-4 col-sm-3">换届日期：</label>
            <div class="formControls col-xs-7 col-sm-8">
                <input type="text" class="input-text radius select_user" value="${fn:substring(info.changeDate, 0, 10) }" placeholder="请选择换届日期" readonly onclick="WdatePicker({lang:'zh-cn',dateFmt:'yyyy-MM-dd'})" id="changeDate" style="margin-right: 0;" name="changeDate"></span>
            </div>
        </div>
        <div class="row cl">
            <div class="col-xs-8 col-sm-8 col-xs-offset-3 col-sm-offset-3">
                <button type="button" class="btn btn-danger radius ml-50" id="submit">提交</button>
            </div>
        </div>
    </form>
</article>
<script type="text/javascript" src="${ctx }/static/back/admin/h-ui.admin/js/form.js"></script>
<!--请在下方写此页面业务相关的脚本-->
<script type="text/javascript">
    //表单验证
    $("#form-submit").validate({
        rules:{
            name:{
                required:true,
            }
        },
    });
    $("#submit").click(function(){
        if($("#form-submit").valid()){
            $("#submit").attr("disabled",true);
            $("form").ajaxSubmit(ajaxFormOption);
        }
    });
    var ajaxFormOption = {
        type: "post",  //提交方式
        dataType:"json",
        url: "${ctx }/back/org/add", //请求url
        success: function (data) { //提交成功的回调函数
            layer.msg(data.msg,{icon:1,time:1000},function () {
                if(data.status==1) {
                    window.parent.location.reload(true);
                }else{
                    $("#submit").attr("disabled",false);
                }

            });
        }
    };
    function org_tree(that,ctx,callback){
        admin_add('组织结构',ctx+'/static/back/tree-layer.html?id='+that.id+'&fun='+callback,'210','350')
    }
</script>
<!--/请在上方写此页面业务相关的脚本-->
</body>
</html>
