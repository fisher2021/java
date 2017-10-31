<!DOCTYPE HTML>
<html>
<head>
    <#include "../../common/hear.ftl">
    <title>试题详情</title>
    <style>
        .layer-container .form>div:last-child{
            margin-top: 30px;
        }
    </style>
</head>
<body>
<article class="page-container layer-container">
    <form class="form form-horizontal" id="form" action="${ctx}/back/questions/add">
        <input type="hidden" name="id" value="${(info.id)!}">
        <input type="hidden" name="options" id="options">
        <input type="hidden" name="optionsResult" id="optionsResult">
        <div class="row cl">
            <label class="form-label col-xs-4 col-sm-3"> 题目：</label>
            <div class="formControls col-xs-7 col-sm-8">
                <input type="text" name="title" class="input-text" autocomplete="off" value="${(info.title)!}" />
            </div>
        </div>

        <div class="row cl">
            <label class="form-label col-xs-4 col-sm-3">类型：</label>
            <div class="formControls col-xs-7 col-sm-8">
                <select class="select input-text radius va-m" style="min-width: 100px" name="examType">
                <#list type as t>
                    <option value="${t}" <#if info?exists&&info.examType?exists&&info.examType==t>selected</#if>>${t.desc}</option>
                </#list>
                </select>
            </div>
        </div>

        <div class="row cl">
            <label class="form-label col-xs-4 col-sm-3">题型：</label>
            <div class="formControls col-xs-7 col-sm-8">
                <select class="select input-text radius va-m" style="min-width: 100px" name="type" id="type" onchange="changeType()">
                <#list questionsType as t>
                    <option value="${t}" <#if info?exists&&info.type?exists&&info.type==t>selected</#if>>${t.desc}</option>
                </#list>
                </select>
            </div>
        </div>

        <div class="row cl">
            <label class="form-label col-xs-4 col-sm-3"> 分数：</label>
            <div class="formControls col-xs-7 col-sm-8">
                <input type="text" name="score" class="input-text" autocomplete="off" value="${(info.score)!}" />
            </div>
        </div>

        <div class="row cl" id="judge" style="display: none">
            <label class="form-label col-xs-4 col-sm-3"> 判断：</label>
            <div class="formControls col-xs-7 col-sm-8">
                <input type="radio" name="judge" checked value="1"><label class="ml-5">对</label>
                <input type="radio" name="judge" class="ml-20" value="0" ><label class="ml-5">错</label>
            </div>
        </div>

        <div class="row cl" id="select" style="display: none">
            <label class="form-label col-xs-4 col-sm-3"> 选项：</label>
            <div class="formControls col-xs-7 col-sm-8">
                <table class="table table-border table-bordered table-bg table-striped">
                    <thead>
                    <tr class="text-c">
                        <th><a href="javascript:;" onclick="addtr()" class="btn-danger-outline radius"><i class="Hui-iconfont">&#xe600;</i></a></th>
                        <th width="col-sm-1">选项内容</th>
                        <th width="col-sm-2">操作</th>
                    </tr>
                    </thead>
                    <tbody id="table">
                    <#if info?exists>
                    <#list info.options?split("||") as op>
                    <tr class="text-c">
                        <td><input type="checkbox" name="result" <#if info.optionsResult?substring(op_index,(op_index+1))=='1'>checked</#if> /></td>
                        <td><input name="option" value="${op}" class="input-text"/></td>
                        <td>
                            <a title="删除" href="javascript:;" onclick="del(this)" class="ml-5" ><i class="Hui-iconfont">&#xe6e2;</i></a>
                        </td>
                    </tr>
                    </#list>
                    <#else>
                    </#if>
                    </tbody>
                </table>
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
    $(function(){
        changeType();
    });
    //表单验证
    $("#form").validate({
        rules:{
            title:"required",
            examType:"required",
            type:"required",
            score:{
                required:true,
                number:true,
                max:100,
            }
        },
        onkeyup:false,
        focusCleanup:true,
        success:"valid",
        submitHandler:function(form){
            var result="";//答案
            var qtype=$("#type").val();//题型
            var options="";  //选项
            if(qtype=="JUDGE"){
                result=$("input[name='judge']:checked").val();
            }else{
                $.each($("input[name='result']"),function (index,dom){
                    if(dom.checked){
                        result+="1";
                    }
                    else{
                        result+="0";
                    }
                })
                $.each($("input[name='option']"),function (index,dom){
                    options+=dom.value+"||";
                })
                if(options==""){
                    layer.msg("选择题至少有一个选项！",{icon:1,time:1500});
                    return false;
                }
                if(qtype=="SINGLE"&&patch("1",result)!=1){
                    layer.msg("单选题，有且只有一个答案！",{icon:1,time:1500});
                    return false;
                }
                if(qtype=="MORE"&&patch("1",result)<=1){
                    layer.msg("多选题，有两个以上答案！",{icon:1,time:1500});
                    return false;
                }
                $("#options").val(options.substring(0,options.length-2));
            }

            $("#optionsResult").val(result);
            $(form).ajaxSubmit({
                type: "post",  //提交方式
                dataType:"json",
                success: function(data){
                    layer.msg(data.msg,{icon:1,time:1500},function () {
                        if(data.status==1) {
                            window.parent.location.reload(true);
                        }
                    });
                }
            });
        }
    });

    function patch(re,str){ //参数1正则式，参数2字符串
        return (str.split(re).length-1);
    }
    function addtr(){
        $("#table").append('<tr class="text-c"><td><input type="checkbox" name="result" value="" id="ck_" /></td> ' +
                '<td><input name="option" class="input-text"/></td> ' +
                '<td>' +
                '<a title="删除" href="javascript:;" onclick="del(this)" class="ml-5" ><i class="Hui-iconfont">&#xe6e2;</i></a>' +
                '</td></tr>');
    }
    function del(obj) {
        $(obj).parents("tr").remove();
    }
    function changeType() {
        if($("#type").val()=="JUDGE"){
            $("#judge").show();
            $("#select").hide();
        }else{
            $("#select").show();
            $("#judge").hide();
        }
    }
</script>
<!--/请在上方写此页面业务相关的脚本-->
</body>
</html>
