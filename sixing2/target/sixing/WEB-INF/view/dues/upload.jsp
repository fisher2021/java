<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ include file="../common/taglibs.jsp" %>

<!DOCTYPE HTML>
<html>
<head>
	<%@ include file="../common/head.jsp" %>
	<title>批量导入</title>
</head>
<body>
<article class="page-container layer-container">
	<form class="form form-horizontal" id="form-submit" enctype="multipart/form-data" method="post">
        <div class="row cl">
            <label class="form-label col-xs-4 col-sm-3">缴费年份：</label>
            <div class="formControls col-xs-7 col-sm-8">
                <input type="text" class="input-text radius select_user" placeholder="请选择缴费年份" readonly onclick="WdatePicker({lang:'zh-cn',dateFmt:'yyyy'})" id="year" style="margin-right: 0;" name="year"></span>
            </div>
        </div>
        <div class="row cl member-dis">
            <label class="form-label col-xs-4 col-sm-3">缴费类型：</label>
            <div class="formControls col-xs-7 col-sm-8">
                <select name="type" id="type" style="width: 170px">
                    <option value="" >请选择类型</option>
                    <option value="0">应缴费</option>
                    <option value="1">已缴费</option>
                </select>
            </div>
        </div>
		<div class="row cl">
			<label class="form-label col-xs-4 col-sm-3">Excel 文件：</label>
			<div class="formControls col-xs-7 col-sm-8">
				<span class=" form-group">
				  <input class="input-text upload-url " type="text" placeholder="点击上传" readonly/>
				  <input type="file" multiple name="file" class="input-file" style="width: 100%;"/>
				</span>
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
                    year: {
                        required:true,
                    },
                    type:{
                        required:true,
                    },
                    file:{
                        required:true,
                    }
                },
                messages: {
                    file: "请选择文件",
                    type: "",
                    year: "请选择缴费年份",
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
			 url: "${ctx }/back/dues/ajax/addBatch", //请求url
			 success: function (data) { //提交成功的回调函数
				 layer.msg(data.data,{icon:1,time:2000},function () {
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