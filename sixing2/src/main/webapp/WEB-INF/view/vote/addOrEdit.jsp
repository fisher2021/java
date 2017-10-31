<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ include file="../common/taglibs.jsp" %>

<!DOCTYPE HTML>
<html>
<head>
    <%@ include file="../common/head.jsp" %>
    <title>在线投票添加</title>
</head>
<body>
<article class="page-container layer-container">
    <form class="form form-horizontal" id="form-submit" enctype="multipart/form-data" method="post">
        <c:if test="${not empty vote }">
            <input type="hidden" name="id" value="${vote.id }">
        </c:if>
        <div class="row cl">
            <label class="form-label col-xs-4 col-sm-3">投票名称：</label>
            <div class="formControls col-xs-7 col-sm-8">
                <input type="text" class="input-text" autocomplete="off" value="${options.vote.title }" placeholder="限50字" id="voteTitle" name="voteTitle"/>
            </div>
        </div>
        <div class="row cl cont">
            <label class="form-label col-xs-4 col-sm-3">简介：</label>
            <div class="formControls col-xs-7 col-sm-8">
                <textarea id="content" name="content" style="width:100%;height:100px"  placeholder="限200字" >${options.content }</textarea>
            </div>
        </div>
        <div class="row cl">
            <label class="form-label col-xs-4 col-sm-3">图片：</label>
            <div class="formControls col-xs-7 col-sm-8">
				<span class=" form-group">
				  <input type="hidden" name="image" value="${options.image }">
				  <input class="input-text upload-url " type="text"  placeholder="点击上传 注意尺寸" readonly />
				  <input type="file" multiple name="file" class="input-file" style="width: 100%;"/>
				</span>
            </div>
        </div>
        <div class="row cl">
            <label class="form-label col-xs-4 col-sm-3">投票类型：</label>
            <div class="formControls col-xs-6 col-sm-8">
                <select name="radio" id="radio">
                    <option value="">请选择投票类型</option>
                    <option value="1" <c:if test="${options.radio == 1 }">selected</c:if>>单选</option>
                    <option value="0" <c:if test="${options.radio == 0 }">selected</c:if>>多选</option>
                </select>
            </div>
        </div>
        <div class="row cl">
            <label class="form-label col-xs-4 col-sm-3">投票标题：</label>
            <div class="formControls col-xs-7 col-sm-8">
                <input type="text" class="input-text" autocomplete="off" value="${vote.title }" placeholder="限50字" id="title" name="title"/>
            </div>
        </div>
        <div class="row cl">
            <label class="form-label col-xs-4 col-sm-3">候选项：</label>
            <div class="formControls col-xs-7 col-sm-8">
                <div id="InputsWrapper">
                    <div><input type="text" name="option" id="field_1" value="" class="input-text" autocomplete="off" placeholder="请输入候选项" style="width: 80%"/>
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
    var uploadDomain = "${uploadDomain}";
    $().ready(function() {
        // 在键盘按下并释放及提交后验证提交表单
        $("#form-submit").validate({
            rules: {
                voteTitle: {
                    required:true,
                    maxlength:50,
                },
                content:{
                    maxlength:200,
                },
                radio:{
                    required:true,
                },
                title: {
                    required:true,
                    maxlength:50,
                },
                option: {
                    required:true,
                },
            },
            messages: {
                radio: "",
                option: "",
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
        url: "${ctx }/back/vote/ajax/addOrEdit", //请求url
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
            $(InputsWrapper).append('<div><input type="text" name="option" id="field_'+ FieldCount +'" value="" class="input-text" autocomplete="off" placeholder="请输入候选项" style="width: 80%"/>' +
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