<!DOCTYPE HTML>
<html>
<head>
    <#include "../common/hear.ftl">
</head>
<body>
<form action="${ctx}/back/room/page" method="post">
<input type="hidden" name="pageNo" value="${page.pageNo }" id="pageNo">
    <nav class="breadcrumb">
        <i class="Hui-iconfont">&#xe67f;</i> 首页 <span class="c-gray en">&gt;</span> 用户管理 <span  class="c-error"><span class="c-gray en">&gt;</span> 会议室管理 </span>
        <a class="btn btn-success radius r" style="line-height:1.6em;margin-top:3px" href="javascript:location.replace(location.href);" title="刷新" ><i class="Hui-iconfont">&#xe68f;</i></a>
    </nav>

    <div class="page-container">
        <div class="operation_column">
            <span class="va-m"> 用户名称：</span>
            <span class="search-box">
                <i class="Hui-iconfont search_icon">&#xe709;</i>
                <input type="text" name="userName" value="${form.userName!""}" class="input-text radius select_user" placeholder="请输入用户名称">
            </span>

            <span class="va-m"> 会议室名称：</span>
            <span class="search-box">
                <select class="select input-text radius va-m" style="min-width: 100px" name="roomId">
                    <option value="">全部</option>
                    <#list rooms as room>
                    <option value="${room.id}" <#if form.roomId?exists&&form.roomId==room.id>selected</#if>>${room.name}</option>
                    </#list>
                </select>
            </span>

            <button type="submit" class="btn btn-danger radius mr-20">搜索</button>
            <a href="javascript:;" onclick="admin_edit('新增','${ctx}/back/room/userPage',document.body.clientWidth-200 ,document.body.clientHeight-200)" class="btn btn-danger-outline radius f-r"><i class="Hui-iconfont">&#xe600;</i> 新增成员</a>
        </div>

        <#--<div class="btn_column radius">-->
            <#--<button type="button" class="btn btn-danger radius" onclick="batchDel('${ctx}/back/room/delete',ids())">批量删除</button>-->
        <#--</div>-->

        <table class="table table-border table-bordered table-bg table-striped">
            <thead>
                <tr class="text-c">
                    <#--<th><input type="checkbox"/></th>-->
                    <th width="col-sm-1">序号</th>
                    <th width="col-sm-1">姓名</th>
                    <th width="col-sm-1">组织名称</th>
                    <th width="col-sm-2">操作</th>
                </tr>
            </thead>
            <tbody>
                <#if page?exists&&page.list?size gt 0 >
                    <#list page.list as item>
                    <tr class="text-c">
                        <#--<td><input type="checkbox" name="items" value="${item.id}" id="ck_${item.id}" /></td>-->
                        <td>${item_index+1}</td>
                        <td>${item.user.nickname!''}</td>
                        <td>${item.room.name!''}</td>
                        <td>
                            <a title="删除" href="javascript:;" onclick="admindel('${ctx}/back/room/delete/${item.id}?room=${item.room.id}',this)" class="ml-5" ><i class="i-font-size-mid Hui-iconfont">&#xe6e2;</i></a>
                        </td>
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
            <div class="pagers f-r mr-20" style="padding-top: 3px">共 ${page.totalCount } 条记录</div>
            <div id="divPager"  class="pagers f-r"></div>
        </div>
    </div>
</form>
</body>
<script type="text/javascript">
    demo(${page.totalPage},${page.pageNo});
</script>
</html>