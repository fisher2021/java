<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ include file="../common/taglibs.jsp" %>

<!DOCTYPE HTML>
<html>
<head>
    <%@ include file="../common/head.jsp" %>
    <title>添加菜单</title>
</head>
<body>
    <article class="page-container layer-container">
        <form class="form form-horizontal" id="form-submit">
        	<c:if test="${not empty menu }">
   				<input type="hidden" name="id" value="${menu.id }">     	
        	</c:if>
        	<c:if test="${not empty parentId }">
        		<input type="hidden" name="parent.id" value="${parentId }">
        	</c:if>
            <div class="row cl">
                <label class="form-label col-xs-4 col-sm-3">菜单编码：</label>
                <div class="formControls col-xs-6 col-sm-8">
                    <input type="text" class="input-text" autocomplete="off" value="${menu.code }" placeholder="请输入菜单编码" id="code" name="code">
                </div>
            </div>
            <div class="row cl">
                <label class="form-label col-xs-4 col-sm-3">菜单名称：</label>
                <div class="formControls col-xs-6 col-sm-8">
                    <input type="text" class="input-text" autocomplete="off" value="${menu.name }" placeholder="请输入菜单名称" id="name" name="name">
                </div>
            </div>
            <div class="row cl">
                <label class="form-label col-xs-4 col-sm-3">执行路径：</label>
                <div class="formControls col-xs-6 col-sm-8">
                    <input type="text" class="input-text" autocomplete="off" value="${menu.targetUrl }" placeholder="请输入执行路径" id="targetUrl" name="targetUrl">
                </div>
            </div>
            <div class="row cl">
                <label class="form-label col-xs-4 col-sm-3">菜单样式：</label>
                <div class="formControls col-xs-6 col-sm-8">
                    <input type="text" class="input-text" autocomplete="off" value="${menu.display }" placeholder="请输入菜单样式" id="display" name="display">
                </div>
            </div>
            <div class="row cl">
                <label class="form-label col-xs-4 col-sm-3">菜单排序：</label>
                <div class="formControls col-xs-6 col-sm-8">
	                <c:if test="${not empty menu }">
	        			<input type="text" class="input-text" value="${menu.rank }" autocomplete="off" placeholder="请输入菜单排序" id="rank" name="rank">	                
	        		</c:if>
	        		<c:if test="${empty menu }">
	        			<input type="text" class="input-text" value="0" autocomplete="off" placeholder="请输入菜单排序" id="rank" name="rank">
	        		</c:if>
                </div>
            </div>
            <div class="row cl">
                <div class="col-xs-9 col-sm-8 col-xs-offset-2 col-sm-offset-3">
                    <button type="reset" class="btn btn-success size-M radius" id="reset">重置</button>
                    <button type="button" class="btn btn-danger radius ml-50" id="submit">提交</button>
                </div>
            </div>
        </form>
    </article>
    <script type="text/javascript" src="${ctx }/static/back/lib/jquery.validation/1.14.0/jquery.validate.js"></script>
	<script type="text/javascript" src="${ctx }/static/back/admin/h-ui.admin/js/common.js"></script>
    <!--请在下方写此页面业务相关的脚本-->
    <script type="text/javascript">
        $().ready(function() {
            // 在键盘按下并释放及提交后验证提交表单
            $("#form-submit").validate({
                rules: {
                	code: {
						required:true,
						isNullStr:true
					},
                	name:{
						required:true,
						isNullStr:true
					},
                	rank: {
						required:true,
						isNullStr:true
					}
                },
                messages: {
                	code: "请输入菜单编码",
                	name: "请输入菜单名称",
                	rank: "请输入菜单排序"
                }
            });
            $("#submit").click(function(){
                if($("#form-submit").valid()){
                    subForm('${ctx}','${ctx}/back/menu/ajax/addOrEdit');
                }
            });
          //空格
			jQuery.validator.addMethod("isNullStr", function(value, element) {
			    var length = value.replace(/\s/g,"").length;
			    return this.optional(element) || (length!=0);
			}, "请输入有效字符");
        });
    </script>
    <!--/请在上方写此页面业务相关的脚本-->
</body>
</html>