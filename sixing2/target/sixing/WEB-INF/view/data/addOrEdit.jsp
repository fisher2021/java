<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ include file="../common/taglibs.jsp" %>

<!DOCTYPE HTML>
<html>
<head>
    <%@ include file="../common/head.jsp" %>
	<title>文件添加</title>
</head>
<body>
<article class="page-container layer-container">
    <form class="form form-horizontal" id="form-submit" enctype="multipart/form-data" method="post">
        <c:if test="${not empty file }">
            <input type="hidden" name="id" value="${file.id }">
        </c:if>
        <div class="row cl">
            <label class="form-label col-xs-4 col-sm-3">标题：</label>
            <div class="formControls col-xs-7 col-sm-8">
                <input type="text" class="input-text" autocomplete="off" value="${file.title }" placeholder="请输入标题" id="title" name="title"/>
            </div>
        </div>
        <div class="row cl">
            <label class="form-label col-xs-4 col-sm-3">资料：</label>
            <div class="formControls col-xs-7 col-sm-8">
				<span class=" form-group">
				  <input type="hidden" name="imgUrl" value="${file.url }">
				  <input class="input-text upload-url " type="text"  placeholder="点击上传 注意文件大小" readonly />
				  <input type="file" multiple name="file" class="input-file" style="width: 100%;"/>
				</span>
            </div>
        </div>
        <c:if test="${logedOperator.org.level != branch}">
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
                                <td><input type="checkbox" value="${org.id }" name="orgs"/><br></td>
                                <td>${org.name }</td>
                            </tr>
                            <span class="parent-title va-m"></span>
                        </c:forEach>
                        </tbody>
                    </table>
                </div>
            </div>
        </c:if>
        <div class="row cl">
            <div class="col-xs-9 col-sm-8 col-xs-offset-3 col-sm-offset-4">
                <button type="reset" class="btn btn-success size-M radius" id="reset">重置</button>
                <button type="button" class="btn btn-danger radius ml-50" id="submit">提交</button>
            </div>
        </div>
    </form>
</article>
<script type="text/javascript" src="${ctx }/static/back/lib/zTree/v3/js/jquery.ztree.all-3.5.min.js"></script>
<script type="text/javascript" src="${ctx }/static/back/admin/h-ui.admin/js/menu-tree.js"></script>
    <!--请在下方写此页面业务相关的脚本-->
    <script type="text/javascript">
        $().ready(function() {
            // 在键盘按下并释放及提交后验证提交表单
            $("#form-submit").validate({
                rules: {
                    title: {
						required:true,
                        maxlength:50,
					},
                },
                messages: {
                    title: "",
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
   			 url: "${ctx }/back/data/ajax/addOrEdit", //请求url
   			 success: function (data) { //提交成功的回调函数
                 layer.msg(data.msg,{icon:1,time:1000},function () {
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