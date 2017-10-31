<!DOCTYPE HTML>
<html>
<head>
    <#include "../common/hear.ftl">
    <title>党费管理</title>
</head>
<body>
<form action="${ctx}/back/dues/page" method="post">
<input type="hidden" name="pageNo" value="${page.pageNo }" id="pageNo">
    <nav class="breadcrumb">
        <i class="Hui-iconfont">&#xe67f;</i> 首页 <span class="c-gray en">&gt;</span> 党费管理 <span  class="c-error"><span class="c-gray en">&gt;</span> 党费管理 </span>
        <a class="btn btn-success radius r" style="line-height:1.6em;margin-top:3px" href="javascript:location.replace(location.href);" title="刷新" ><i class="Hui-iconfont">&#xe68f;</i></a>
    </nav>

    <div class="page-container">
        <div class="operation_column">
            <span class="va-m">姓名：</span>
            <span class="search-box">
                <i class="Hui-iconfont search_icon">&#xe709;</i>
                <input type="text" name="name" value="${form.name!''}" style="width: 100px" class="input-text radius select_user" placeholder="请输入姓名">
            </span>
            <#if logedOperator.org?exists && logedOperator.org.level != branch >
                <span class="va-m">组织关系：</span>
                <span class="search-box mr-20" onclick="tree_layer(this,'${ctx}')" id="change-to">
                    <i class="Hui-iconfont search_icon">&#xe709;</i>
                    <input type="text" name="orgName" value="${form.orgName!'' }" class="input-text radius select_user" placeholder="请选择组织关系" readonly>
                    <input type="hidden" name="orgId" value="${form.orgId!'' }" >
                </span>
            <#else>
            </#if>
            <span class="va-m">年月：</span>
            <span class="search-box">
				<i class="Hui-iconfont search_icon">&#xe709;</i>
				<input type="text" name="year" value="${form.year!''}" style="width: 100px" class="input-text radius select_user" placeholder="请选择年月" readonly onclick="WdatePicker({lang:'zh-cn',dateFmt:'yyyy-MM'})">
			</span>

            <span class="va-m">是否缴费：</span>
            <span class="search-box">
                <select class="select input-text radius va-m select_user" style="width: 80px" name="status">
                    <option value="">全部</option>
                    <option value="0" <#if form.status?exists&&!form.status>selected</#if>>未缴费</option>
                    <option value="1" <#if form.status?exists&&form.status>selected</#if>>已缴费</option>
                </select>
            </span>

            <button type="submit" class="btn btn-danger radius mr-20">搜索</button>
        </div>

        <div class="btn_column radius">
            <#if logedOperator.org?exists && logedOperator.org.level != branch >
                <button type="button" class="btn btn-danger radius" onclick="admin_add('批量生成党费','${ctx }/back/dues/addBatch','620','500')">批量导入</button>
                <a href="${ctx }/file/dues.xlsx" class="btn btn-danger radius"><i class="Hui-iconfont">&#xe641;</i> 下载模板</a>
                <a class="btn btn-success radius r" style="line-height:1.6em;margin-top:3px;" href="${ctx }/back/dues/export?orgId=${form.orgId!""}&month=${form.year!""}&status=<#if form.status?exists>${form.status?string('true','false')}</#if>" title="批量导出" >批量导出</a>
            </#if>
            <span class="va-m mr-20">总数：${page.totalCount!'0'}</span>
            <span class="va-m mr-20">应缴费党费：${pay.amount!'0'}元</span>
            <span class="va-m mr-20">实缴费党费：${pay.feeReceived!'0'}元</span>
        </div>

        <table class="table table-border table-bordered table-bg table-striped">
            <thead>
            <tr class="text-c">
                <th width="col-sm-1"><input type="checkbox"/></th>
                <th width="col-sm-1">序号</th>
                <th width="col-sm-1">姓名</th>
                <#--<th width="col-sm-1">工号</th>-->
                <th width="col-sm-1">手机号码</th>
                <th width="col-sm-1">应缴费</th>
                <th width="col-sm-1">已缴费</th>
                <th width="col-sm-1">年月</th>
                <th width="col-sm-1">状态</th>
                <#if logedOperator.org?exists && logedOperator.org.level != branch >
                <th width="col-sm-1">操作</th>
                </#if>
            </tr>
            </thead>
            <tbody>
            <#if page?exists&&page.list?size gt 0 >
                <#list page.list as item>
                <tr class="text-c">
                    <td><input type="checkbox" name="items" value="${item.id}" id="ck_${item.id}" /></td>
                    <td>${item_index+1}</td>
                    <td>${item.user.nickname!''}</td>
                    <#--<td>${item.user.employeeNumber!''}</td>-->
                    <td>${item.user.phone!''}</td>
                    <td>${item.amount?string("#0.00")}元</td>
                    <td>${item.feeReceived?string("#0.00")}元</td>
                    <td>${item.year!''}</td>
                    <td>${item.status?string("已缴","未缴")}</td>
                    <#if logedOperator.org?exists && logedOperator.org.level != branch >
                    <td class="td-manage">
                        <a title="编辑" href="javascript:;" onclick="admin_edit('编辑','${ctx }/back/dues/addOrEdit?id=${item.id }',500,600)" class="ml-5" style="text-decoration:none"><i class="Hui-iconfont i-font-size-mid">&#xe6df;</i></a>
                        <a title="删除" href="javascript:;" onclick="admindel('${ctx }/back/dues/delete/'+${item.id},this)" class="ml-5" style="text-decoration:none"><i class="Hui-iconfont i-font-size-mid">&#xe6e2;</i></a>
                    </td>
                    </#if>
                </tr>
                </#list>
            <#else>
            <tr class="text-c">
                <td colspan="10">暂无数据...</td>
            </tr>
            </#if>
            </tbody>
        </table>
        <form id="deleteMore">
            <div class="btn_column radius">
                <button type="button" class="btn btn-danger radius" onclick="batchDel('${ctx}/back/dues/delete',ids())">批量删除</button>
            </div>
        </form>
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