<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ include file="../common/taglibs.jsp" %>

<!DOCTYPE HTML>
<html>
<head>
	<%@ include file="../common/head.jsp" %>
	<title>动态资讯 轮播图添加</title>
</head>
<body>
<article class="page-container layer-container">
	<form class="form form-horizontal" id="form-submit" enctype="multipart/form-data">
		<c:if test="${not empty carousel }">
			<input type="hidden" name="id" value="${carousel.id }">
		</c:if>
		<div class="row cl member-dis">
			<label class="form-label col-xs-4 col-sm-3">所属类别：</label>
			<div class="formControls col-xs-7 col-sm-8">
				<select name="type.id" id="type">
					<%--<option value="" >请选择类别</option>--%>
					<option value="1" <c:if test="${1 == carousel.type.id }">selected</c:if>>党建要闻</option>
					<%--<option value="3" <c:if test="${3 == carousel.type.id }">selected</c:if>>理论学习</option>--%>
                    <c:if test="${logedOperator.org.level.ordinal() eq 0}">
					    <option value="54" <c:if test="${54 == carousel.type.id }">selected</c:if>>专题学习</option>
                    </c:if>
				</select>
			</div>
		</div>
		<div class="row cl">
			<label class="form-label col-xs-4 col-sm-3">标题：</label>
			<div class="formControls col-xs-7 col-sm-8">
				<input type="text" class="input-text" autocomplete="off" value="${carousel.title }" placeholder="请输入标题"
					   name="title"/>
			</div>
		</div>
        <div class="row cl">
            <label class="form-label col-xs-4 col-sm-3">发布人：</label>
            <div class="formControls col-xs-7 col-sm-8">
                <input type="text" class="input-text" autocomplete="off" value="${carousel.author }" placeholder="请输入发布人"
                       id="author" name="author"/>
            </div>
        </div>
        <div class="row cl">
            <label class="form-label col-xs-4 col-sm-3">排序：</label>
            <div class="formControls col-xs-7 col-sm-8">
                <input type="number" class="input-text" autocomplete="off" value="${carousel.rank }" placeholder="序号"
                       name="rank"/>
            </div>
        </div>
		<div class="row cl">
			<label class="form-label col-xs-4 col-sm-3">图片：</label>
			<div class="formControls col-xs-7 col-sm-8">
				<span class=" form-group">
				  <input type="hidden" name="imageUrl" value="${carousel.imageUrl }">
				  <input class="input-text upload-url " type="text" placeholder="点击上传 注意尺寸" readonly/>
				  <input type="file" multiple name="image" class="input-file" style="width: 100%;"/>
				</span>
			</div>
		</div>
        <div class="row cl">
            <label class="form-label col-xs-4 col-sm-3">是否外链：</label>
            <div class="formControls col-xs-7 col-sm-8">
                <input type="radio" class="mr-5" name="targetOut" id="yes" value="true"
                       <c:if test="${carousel.targetOut }">checked</c:if>/><label class="mr-10" for="yes">是</label>
                <input type="radio" class="mr-5" name="targetOut" id="no" value="false"
                       <c:if test="${(empty carousel) || (!carousel.targetOut) }">checked</c:if>/><label class="mr-10" for="no">否</label>
            </div>
        </div>
        <div class="row cl link">
            <label class="form-label col-xs-4 col-sm-3">外链地址：</label>
            <div class="formControls col-xs-7 col-sm-8">
                <input type="text" class="input-text" autocomplete="off" value="${carousel.targetUrl }"
                       placeholder="请输入外链地址" id="targetUrl" name="targetUrl"/>
            </div>
        </div>
        <div class="row cl cont">
            <label class="form-label col-xs-4 col-sm-3">内容：</label>
            <div class="formControls col-xs-7 col-sm-8">
                <textarea id="editor" name="content" style="width:100%;height:200px">${carousel.content }</textarea>
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
<script type="text/javascript" src="${ctx }/static/back/lib/jquery.validation/1.14.0/jquery.validate.js"></script>
<script type="text/javascript" src="${ctx }/static/back/admin/h-ui.admin/js/form.js"></script>
<script type="text/javascript" src="${ctx }/static/back/lib/ueditor/ueditor.config.js"></script>
<script type="text/javascript" src="${ctx }/static/back/lib/ueditor/ueditor.all.min.js"></script>
	<!--请在下方写此页面业务相关的脚本-->
	<script type="text/javascript">
		$().ready(function() {
            //富文本编辑框
            var ue = UE.getEditor('editor');
            //覆盖UEditor中获取路径的方法
            UE.Editor.prototype._bkGetActionUrl = UE.Editor.prototype.getActionUrl;
            UE.Editor.prototype.getActionUrl = function(action) {
                //判断路径   这里是config.json 中设置执行上传的action名称
                if (action == 'uploadimage' || action == 'catchimage') {
                    return '${ctx}/back/article/uploadUEditorFile';
                } else if (action == 'uploadvideo') {
                    return '';
                } else {
                    return this._bkGetActionUrl.call(this, action);
                }
            }
			// 在键盘按下并释放及提交后验证提交表单
			$("#form-submit").validate({
				rules: {
					title: {
						required:true,
						isNullStr:true
					},
					targetUrl:{
						required:true,
						isUrl:true
					},
					'type.id':{
						required:true,
						isNullStr:true
					},
                    content:{
                        required:true,
                    },
				},
				messages: {
					title: "请输入标题",
					targetUrl:"请输入正确的链接地址",
					'type.id':"",
                    content: "请输入内容",
				}
			});
			$("#submit").click(function(){
				if($("#form-submit").valid()){
                    <%--var type = $("#type").val();--%>
                    <%--var id = $("#id").val();--%>
                    <%--if(id == undefined){--%>
                        <%--//执行新增--%>
                        <%--$.ajax({--%>
                            <%--url:"${ctx}/back/carousel/ajax/count",--%>
                            <%--type:"post",--%>
                            <%--data:{"type" : type},--%>
                            <%--dataType:"json",--%>
                            <%--success:function(data){--%>
                                <%--if(data.data){--%>
                                    <%--$("form").ajaxSubmit(ajaxFormOption);--%>
                                <%--}else {--%>
                                    <%--layer.msg("该类别最多添加5张轮播图！",{icon:5,time:2000});--%>
                                    <%--$("#type").focus();--%>
                                <%--}--%>
                            <%--}--%>
                        <%--})--%>
                    <%--}else{--%>
                        <%--//执行保存--%>
                        <%--$("form").ajaxSubmit(ajaxFormOption);--%>
                    <%--}--%>
                    $("form").ajaxSubmit(ajaxFormOption);
				}
			});
            if($("#yes").is(":checked")){
                $(".link").css("display","block");
                $(".cont").css("display","none")
            }else{
                $(".link").css("display","none");
                $(".cont").css("display","block")
            }
            $("input:radio").click(function(){
                if($("#yes").is(":checked")){
                    $(".link").css("display","block");
                    $(".cont").css("display","none")
                }else{
                    $(".link").css("display","none");
                    $(".cont").css("display","block")
                }
            });
		});
		
		var ajaxFormOption = {
			 type: "post",  //提交方式 
			 dataType:"json",
			 url: "${ctx }/back/carousel/ajax/addOrEdit", //请求url
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
		//网址
		jQuery.validator.addMethod("isUrl", function(value, element) {
			var url = /^(((http|ftp|https):\/\/\S+)||#)$/;
		    return this.optional(element) || (url.test(value));
		}, "请输入正确网址");
	</script>
	<!--/请在上方写此页面业务相关的脚本-->
</body>
</html>