<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ include file="../common/taglibs.jsp" %>

<!DOCTYPE HTML>
<html>
<head>
    <%@ include file="../common/head.jsp" %>
	<title>系统消息详情</title>
</head>
<body>
    <article class="page-container layer-container">
    	<form class="form form-horizontal" id="form-submit" enctype="multipart/form-data">
            <div class="row cl">
                <label class="form-label col-xs-4 col-sm-3">标题：</label>
                <div class="formControls col-xs-7 col-sm-8">
                    <input type="text" class="input-text" autocomplete="off" value="${message.title }" placeholder="标题" id="title" name="title" readonly/>
                </div>
            </div>
            <div class="row cl">
                <label class="form-label col-xs-4 col-sm-3">内容：</label>
                <div class="formControls col-xs-7 col-sm-8">
                    <input type="text" class="input-text" autocomplete="off" value="${message.content }" placeholder="内容" id="content" name="content" readonly/>
                </div>
            </div>
            <div class="row cl">
                <label class="form-label col-xs-4 col-sm-3">指派组织：</label>
                <div class="formControls col-xs-7 col-sm-8">
                    <table class="table table-border table-bordered table-bg table-striped">
                        <thead>
                        <tr class="text-c">
                            <th width="col-sm-1">序号</th>
                            <th width="col-sm-1">组织</th>
                        </tr>
                        </thead>
                        <tbody>
                        <c:choose>
                            <c:when test="${not empty orgs }">
                                <c:forEach items="${orgs }" var="org" varStatus="status">
                                    <tr class="text-c">
                                        <td>${status.count }</td>
                                        <td>${org }</td>
                                    </tr>
                                </c:forEach>
                            </c:when>
                            <c:otherwise>
                                <tr class="text-c">
                                    <td colspan="6">暂未指派组织...</td>
                                </tr>
                            </c:otherwise>
                        </c:choose>
                        </tbody>
                    </table>
                </div>
            </div>
        </form>
    </article>
</body>
</html>