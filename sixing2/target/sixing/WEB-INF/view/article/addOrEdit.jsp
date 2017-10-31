<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ include file="../common/taglibs.jsp" %>

<!DOCTYPE HTML>
<html>
<head>
    <%@ include file="../common/head.jsp" %>
	<title>文章添加</title>
</head>
<body>
<article class="page-container layer-container">
    <form class="form form-horizontal" id="form-submit" enctype="multipart/form-data" method="post">
        <c:if test="${not empty article }">
            <input type="hidden" name="id" value="${article.id }">
        </c:if>
        <div class="row cl">
            <label class="form-label col-xs-4 col-sm-3">标题：</label>
            <div class="formControls col-xs-7 col-sm-8">
                <input type="text" class="input-text" autocomplete="off" value="${article.title }" placeholder="请输入标题" id="title" name="title"/>
            </div>
        </div>
        <div class="row cl">
            <label class="form-label col-xs-4 col-sm-3">发布人：</label>
            <div class="formControls col-xs-7 col-sm-8">
                <input type="text" class="input-text" autocomplete="off" value="${article.author }" placeholder="请输入发布人" id="author" name="author"/>
            </div>
        </div>
        <div class="row cl">
            <label class="form-label col-xs-4 col-sm-3">创建时间：</label>
            <div class="formControls col-xs-7 col-sm-8">
                <input type="text" class="input-text radius select_user" placeholder="请选择创建时间" readonly onclick="WdatePicker({lang:'zh-cn',dateFmt:'yyyy-MM-dd HH:mm:ss'})" id="createTime" style="margin-right: 0;" name="createTime" value="${article.createTime}"></span>
            </div>
        </div>
        <div class="row cl">
            <label class="form-label col-xs-4 col-sm-3">图片：</label>
            <div class="formControls col-xs-7 col-sm-8">
				<span class=" form-group">
				  <input type="hidden" name="imgUrl" value="${article.imgUrl }">
				  <input class="input-text upload-url " type="text"  placeholder="点击上传 注意尺寸" readonly />
				  <input type="file" multiple name="image" class="input-file" style="width: 100%;"/>
				</span>
            </div>
        </div>
         <div class="row cl" <c:if test="${logedOperator.org.level == branch || dictId == 'gzgf' || dictId == 'shyk' || dictId == 'lzjs' || dictId == 'tzgg'}">style="display: none" </c:if>>
                <label class="form-label col-xs-4 col-sm-3">所属分类：</label>
                <div class="formControls col-xs-6 col-sm-8">
                    <select  style="width: 50%" id="kind" onclick="change()" onchange="change()">
                        <c:forEach items="${kind }" var="kind">
                            <option value="${kind.id }" <c:if test="${kind.id == article.type.parent.id || kind.id == article.type.parent.parent.id
                            || kind.dictId == dictId}">selected</c:if>
                                    <c:if test="${kind.dictId == 'gzgf' || kind.dictId == 'shyk' || kind.dictId == 'lzjs' || kind.dictId == 'tzgg' }">style="display: none" </c:if>>${kind.dictName }</option>
                        </c:forEach>
                    </select>
                </div>
         </div>
        <c:choose>
            <c:when test="${sessionScope.get('logedOperator').org.level == branch && dictId =='tzgg'}"></c:when>
            <c:otherwise>
                <%--<c:if test="${dictId != 'gzgf'}">--%>
                <c:if test="${dictId != 'gzgf' && dictId != 'lzjs'}">
                    <div class="row cl">
                        <label class="form-label col-xs-4 col-sm-3">所属频道：</label>
                        <div class="formControls col-xs-6 col-sm-8">
                            <select onclick="change()" onchange="change()" name="type.id" style="width: 50%" id="channelDictId1" >
                                <option value="">请选择频道</option>
                                <c:forEach items="${firstDicts }" var="firstDicts">
                                    <option value="${firstDicts.id }" class="type_kind_${firstDicts.parent.id } dict2 " <c:if test="${firstDicts.id == article.type.id || firstDicts.id == article.type.parent.id}">selected</c:if>>${firstDicts.dictName }</option>
                                </c:forEach>
                            </select>
                            <select style="width: 49%" name="typeId"  class="secondDicts">
                                <option value="">请选择子频道</option>
                                <c:forEach items="${secondDicts }" var="secondDicts">
                                    <option value="${secondDicts.id }" class="dict2 type_${secondDicts.parent.id } " <c:if test="${secondDicts.id == article.type.id }">selected</c:if>>${secondDicts.dictName }</option>
                                </c:forEach>
                            </select>
                        </div>
                    </div>
                </c:if>
                <c:if test="${dictId == 'gzgf'}">
                    <input type="hidden" name="type.id" value="${gzgf }">
                </c:if>
                <c:if test="${dictId == 'lzjs'}">
                    <input type="hidden" name="type.id" value="${lzjs }">
                </c:if>
            </c:otherwise>
        </c:choose>
        <c:if test="${sessionScope.get('logedOperator').org.level == branch && dictId == 'tzgg'}">
            <input type="hidden" name="type.id" value="${dzb }">
        </c:if>

        <div class="row cl">
            <label class="form-label col-xs-4 col-sm-3">是否外链：</label>
            <div class="formControls col-xs-7 col-sm-8">
                <input type="radio" class="mr-5" name="targetOut" id="yes" value="true"
                       <c:if test="${article.targetOut }">checked</c:if>/><label class="mr-10" for="yes">是</label>
                <input type="radio" class="mr-5" name="targetOut" id="no" value="false"
                       <c:if test="${(empty article) || (!article.targetOut) }">checked</c:if>/><label class="mr-10" for="no">否</label>
            </div>
        </div>
        <div class="row cl link">
            <label class="form-label col-xs-4 col-sm-3">外链地址：</label>
            <div class="formControls col-xs-7 col-sm-8">
                <input type="text" class="input-text" autocomplete="off" value="${article.targetUrl }"
                       placeholder="请输入外链地址" id="targetUrl" name="targetUrl"/>
            </div>
        </div>
        <div class="row cl cont">
            <label class="form-label col-xs-4 col-sm-3">内容：</label>
            <div class="formControls col-xs-7 col-sm-8">
                <textarea id="editor" name="content" style="width:100%;height:200px">${article.content }</textarea>
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
    <script type="text/javascript" src="${ctx }/static/back/lib/ueditor/ueditor.config.js"></script>
    <script type="text/javascript" src="${ctx }/static/back/lib/ueditor/ueditor.all.min.js"></script>
    <!--请在下方写此页面业务相关的脚本-->
    <script type="text/javascript">
        function change() {
            var kind = $("#kind").val();
            var id = $("#channelDictId1").val();
            $(".dict2").css("display","none");
            $(".type_kind_"+kind).css("display","block");
            if (kind == ${shyk.id}){
                $(".secondDicts").css("display","inline-block");
            }else {
                $(".secondDicts").css("display","none");
            }
            $(".type_"+id).css("display","block");
        }

        $().ready(function() {
            var kind = $("#kind").val();
            if (kind == ${shyk.id}){
                $(".secondDicts").css("display","inline-block");
            }else {
                $(".secondDicts").css("display","none");
            }
            $(".dict2").css("display","none");
            $(".type_kind_"+kind).css("display","block");
            change();
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
            //富文本编辑框
            var ue = UE.getEditor('editor');

            // 在键盘按下并释放及提交后验证提交表单
            $("#form-submit").validate({
                rules: {
                	title: {
						required:true,
                        maxlength:50
					},
                    "type.id": {
                        required:true,
                    },
                    content:{
						required:true,
					},
                    targetUrl:{
                        required:true,
                        isUrl:true,
                    },
                    author:{
                	    maxlength:12
                    }
                },
                messages: {
                    "type.id": "",
                    content: "请输入内容",
                    targetUrl:"请输入正确的链接地址"
                }
            });
            $("#submit").click(function(){
                if($("#form-submit").valid()){
                    $("#submit").attr("disabled",true);
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
   			 url: "${ctx }/back/article/ajax/addOrEdit", //请求url
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
		    var url = /^(http|ftp|https):\/\/\S+$/;
		    return this.optional(element) || (url.test(value));
		}, "请输入正确网址");
    </script>
    <!--/请在上方写此页面业务相关的脚本-->
</body>
</html>