<!DOCTYPE HTML>
<html>
<head>
    <#include "../common/hear.ftl">
    <title>试卷管理</title>
</head>
<body>
<form action="${ctx}/back/exam/page" method="post">
<input type="hidden" name="pageNo" value="${page.pageNo }" id="pageNo">
    <nav class="breadcrumb">
        <i class="Hui-iconfont">&#xe67f;</i> 首页 <span class="c-gray en">&gt;</span> 考试管理 <span  class="c-error"><span class="c-gray en">&gt;</span> 试卷管理 </span>
        <a class="btn btn-success radius r" style="line-height:1.6em;margin-top:3px" href="javascript:location.replace(location.href);" title="刷新" ><i class="Hui-iconfont">&#xe68f;</i></a>
    </nav>

    <div class="page-container">
        <div class="operation_column">
            <span class="va-m">试卷名称：</span>
            <span class="search-box mr-20">
                <i class="Hui-iconfont search_icon">&#xe709;</i>
                <input type="text" name="title" value="${form.title!""}" class="input-text radius" placeholder="试卷名称">
            </span>

            <span class="va-m">类型：</span>
            <span class="search-box mr-20">
                <select class="select input-text radius va-m" style="min-width: 100px" name="type">
                    <option value="">全部</option>
                    <#list type as t>
                        <option value="${t}" <#if form.type?exists&&form.type==t>selected</#if>>${t.desc}</option>
                    </#list>
                </select>
            </span>
            <button type="submit" class="btn btn-danger radius mr-20">搜索</button>
            <a href="javascript:;" onclick="admin_edit('新增','${ctx}/back/exam/info/0')" class="btn btn-danger-outline radius f-r"><i class="Hui-iconfont">&#xe600;</i> 新增试卷</a>
        </div>

        <table class="table table-border table-bordered table-bg table-striped">
            <thead>
                <tr class="text-c">
                    <th><input type="checkbox"/></th>
                    <th width="col-sm-1">编号</th>
                    <th width="col-sm-1">试卷名称</th>
                    <th width="col-sm-1">类型</th>
                    <th width="col-sm-1">题量</th>
                    <th width="col-sm-1">及格分数</th>
                    <th width="col-sm-1">考核时间</th>
                    <th width="col-sm-1">有效时间</th>
                    <th width="col-sm-2">操作</th>
                </tr>
            </thead>
            <tbody>
            <#if page?exists&&page.list?size gt 0 >
                    <#list page.list as item>
                    <tr class="text-c">
                        <td><input type="checkbox" name="items" value="${item.id}" id="ck_${item.id}" /></td>
                        <td>${item.id}</td>
                        <td class="text-l" style="max-width: 300px">${item.title}</td>
                        <td>${item.type.desc}</td>
                        <td>${item.questions?size}</td>
                        <td>${item.pass}</td>
                        <td>${item.duration}</td>
                        <td>${item.expire?string("yyyy/MM/dd")}</td>
                        <td>
                            <a title="编辑" href="javascript:;" onclick="admin_edit('编辑','${ctx}/back/exam/info/${item.id}',1000,600)" class="ml-5" ><i class="Hui-iconfont i-font-size-mid">&#xe6df;</i></a>
                            <a title="选择试题" href="javascript:;" onclick="admin_edit('选择试题','${ctx}/back/questions/page?exam=${item.id}&examHave=0',1100,600)" class="ml-5" ><i class="Hui-iconfont i-font-size-mid">&#xe6f5;</i></a>
                            <a title="删除" href="javascript:;" onclick="admindel('${ctx}/back/exam/delete/'+${item.id},this)" class="ml-5" ><i class="Hui-iconfont i-font-size-mid">&#xe6e2;</i></a>
                        </td>
                    </tr>
                    </#list>
                <#else>
                    <tr class="text-c">
                        <td colspan="9">暂无数据...</td>
                    </tr>
                </#if>
            </tbody>
        </table>
        <div class="btn_column radius">
            <button type="button" class="btn btn-danger radius" onclick="batchDel('${ctx}/back/exam/delete',ids())">批量删除</button>
        </div>
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