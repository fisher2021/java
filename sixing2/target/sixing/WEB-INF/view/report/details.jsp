<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ include file="../common/taglibs.jsp" %>

<!DOCTYPE HTML>
<html>
<head>
    <%@ include file="../common/head.jsp" %>
	<title>工作反馈详情</title>
    <style>
        @font-face {
            font-family: song;
            src:url(${ctx}/static/back/font/song.TTF);
        }
        @page {/*不打印页眉和页脚*/
            size: auto;  /* auto is the initial value */
            margin: 0mm; /* this affects the margin in the printer settings */
        }
    </style>
</head>
<body>
<button id="print" onclick="printArticle()" class="btn radius f-r" style="margin: 10px">打印</button>
    <article class="page-container layer-container">
    	<form class="form form-horizontal" id="form-submit" name="form1" enctype="multipart/form-data">
            <div class="row cl text-c">
                <%--<label class="form-label col-xs-4 col-sm-3">汇报主题：</label>--%>
                <%--<div class="formControls col-xs-7 col-sm-8">--%>
                    <span style="font-family:song;font-size:28px;">${report.subject }</span>
                    <%--<input type="text" class="input-text" autocomplete="off" value="${report.subject }" placeholder="汇报主题" id="subject" name="subject" readonly/>--%>
                <%--</div>--%>
            </div>
            <div class="row cl">
                <%--<label class="form-label col-xs-4 col-sm-3">汇报内容：</label>--%>
                <%--<div class="formControls col-xs-7 col-sm-8">--%>
                    　　　<span style="font-family:'仿宋';font-size:20px">${report.content }</span>
                    <%--<textarea id="content" name="content" style="width:100%;height:100px" placeholder="请输入汇报内容" readonly >${report.content }</textarea>--%>
                <%--</div>--%>
            </div>
        </form>
    </article>
<script>
    function printArticle() {
        $("#print").css("display","none");
        window.print();
        $("#print").css("display","block");
    }
    <%--function outDoc(){--%>
        <%--try {--%>
            <%--var wdapp = new ActiveXObject("Word.Application");}--%>
        <%--catch(e){--%>
                <%--alert("请启用ActiveX控件设置！");--%>
                <%--return;--%>
            <%--}--%>

        <%--wdapp.visible = true;--%>
        <%--wddoc = wdapp.document.open("${ctx}/file/report.doc");--%>
        <%--var form = document.all.form1;--%>
        <%--subject = form.subject;--%>
        <%--range =wdapp.ActiveDocument.Bookmarks("subject").Range;--%>
        <%--range.Text=subject;--%>
        <%--wddoc.Application.Printout();--%>
        <%--wdapp=null;--%>
    <%--}--%>
</script>
</body>
</html>