<!DOCTYPE HTML>
<html>
<head>
    <#include "../../common/hear.ftl">
    <title>试卷管理</title>
</head>
<body>
<form action="${ctx}/back/score/page" method="post">
<input type="hidden" name="pageNo" value="${page.pageNo }" id="pageNo">
    <nav class="breadcrumb">
        <i class="Hui-iconfont">&#xe67f;</i> 首页 <span class="c-gray en">&gt;</span> 考试管理 <span  class="c-error"><span class="c-gray en">&gt;</span> 成绩管理 </span>
        <a class="btn btn-success radius r" style="line-height:1.6em;margin-top:3px" href="javascript:location.replace(location.href);" title="刷新" ><i class="Hui-iconfont">&#xe68f;</i></a>
    </nav>

    <div class="page-container">
        <div class="operation_column">
            <span class="va-m">试卷名称：</span>
            <span class="search-box mr-20">
                <i class="Hui-iconfont search_icon">&#xe709;</i>
                <input type="text" name="title" value="${form.title!''}" class="input-text radius" placeholder="试卷名称">
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

            <span class="va-m">分数：</span>
            <span class="search-box">
				<i class="Hui-iconfont search_icon">&#xe709;</i>
				<input type="text" name="startValue" value="${form.startValue!''}" class="input-text radius select_user" style="margin-right: 0;width: 60px">
			</span>
            -
            <span class="search-box">
				<i class="Hui-iconfont search_icon">&#xe709;</i>
				<input type="text" name="endValue" value="${form.endValue!''}" class="input-text radius select_user" style="margin-right: 0;width: 60px">
            </span>

            <button type="submit" class="btn btn-danger radius mr-20">搜索</button>
        </div>

        <div class="btn_column radius">
            <span class="va-m mr-20">合格率：<#if count.total gt 0>${count.pass/count.total*100}%<#else>0%</#if></span>
            <span class="va-m">参与人数：${count.total}</span>
        </div>

        <table class="table table-border table-bordered table-bg table-striped">
            <thead>
                <tr class="text-c">
                    <th width="col-sm-1">序号</th>
                    <th width="col-sm-1">试卷名称</th>
                    <th width="col-sm-1">类型</th>
                    <th width="col-sm-1">提交人</th>
                    <th width="col-sm-1">分数</th>
                    <th width="col-sm-1">总分</th>
                    <th width="col-sm-1">是否通过</th>
                    <th width="col-sm-1">考核时间</th>
                </tr>
            </thead>
            <tbody>
                <#if page?exists&&page.list?size gt 0 >
                    <#list page.list as item>
                    <tr class="text-c">
                        <td>${item_index+1}</td>
                        <td>${item.exam.title!''}</td>
                        <td>${item.exam.type.desc!''}</td>
                        <td>${item.user.nickname!''}</td>
                        <td>${item.value!''}</td>
                        <td>${item.exam.total!"0"}</td>
                        <td>${item.pass?string("通过","未通过")}</td>
                        <td>${item.createTime!''}</td>
                    </tr>
                    </#list>
                <#else>
                    <tr class="text-c">
                        <td colspan="9">暂无数据...</td>
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