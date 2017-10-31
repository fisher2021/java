<!DOCTYPE HTML>
<html>
<head>
    <#include "../../common/hear.ftl">
    <title>试题管理</title>
</head>
<body>
<form action="${ctx}/back/questions/page" method="post">
<input type="hidden" name="pageNo" value="${page.pageNo }" id="pageNo">
<input type="hidden" name="exam" value="${(form.exam)! }" id="exam">
    <nav class="breadcrumb">
        <i class="Hui-iconfont">&#xe67f;</i> 首页 <span class="c-gray en">&gt;</span> 考试管理 <span  class="c-error"><span class="c-gray en">&gt;</span> 试题管理 </span>
        <a class="btn btn-success radius r" style="line-height:1.6em;margin-top:3px" href="javascript:location.reload();" title="刷新" ><i class="Hui-iconfont">&#xe68f;</i></a>
    </nav>

    <div class="page-container">
        <div class="operation_column">

            <span class="va-m">试题名称：</span>
            <span class="search-box mr-20">
                <i class="Hui-iconfont search_icon">&#xe709;</i>
                <input type="text" name="title" value="${form.title!""}" class="input-text radius" placeholder="试题名称">
            </span>

            <span class="va-m">题型：</span>
            <span class="search-box mr-20">
                <select class="select input-text radius va-m "  style="min-width: 100px" name="questionsType">
                    <option value="">全部</option>
                    <#list questionsType as t>
                        <option value="${t}" <#if form.questionsType?exists&&form.questionsType==t>selected</#if>>${t.desc}</option>
                    </#list>
                </select>
            </span>

            <span class="va-m">类型：</span>
            <span class="search-box mr-20">
                <select class="select input-text radius va-m " style="min-width: 100px" name="type">
                    <option value="">全部</option>
                <#list type as t>
                    <option value="${t}" <#if form.type?exists&&form.type==t>selected</#if>>${t.desc}</option>
                </#list>
                </select>
            </span>

            <#if form.exam?exists>
            <span class="va-m">状态：</span>
            <span class="search-box mr-20">
                <select class="select input-text radius va-m" style="min-width: 100px" name="examHave">
                        <option value="0" <#if !form.examHave>selected</#if> >未添加</option>
                        <option value="1" <#if form.examHave>selected</#if> >已添加</option>
                </select>
            </span>
            <button type="button" class="btn btn-danger radius " onclick="addQuestions(ids())">添加试题</button>
            </#if>
            <button type="submit" class="btn btn-danger radius ">搜索</button>

            <a href="javascript:;" onclick="admin_edit('新增','${ctx}/back/questions/info/0')" class="btn btn-danger-outline radius f-r"><i class="Hui-iconfont">&#xe600;</i> 新增试题</a>
        </div>

        <table class="table table-border table-bordered table-bg table-striped">
            <thead>
                <tr class="text-c">
                    <th><input type="checkbox"/></th>
                    <th width="col-sm-1">序号</th>
                    <th width="col-sm-1">题目名称</th>
                    <th width="col-sm-1">类型</th>
                    <th width="col-sm-1">题型</th>
                    <th width="col-sm-1">分数</th>
                    <th width="col-sm-2">操作</th>
                </tr>
            </thead>
            <tbody>
                <#if page?exists&&page.list?size gt 0 >
                    <#list page.list as item>
                    <tr class="text-c">
                        <td><input type="checkbox" name="items" value="${item.id}" id="ck_${item.id}" /></td>
                        <td>${item_index+1}</td>
                        <td class="text-l" style="max-width: 300px">${item.title}</td>
                        <td>${item.type.desc}</td>
                        <td>${item.examType.desc}</td>
                        <td>${item.score}</td>
                        <td>
                            <a title="编辑" onclick="admin_edit('编辑','${ctx}/back/questions/info/${item.id}')" class="ml-5" ><i class="Hui-iconfont i-font-size-mid">&#xe6df;</i></a>
                            <a title="删除" href="javascript:;" onclick="admindel('${ctx}<#if !form.exam?exists>/back/questions/delete/<#else>/back/exam/delete/${form.exam}/</#if> '+${item.id},this)" class="ml-5" ><i class="Hui-iconfont i-font-size-mid">&#xe6e2;</i></a>
                        </td>
                    </tr>
                    </#list>
                <#else>
                    <tr class="text-c">
                        <td colspan="7">暂无数据...</td>
                    </tr>
                </#if>
            </tbody>
        </table>
        <div class="btn_column radius">
            <button type="button" class="btn btn-danger radius" onclick="batchDel('${ctx}<#if !form.exam?exists>/back/questions/delete/<#else>/back/exam/delete/${form.exam}/</#if>',ids())">批量删除</button>
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

    function addQuestions(ids) {
        if(ids==null||ids=='') {
            layer.msg('至少选一个!', {icon: 1, time: 1000});
            return;
        }
        $.post("${ctx}/back/exam/add/${(form.exam)!}",{ids:ids},function (data) {
            layer.msg(data.msg,{icon:1,time:1000},function () {
                window.location.reload(true);
            });
        },"json")
    }
</script>
</html>