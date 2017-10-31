<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ include file="../common/taglibs.jsp" %>

<!DOCTYPE HTML>
<html>
<head>
    <%@ include file="../common/head.jsp" %>
	<title>在线投票统计</title>
</head>
<body>
    <article class="page-container layer-container">
    	<form class="form form-horizontal" id="form-submit" enctype="multipart/form-data">
            <div class="row cl">
                <label class="form-label col-xs-4 col-sm-3">投票名称：</label>
                <div class="formControls col-xs-7 col-sm-8">
                    <input type="text" class="input-text" autocomplete="off" value="${options.vote.title }" placeholder="投票名称" readonly/>
                </div>
            </div>
            <div class="row cl">
                <label class="form-label col-xs-4 col-sm-3">简介：</label>
                <div class="formControls col-xs-7 col-sm-8">
                    <input type="text" class="input-text" autocomplete="off" value="${options.content }" placeholder="简介" id="content" name="content" readonly/>
                </div>
            </div>
            <c:if test="${not empty options.image }">
                <div class="row cl">
                    <label class="form-label col-xs-4 col-sm-3">图片：</label>
                    <div class="formControls col-xs-7 col-sm-8">
                        <img src="${ctx }${options.image }" alt="" width="200" height="200"/>
                    </div>
                </div>
            </c:if>
            <div class="row cl">
                <label class="form-label col-xs-4 col-sm-3">投票类型：</label>
                <div class="formControls col-xs-7 col-sm-8">
                    <input type="text" class="input-text" autocomplete="off" value="<c:if test="${options.radio }">单选</c:if><c:if test="${!options.radio }">多选</c:if>" placeholder="投票类型" id="radio" name="radio" readonly/>
                </div>
            </div>
            <div class="row cl">
                <label class="form-label col-xs-4 col-sm-3">参与人数：</label>
                <div class="formControls col-xs-7 col-sm-8">
                    <input type="text" class="input-text" autocomplete="off" value="${count }" placeholder="参与人数" id="count" name="count" readonly/>
                </div>
            </div>
            <div class="row cl">
                <label class="form-label col-xs-4 col-sm-3">投票标题：</label>
                <div class="formControls col-xs-7 col-sm-8">
                    <input type="text" class="input-text" autocomplete="off" value="${options.title }" placeholder="投票标题" id="title" name="title" readonly/>
                </div>
            </div>
            <div class="row cl">
                <label class="form-label col-xs-4 col-sm-3">统计结果：</label>
                <div class="formControls col-xs-7 col-sm-8">
                    <table class="table table-border table-bordered table-bg table-striped">
                        <thead>
                        <tr class="text-c">
                            <th width="col-sm-1">序号</th>
                            <th width="col-sm-1">选项</th>
                            <th width="col-sm-1">得票</th>
                        </tr>
                        </thead>
                        <tbody>
                        <c:choose>
                            <c:when test="${not empty options }">
                                <c:set value="${fn:split(options.evaluate,',') }" var="evaluate"></c:set>
                                <c:forEach items="${fn:split(options.choice,',') }" var="choice" varStatus="status">
                                    <tr class="text-c">
                                        <td>${status.count }</td>
                                        <td>${choice }</td>
                                        <td>${evaluate[status.count-1] }</td>
                                    </tr>
                                </c:forEach>
                            </c:when>
                            <c:otherwise>
                                <tr class="text-c">
                                    <td colspan="6">暂无在线投票...</td>
                                </tr>
                            </c:otherwise>
                        </c:choose>
                        </tbody>
                    </table>
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