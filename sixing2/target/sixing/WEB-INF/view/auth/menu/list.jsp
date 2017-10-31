<%--
  Created by IntelliJ IDEA.
  User: zhangshao
  Date: 2016/5/14
  Time: 18:08
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ include file="../../common/taglibs.jsp" %>
<!DOCTYPE html>
<html lang="en">
<head>
  <%@ include file="../../common/head.jsp" %>
</head>
<body class="page-body" style="width: 100%;">
<!--search-->
<div class="main-content">
  <section class="roleList panel panel-default">
    <div class="row marg0 panel-body">
      <div class="row">
        <div class="col-sm-10"></div>
        <div class="col-sm-2">
          <button class="btn btn-pink" onclick="window.location.href='editMenu?parentMenu.id=${parentMenu.id }'">
            <i class="fa-plus"></i>
            <span>新增菜单</span>
          </button>
        </div>
      </div>


      <table class="table table-bordered table-striped box" id="example-2">
        <thead>
        <tr>
          <th class="no-sorting">
            编号
          </th>
          <th>菜单编码</th>
          <th>菜单名称</th>
          <th>创建时间</th>
          <th>操作</th>
        </tr>
        </thead>
        <tbody class="middle-align">
<c:forEach  items="${page.list}" var="menu" varStatus="i">
        <tr>
          <td>${ i.count }</td>
          <td><c:out value="${menu.menuCode}" /></td>
          <td><c:out value="${menu.menuName}" /></td>
          <td>${menu.createTime}</td>
          <td>
            <a href="editMenu?id=${menu.id}" class="btn btn-secondary btn-sm btn-icon icon-left">
              详情
            </a>
            <a delUrl="delMenu"  delId="${menu.id}"  href="javascript:;" class="btn btn-warning btn-sm btn-icon icon-left remove-btn">
              删除
            </a>
          </td>
        </tr>
  </c:forEach>


        </tbody>
      </table>
      <ul class="pagination fr">

      </ul>
    </div>
  </section>

</div>

<!-- Imported styles on this page -->
<link rel="stylesheet" href="${ctx}/static/admin/assets/js/daterangepicker/daterangepicker-bs3.css">
<link rel="stylesheet" href="${ctx}/static/admin/assets/js/select2/select2.css">
<link rel="stylesheet" href="${ctx}/static/admin/assets/js/select2/select2-bootstrap.css">
<link rel="stylesheet" href="${ctx}/static/admin/assets/js/multiselect/css/multi-select.css">

<!-- Bottom Scripts -->
<script src="${ctx}/static/admin/assets/js/bootstrap.min.js"></script>
<script src="${ctx}/static/admin/assets/js/TweenMax.min.js"></script>
<script src="${ctx}/static/admin/assets/js/resizeable.js"></script>
<script src="${ctx}/static/admin/assets/js/joinable.js"></script>
<script src="${ctx}/static/admin/assets/js/xenon-api.js"></script>
<script src="${ctx}/static/admin/assets/js/xenon-toggles.js"></script>
<script src="${ctx}/static/admin/assets/js/moment.min.js"></script>


<!-- Imported scripts on this page -->
<script src="${ctx}/static/admin/assets/js/jquery-validate/jquery.validate.min.js"></script>
<script src="${ctx}/static/admin/assets/js/daterangepicker/daterangepicker.js"></script>
<script src="${ctx}/static/admin/assets/js/datepicker/bootstrap-datepicker.js"></script>
<script src="${ctx}/static/admin/assets/js/timepicker/bootstrap-timepicker.min.js"></script>
<script src="${ctx}/static/admin/assets/js/colorpicker/bootstrap-colorpicker.min.js"></script>
<script src="${ctx}/static/admin/assets/js/select2/select2.min.js"></script>
<script src="${ctx}/static/admin/assets/js/jquery-ui/jquery-ui.min.js"></script>
<script src="${ctx}/static/admin/assets/js/selectboxit/jquery.selectBoxIt.min.js"></script>
<script src="${ctx}/static/admin/assets/js/tagsinput/bootstrap-tagsinput.min.js"></script>
<script src="${ctx}/static/admin/assets/js/typeahead.bundle.js"></script>
<script src="${ctx}/static/admin/assets/js/handlebars.min.js"></script>
<script src="${ctx}/static/admin/assets/js/multiselect/js/jquery.multi-select.js"></script>

<!-- JavaScripts initializations and stuff -->
<script src="${ctx}/static/admin/assets/js/xenon-custom.js"></script>

<script src="${ctx}/static/admin/js/common/common.js"></script>
<script src="${ctx}/static/common/js/base.js"></script>
<script src="${ctx}/static/common/js/checkbox.js"></script>
<script src="${ctx}/static/common/js/page.js"></script>
<script type="text/javascript">
  var curpage='${page.pageNo}';
  var totalPage='${page.totalPage}';
</script>
<script>

  $(document).ready(function() {
    createCheckboxDefaultValue("menuStatus", null, "menuStatus", "status.dictId", "${statuss}", "dictId");
    removeData();
  })

</script>
</body>
</html>
