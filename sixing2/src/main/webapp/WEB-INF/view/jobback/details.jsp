<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ include file="../common/taglibs.jsp" %>

<!DOCTYPE HTML>
<html>
<head>
    <%@ include file="../common/head.jsp" %>
	<title>工作展示详情</title>
    <style>
        @font-face {
            font-family: song;
            src:url(${ctx}/static/back/font/song.TTF);
        }
        .div-top{
            margin-top: 10px;
        }
        @page {/*不打印页眉和页脚*/
            size: auto;  /* auto is the initial value */
            margin: 0mm; /* this affects the margin in the printer settings */
        }
    </style>
</head>
<body id="word">
    <button id="print" onclick="printArticle()" class="btn radius f-r" style="margin: 10px">打印</button>
    <article class="page-container layer-container">
    	<form class="form form-horizontal" id="form-submit" enctype="multipart/form-data">
            <div style="text-align: center">
                <%--<label class="form-label col-xs-4 col-sm-3">活动主题：</label>--%>
                <%--<div class="formControls col-xs-7 col-sm-8">--%>
                    <span style="font-family:song;font-size:28px;">${job.subject }</span>
                    <%--<input type="text" class="input-text" autocomplete="off" value="${job.subject }" placeholder="活动主题" id="subject" name="subject" readonly/>--%>
                <%--</div>--%>
            </div>
            <c:if test="${not empty job.image1}">
            <div class="div-top">
                <%--<label class="form-label col-xs-4 col-sm-3">图片一：</label>--%>
                <%--<div class="formControls col-xs-7 col-sm-8">--%>
                    <img src="${ctx }${job.image1 }" alt="加载失败" width="60%" height="250" style="margin-left: 20%"/>
                        <%--</div>--%>
            </div>
            <div class="div-top">
                <%--<label class="form-label col-xs-4 col-sm-3">图片一描述：</label>--%>
                <%--<div class="formControls col-xs-7 col-sm-8">--%>
                    　　　<span style="font-family:'楷体';font-size:18px">${job.description1 }</span>
                    <%--<textarea id="description1" name="description1" style="width:100%;height:50px" placeholder="请输入详细描述" readonly >${job.description1 }</textarea>--%>
                    <%--<input type="text" class="input-text" autocomplete="off" value="${job.description1 }" placeholder="参与人员" id="description1" name="description1" readonly/>--%>
                <%--</div>--%>
            </div>
            </c:if>
            <c:if test="${not empty job.image2}">
                <div class="div-top">
                    <%--<label class="form-label col-xs-4 col-sm-3">图片二：</label>--%>
                    <%--<div class="formControls col-xs-7 col-sm-8">--%>
                        <img src="${ctx }${job.image2 }" alt="加载失败" width="60%" height="250" style="margin-left: 20%"/>
                    <%--</div>--%>
                </div>
                <div class="div-top">
                    <%--<label class="form-label col-xs-4 col-sm-3">图片二描述：</label>--%>
                    <%--<div class="formControls col-xs-7 col-sm-8">--%>
                        　　　<span style="font-family:'楷体';font-size:18px">${job.description2 }</span>
                        <%--<textarea id="description2" name="description2" style="width:100%;height:50px" placeholder="请输入详细描述" readonly >${job.description2 }</textarea>--%>
                        <%--<input type="text" class="input-text" autocomplete="off" value="${job.description2 }" placeholder="参与人员" id="description2" name="description2" readonly/>--%>
                    <%--</div>--%>
                </div>
            </c:if>
            <div style="margin-top: 15px">
                <%--<label class="form-label col-xs-4 col-sm-3">详细描述：</label>--%>
                <%--<div class="formControls col-xs-7 col-sm-8">--%>
                    　　　<span style="font-family:'仿宋';font-size:20px">${job.content }</span>
                    <%--<textarea id="content" name="content" style="width:100%;height:100px" placeholder="请输入详细描述" readonly >${job.content }</textarea>--%>
                <%--</div>--%>
            </div>
        </form>
    </article>
<script type="text/javascript" src="${ctx}/static/back/lib/tableexport.min.js"></script>
<script>
    function printArticle() {
        $("#print").css("display","none");
        window.print();
        $("#print").css("display","block");
    }

</script>
</body>
</html>