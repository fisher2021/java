<!DOCTYPE HTML>
<html>
<head>
    <#include "../common/hear.ftl">
    <title>试卷详情</title>
    <style>
        .layer-container .form>div:last-child{
            margin-top: 30px;
        }
    </style>
</head>
<body>
<article class="page-container layer-container">
    <form class="form form-horizontal" id="form" action="${ctx}/back/exam/add">
        <input type="hidden" name="id" value="${(info.id)!}">
        <div class="row cl">
            <label class="form-label col-xs-4 col-sm-3">试卷名称：</label>
            <div class="formControls col-xs-7 col-sm-8">
                <input type="text" name="title" class="input-text" autocomplete="off" value="${(info.title)!}" />
            </div>
        </div>

        <div class="row cl">
            <label class="form-label col-xs-4 col-sm-3">及格分数：</label>
            <div class="formControls col-xs-7 col-sm-8">
                <input type="text" name="pass" class="input-text" autocomplete="off" value="${(info.pass)!}" />
            </div>
        </div>

        <div class="row cl">
            <label class="form-label col-xs-4 col-sm-3">重考次数：</label>
            <div class="formControls col-xs-7 col-sm-8">
                <input type="text" name="times" class="input-text" autocomplete="off" value="${(info.times)!}" />
            </div>
        </div>

        <div class="row cl">
            <label class="form-label col-xs-4 col-sm-3">考试时间：</label>
            <div class="formControls col-xs-7 col-sm-8">
                <input type="text" name="duration" class="input-text" autocomplete="off" value="${(info.duration)!}" />
            </div>
        </div>

        <div class="row cl">
            <label class="form-label col-xs-4 col-sm-3">有效时间：</label>
            <div class="formControls col-xs-7 col-sm-8">
                <input type="text" name="expire" class="input-text" placeholder="请输入有效时间" readonly onclick="WdatePicker({lang:'zh-cn',dateFmt:'yyyy/MM/dd'})" id="birth" style="margin-right: 0;" value="${(info.expire?string("yyyy/MM/dd"))!}">
            </div>
        </div>

        <div class="row cl">
            <label class="form-label col-xs-4 col-sm-3">试卷类型：</label>
            <div class="formControls col-xs-7 col-sm-8">
                <select class="select input-text radius va-m" style="min-width: 100px" name="type">
                    <#list type as t>
                    <option value="${t}" <#if info?exists&&info.type?exists&&info.type==t>selected</#if>>${t.desc}</option>
                    </#list>
                </select>
            </div>
        </div>

        <div class="row cl">
            <label class="form-label col-xs-4 col-sm-3">试卷描述：</label>
            <div class="formControls col-xs-7 col-sm-8">
                <textarea name="content" style="width:100%;height:250px">${(info.content)!''}</textarea>
            </div>
        </div>

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
            'title':{
                required:true,
            }
        },
        onkeyup:false,
        focusCleanup:true,
        success:"valid",
        submitHandler:function(form){
            $(form).ajaxSubmit({
                type: "post",  //提交方式
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
