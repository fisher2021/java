<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ include file="../common/taglibs.jsp" %>

<!DOCTYPE HTML>
<html>
<head>
    <%@ include file="../common/head.jsp" %>
	<title>党务日历详情</title>
</head>
<body>
    <article class="page-container layer-container">
    	<form class="form form-horizontal" id="form-submit" enctype="multipart/form-data">
            <div class="row cl">
                <label class="form-label col-xs-4 col-sm-3">主题：</label>
                <div class="formControls col-xs-7 col-sm-8">
                    <input type="text" class="input-text" autocomplete="off" value="${calendar.subject }" placeholder="活动主题" id="subject" name="subject" readonly/>
                </div>
            </div>
            <div class="row cl">
                <label class="form-label col-xs-4 col-sm-3">详情：</label>
                <div class="formControls col-xs-7 col-sm-8">
                    <input type="text" class="input-text" autocomplete="off" value="${calendar.content }" placeholder="请输入活动成果" id="content" name="content" readonly/>
                </div>
            </div>
            <div class="row cl">
                <label class="form-label col-xs-4 col-sm-3">指派时间：</label>
                <div class="formControls col-xs-7 col-sm-8">
                    <input type="text" class="input-text" autocomplete="off" value="${calendar.doDate }" placeholder="活动时间" id="doDate" name="doDate" readonly/>
                </div>
            </div>
            <div class="row cl">
                <label class="form-label col-xs-4 col-sm-3">指派对象：</label>
                <div class="formControls col-xs-7 col-sm-8">
                    <table class="table table-border table-bordered table-bg table-striped">
                        <thead>
                        <tr class="text-c">
                            <th width="col-sm-1">序号</th>
                            <th width="col-sm-1">指派对象</th>
                            <th width="col-sm-1">用户名</th>
                            <th width="col-sm-1">组织关系</th>
                        </tr>
                        </thead>
                        <tbody>
                        <c:choose>
                            <c:when test="${not empty calendarUsers }">
                                <c:forEach items="${calendarUsers }" var="calendaruser" varStatus="status">
                                    <tr class="text-c">
                                        <td>${status.count }</td>
                                        <td>${calendaruser.user.nickname }</td>
                                        <td>${calendaruser.user.account  }</td>
                                        <td><c:forEach items="${calendaruser.user.orgs }" var="org" varStatus="status">
                                            <c:choose>
                                                <c:when test="${org.level != branch}">
                                                    ${org.level.desc }
                                                </c:when>
                                                <c:otherwise>
                                                    ${org.name }
                                                </c:otherwise>
                                            </c:choose>
                                            <c:if test="${!status.last}">、</c:if>
                                        </c:forEach></td>
                                    </tr>
                                </c:forEach>
                            </c:when>
                            <c:otherwise>
                                <tr class="text-c">
                                    <td colspan="6">暂无党务日历...</td>
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