<!DOCTYPE HTML>
<html>
<head>
    <#include "../common/hear.ftl">
    <title>APP用户管理</title>
    <style>
        body{
            min-width: 1000px;
        }
    </style>
</head>
<body>
<nav class="breadcrumb">
    <i class="Hui-iconfont">&#xe67f;</i> 首页 <span class="c-gray en">&gt;</span> 会议室
    <span  class="c-error"><span class="c-gray en">&gt;</span> 新增用户 </span>
    <a class="btn btn-success radius r" href="javascript:location.replace(location.href);" title="刷新" ><i class="Hui-iconfont">&#xe68f;</i></a>
</nav>
<div class="page-container">
    <form action="${ctx }/back/room/userPage" method="post">
        <input type="hidden" name="pageNo" value="${page.pageNo }" id="pageNo">
        <span class="va-m">姓名：</span>
        <span class="search-box">
            <i class="Hui-iconfont search_icon">&#xe709;</i>
            <input type="text" class="input-text radius select_user" placeholder="请输入姓名关键字" name="userName" value=${form.userName!'' }>
        </span>

        <span class="va-m">组织关系：</span>
        <span class="search-box mr-20" onclick="tree_layer(this,'${ctx}')" id="change-to">
			<i class="Hui-iconfont search_icon">&#xe709;</i>
			<input type="text" name="orgName" value="${form.orgName!''}" class="input-text radius select_user" placeholder="请选择组织关系" readonly>
            <input type="hidden" name="orgId" value="${form.orgId!''}" >
		</span>

        <span class="va-m">添加到：</span>
        <span class="search-box mr-20">
            <select class="select input-text radius va-m" style="min-width: 100px" id="room" name="noRoom">
                <option value="">请选择会议室</option>
            <#list rooms as room>
                <option value="${room.id}" <#if form.noRoom?exists&&form.noRoom==room.id>selected</#if>>${room.name}</option>
            </#list>
            </select>
        </span>

        <button type="submit" class="btn btn-danger radius">搜索</button>

        <button type="button" class="btn btn-danger radius" onclick="addUser(ids())">提交</button>
    </form>

    <form action="${ctx }/back/user/list" method="post">

    </form>
</div>

    <table class="table table-border table-bordered table-bg table-striped">
        <thead>
        <tr class="text-c">
            <th width="col-sm-1"><input type="checkbox"/></th>
            <th width="col-sm-1">序号</th>
            <th width="col-sm-1">组织关系</th>
            <th width="col-sm-1">头像</th>
            <th width="col-sm-1">姓名</th>
            <th width="col-sm-1">用户名</th>
        </tr>
        </thead>
        <tbody>
        <#if page?exists&&page.list?has_content >
            <#list page.list as item>
            <tr class="text-c">
                <td><input type="checkbox" name="items" value="${item.id}" id="ck_${item.id}" /></td>
                <td>${item_index+1}</td>
                <td><#if item.org?exists> ${item.org.name!'' }</#if></td>
                <td><img src="${ctx }${item.image!'' }" alt="加载失败" width="80px" height="80px"></td>
                <td>${item.nickname!'' }</td>
                <td>${item.account!'' }</td>
            </tr>
            </#list>
        <#else>
        <tr class="text-c">
            <td colspan="5">暂无数据...</td>
        </tr>
        </#if>
        </tbody>
    </table>
    <div class="clearfix">
        <div class="pagers f-r mr-20" >共 ${page.totalCount } 条记录</div>
        <div id="divPager"  class="pagers f-r"></div>
    </div>
</div>
<script type="text/javascript" src="${ctx}/static/back/lib/laypage/1.2/laypage.js"></script>
<script type="text/javascript" src="${ctx}/static/back/admin/h-ui.admin/js/mine.js"></script>
<script type="text/javascript" src="${ctx}/static/back/admin/h-ui.admin/js/common.js"></script>
<script type="text/javascript">
    demo(${page.totalPage},${page.pageNo});

    function addUser(ids) {
        if(ids==null||ids=='') {
            layer.msg('至少选一个!', {icon: 5, time: 1000});
            return;
        }
        if($("#room").val() == ''){
            layer.msg('请选择会议室!', {icon: 5, time: 1000});
            return;
        }
        $.post("${ctx}/back/room/add/"+$("#room").val(),{ids:ids},function (data) {
            layer.msg(data.msg,{icon:1,time:1000},function () {
                window.parent.location.reload(true);
            });
        },"json")
    }
</script>
</body>
</html>