<!DOCTYPE HTML>
<html>
<head>
    <#include "../common/hear.ftl">
    <title>组织详情</title>
    <style>
        .layer-container .form>div:last-child{
            margin-top: 30px;
        }
    </style>
</head>
<body>
<article class="page-container layer-container">
    <form class="form form-horizontal" id="form" action="${ctx}/back/org/add">
        <input type="hidden" name="id" value="${(info.id)!}">
        <div class="row cl">
            <label class="form-label col-xs-4 col-sm-3">组织名：</label>
            <div class="formControls col-xs-7 col-sm-8">
                <input type="text" name="name" class="input-text" autocomplete="off" value="${(info.name)!}" />
            </div>
        </div>

        <#if info?exists>
        <div class="row cl">
            <label class="form-label col-xs-4 col-sm-3">类型：</label>
            <div class="formControls col-xs-7 col-sm-8">
                ${info.level.desc}
            </div>
        </div>
        </#if>
        <#if !info?exists||info.parent?exists>
            <div class="row cl">
                <label class="form-label col-xs-4 col-sm-3"><span class="c-red">*</span>上级组织：</label>
                <div class="formControls col-xs-7 col-sm-8" onclick="tree_layer(this,'${ctx}')" id="union-organization">
                    <input type="text" class="input-text mb-10" autocomplete="off" value="${(info.parent.name)!}" placeholder="请选择组织" readonly  name="parent.name" >
                    <input type="hidden" name="parent.id" value="${(info.parent.id)!}">
                </div>
            </div>
        </#if>

        <div class="row cl">
            <div class="col-xs-8 col-sm-8 col-xs-offset-3 col-sm-offset-3">
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
            'name':{
                required:true,
            },
            'level':{
                required:true,
            }
        },
        onkeyup:false,
        focusCleanup:true,
        success:"valid",
        submitHandler:function(form){
            $(form).ajaxSubmit({
                dataType:"json",
                success: function(data){
                    layer.msg(data.msg,{icon:1,time:1000},function () {
                        if(data.status==1) {
                            window.parent.location.reload(true);
                        }
                    });
                }
            });
        }
    });
</script>
<!--/请在上方写此页面业务相关的脚本-->
</body>
</html>
