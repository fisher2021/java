<#assign urlPath=request.contextPath>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="utf-8">
    <title>详情</title>
    <meta name="viewport" content="width=device-width, initial-scale=1,maximum-scale=1, user-scalable=no,target-densitydpi=device-dpi">
    <meta name="apple-mobile-web-app-capable" content="yes">
    <meta name="apple-mobile-web-app-status-bar-style" content="black">
    <style>
        body{
            padding: .5rem .5rem;
            margin: 0;
        }
        body,html{
            width: 100%;
            box-sizing: border-box;
        }
        table{
            width: 100%;
            min-height: 700px;
            font-size: .6rem;
            text-align: center;
            border: 1px solid #999;
        }
        table td{
            /*width: 2.5rem;*/
            padding: .1rem;
            word-break: break-all;
            border-left: 1px solid #999;
            border-bottom: 1px solid #999;
        }
        table tr td:first-child{
            border-left: none;
        }
        table tr:last-child td{
            border-bottom: none;
        }
        table .pic{
            width: 4.5rem;
            height: 6rem;
            padding: 0;
        }
        table .pic img{
            width: 100%;
            height: 100%;
        }
    </style>
</head>
<body>
<table cellpadding="0" cellspacing="0">
    <tr style="height: 2rem;width: 2rem">
        <td style="max-width:2rem;">姓名</td>
        <#--<td>姓名</td>-->
        <td style="max-width:4rem;" colspan="2">${info.nickname!""}</td>
        <td colspan="2" style="min-width:1.5rem;">性别</td>
        <td colspan="2" style="min-width:1rem;">${info.sex!""}</td>
        <td rowspan="3" class="pic"><img src="${urlPath}${info.image!""}"></td>
    </tr>
    <tr style="height: 2rem">
        <td>籍贯</td>
        <td colspan="2">${info.nativePlace!""}</td>
        <td colspan="2">民族</td>
        <td colspan="2">${info.nation!""}</td>
    </tr>
    <tr style="height: 2rem">
        <td>岗位</td>
        <td colspan="2">${info.job!""}</td>
        <td colspan="2">学历</td>
        <td colspan="2">${info.education!""}</td>
    </tr>
    <tr style="height: 4rem">
        <td colspan="1">入党时间</td>
        <td colspan="4">${(info.partyTime?string("yyyy-MM"))!}</td>
        <td colspan="2">出生年月</td>
        <td colspan="3">${(info.birth?string("yyyy-MM"))!}</td>
    </tr>
    <tr style="height: 4rem">
        <td colspan="1">党内职务</td>
        <td colspan="4" style="max-width: 4rem">${info.duty!""}</td>
        <td colspan="2">联系电话</td>
        <td colspan="3">${info.phone!""}</td>
    </tr>
    <#--<tr>-->
        <#--<td colspan="3">身份证号</td>-->
        <#--<td colspan="5">${info.idCard!""}</td>-->
    <#--</tr>-->
    <#--<tr>-->
        <#--<td colspan="3">现居住地</td>-->
        <#--<td colspan="5">${info.address!""}</td>-->
    <#--</tr>-->
    <#--<tr>-->
        <#--<td colspan="3">紧急联系人姓名</td>-->
        <#--<td colspan="5">${info.contact!""}</td>-->
    <#--</tr>-->
    <#--<tr>-->
        <#--<td colspan="3">紧急联系人电话</td>-->
        <#--<td colspan="5">${info.contactMobile!""}</td>-->
    <#--</tr>-->
    <tr>
        <td colspan="2">党内培训记录</td>
        <td colspan="6">${info.train!""}</td>
    </tr>
    <tr>
        <td colspan="2">奖励记录</td>
        <td colspan="6">${info.award!""}</td>
    </tr>
    <tr>
        <td colspan="2">惩罚记录</td>
        <td colspan="6">${info.punishment!""}</td>
    </tr>
</table>

<script src="${urlPath}/static/back/lib/fontsize.js"></script>
</body>
</html>