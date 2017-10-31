<!DOCTYPE HTML>
<html>
<head>
    <#include "../common/hear.ftl">
    <title>积分配置</title>
    <style>
        .layer-container .form>div:last-child{
            margin-top: 30px;
        }
    </style>
</head>
<body>
<nav class="breadcrumb">
    <i class="Hui-iconfont">&#xe67f;</i> 首页 <span class="c-gray en">&gt;</span> 积分管理 <span  class="c-error"><span class="c-gray en">&gt;</span> 积分管理 </span>
    <a class="btn btn-success radius r"  href="javascript:location.replace(location.href);" title="刷新" ><i class="Hui-iconfont">&#xe68f;</i></a>
</nav>
<article class="page-container layer-container" style="margin-left: 5%">
    <form class="form form-horizontal" id="form" action="${ctx}/back/points/update">

        <#list list as item>

            <#if item_index==0>
                <div class="row cl" style="display: block;width: 50%">
                    <label class="form-label col-xs-4 col-sm-3"><strong>${item.type.dictName}：</strong></label>
                    <div class="formControls col-xs-7 col-sm-8">
                        <input type="text" name="${item.type.dictId}" class="input-text" autocomplete="off" value="${(item.value)!}" style="width: 200px"/>
                    </div>
                </div>
                <#else >
                <div class="row cl" style="display: inline-block;width: 50%">
                    <label class="form-label col-xs-4 col-sm-3">${item.type.dictName}：</label>
                    <div class="formControls col-xs-7 col-sm-8">
                        <input type="text" name="${item.type.dictId}" class="input-text" autocomplete="off" value="${(item.value)!}" style="width: 200px"/>
                    </div>
                </div>
            </#if>

        </#list>
        <div class="row cl">
            <div class="col-xs-8 col-sm-8 col-xs-offset-4 col-sm-offset-4">
                <button type="submit" class="btn btn-danger radius ml-50" id="submit">提交</button>
            </div>
        </div>
    </form>
</article>

<!--请在下方写此页面业务相关的脚本-->
<script type="text/javascript">
    //表单验证
    $("#form").validate({
        rules:{
            points:{
                required:true,
                number:true
            },
            <#list list as item>
            ${item.id}:{
                required:true,
                number:true
            },
            </#list>
        },
        onkeyup:false,
        focusCleanup:true,
        success:"valid",
        submitHandler:function(form){
            $(form).ajaxSubmit({
                dataType:"json",
                success: function(data){
                    layer.msg(data.msg,{icon:1,time:1000});
                }
            });
        }
    });
</script>
<!--/请在上方写此页面业务相关的脚本-->
</body>
</html>
