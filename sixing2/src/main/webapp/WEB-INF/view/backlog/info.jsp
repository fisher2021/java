<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ include file="../common/taglibs.jsp" %>

<!DOCTYPE HTML>
<html>
<head>
    <%@ include file="../common/head.jsp" %>
	<title>待办事项详情</title>
</head>
<body>
    <article class="page-container layer-container">
    	<form class="form form-horizontal" id="form-submit" enctype="multipart/form-data">
            <div class="row cl">
                <label class="form-label col-xs-4 col-sm-3">指派事项：</label>
                <div class="formControls col-xs-7 col-sm-8">
                    <input type="text" class="input-text" autocomplete="off" value="${backlog.type.desc }" placeholder="活动成果" id="type" name="type" readonly/>
                </div>
            </div>
            <c:if test="${not empty backlog.missionId }">
                <div class="row cl">
                    <label class="form-label col-xs-4 col-sm-3">任务编号：</label>
                    <div class="formControls col-xs-7 col-sm-8">
                        <input type="text" class="input-text" autocomplete="off" value="${backlog.missionId }" placeholder="任务编号" id="missionId" name="missionId" readonly/>
                    </div>
                </div>
            </c:if>
            <c:if test="${not empty backlog.year }">
                <div class="row cl">
                    <label class="form-label col-xs-4 col-sm-3">缴费月份：</label>
                    <div class="formControls col-xs-7 col-sm-8">
                        <input type="text" class="input-text" autocomplete="off" value="${backlog.year }" placeholder="缴费月份" id="year" name="year" readonly/>
                    </div>
                </div>
            </c:if>
            <div class="row cl">
                <label class="form-label col-xs-4 col-sm-3">主题：</label>
                <div class="formControls col-xs-7 col-sm-8">
                    <input type="text" class="input-text" autocomplete="off" value="${backlog.subject }" placeholder="活动主题" id="subject" name="subject" readonly/>
                </div>
            </div>
            <div class="row cl">
                <label class="form-label col-xs-4 col-sm-3">指派时间：</label>
                <div class="formControls col-xs-7 col-sm-8">
                    <input type="text" class="input-text" autocomplete="off" value="${backlog.doDate }" placeholder="活动时间" id="doDate" name="doDate" readonly/>
                </div>
            </div>
            <div class="row cl">
                <label class="form-label col-xs-4 col-sm-3">完成情况：</label>
                <div class="formControls col-xs-7 col-sm-8">
                    <table class="table table-border table-bordered table-bg table-striped">
                        <thead>
                        <tr class="text-c">
                            <th width="col-sm-1">序号</th>
                            <th width="col-sm-1">指派对象</th>
                            <th width="col-sm-1">用户名</th>
                            <th width="col-sm-1">组织关系</th>
                            <th width="col-sm-1">是否完成</th>
                        </tr>
                        </thead>
                        <tbody>
                        <c:choose>
                            <c:when test="${not empty backlogUsers }">
                                <c:forEach items="${backlogUsers }" var="backloguser" varStatus="status">
                                    <tr class="text-c">
                                        <td>${status.count }</td>
                                        <td>${backloguser.user.nickname }</td>
                                        <td>${backloguser.user.account }</td>
                                        <td><c:forEach items="${backloguser.user.orgs }" var="org" varStatus="status">
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
                                        <td>
                                            <c:if test="${backloguser.finish }"><span style="color: red">已完成</span></c:if>
                                            <c:if test="${!backloguser.finish }">未完成</c:if>
                                        </td>
                                    </tr>
                                </c:forEach>
                            </c:when>
                            <c:otherwise>
                                <tr class="text-c">
                                    <td colspan="6">暂无待办事项...</td>
                                </tr>
                            </c:otherwise>
                        </c:choose>
                        </tbody>
                    </table>
                </div>
            </div>
        </form>
    </article>
    <script type="text/javascript">
        var uploadDomain = "${uploadDomain}";
    </script>
</body>
</html>