<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ include file="../common/taglibs.jsp" %>

<!DOCTYPE HTML>
<html>
<head>
    <%@ include file="../common/head.jsp" %>
	<title>文章详情</title>
    <style>
        .editor-detail{
            overflow: auto;
        }
        .editor-detail img{
            width: 100%;
            height: auto;
        }
    </style>
</head>
<body>
    <article class="page-container layer-container">
    	<form class="form form-horizontal" id="form-submit" enctype="multipart/form-data">
        <div class="row cl">
            <label class="form-label col-xs-4 col-sm-3">标题：</label>
            <div class="formControls col-xs-7 col-sm-8">
                <input type="text" class="input-text" autocomplete="off" value="${article.title }" placeholder="请输入标题" id="title" name="title" readonly/>
            </div>
        </div>
        <c:if test="${not empty article.imgUrl }">
            <div class="row cl">
                <label class="form-label col-xs-4 col-sm-3">缩略图：</label>
                <div class="formControls col-xs-7 col-sm-8">
                      <img src="${ctx }${article.imgUrl }" alt="" width="200" height="200"/>
                </div>
            </div>
        </c:if>
		<div class="row cl">
            <label class="form-label col-xs-4 col-sm-3">所属频道：</label>
            <div class="formControls col-xs-7 col-sm-8">
                <input type="text" class="input-text" autocomplete="off" value="${article.type.dictName }" placeholder="请输入频道" id="type" name="type" readonly/>
            </div>
        </div>
        <c:if test="${article.targetOut }">
             <div class="row cl link">
                 <label class="form-label col-xs-4 col-sm-3">外链地址：</label>
                 <div class="formControls col-xs-7 col-sm-8">
                    <input type="text" class="input-text" autocomplete="off" value="${article.targetUrl }" placeholder="请输入外链地址" id="targetUrl" name="targetUrl" readonly/>
                 </div>
             </div>
        </c:if>
        <c:if test="${!article.targetOut }">
        <div class="row cl cont">
            <label class="form-label col-xs-4 col-sm-3">内容：</label>
            <div class="formControls col-xs-7 col-sm-8">
                <%--<textarea  id="editor" name="content" style="width:100%;height:250px" readonly>${article.content }</textarea>--%>
                <div class="editor-detail" style="border: solid #b3b3b3 1px">${article.content }</div>
            </div>
        </div>
        </c:if>
        </form>
    </article>
    <script type="text/javascript" src="${ctx }/static/back/lib/jquery.validation/1.14.0/jquery.validate.js"></script>
    <script type="text/javascript" src="${ctx }/static/back/lib/ueditor/ueditor.config.js"></script>
    <script type="text/javascript" src="${ctx }/static/back/lib/ueditor/ueditor.all.min.js"></script>
    <!--请在下方写此页面业务相关的脚本-->
    <script type="text/javascript">
        <c:if test="${!article.targetOut }">
        var uploadDomain = "${uploadDomain}";
        $().ready(function () {
            //富文本编辑框
//            var ue = UE.getEditor('editor');
        });
        </c:if>
    </script>
    <!--/请在上方写此页面业务相关的脚本-->
</body>
</html>