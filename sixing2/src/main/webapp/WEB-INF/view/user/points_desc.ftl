<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="utf-8">
    <title>积分获取帮助</title>
    <meta name="viewport" content="width=device-width, initial-scale=1,maximum-scale=1, user-scalable=no,target-densitydpi=device-dpi">
    <meta name="apple-mobile-web-app-capable" content="yes">
    <meta name="apple-mobile-web-app-status-bar-style" content="black">
    <style>
        body{
            padding: 0;
            margin: 0;
            color: #333;
            background-color: #ebebeb;
        }
        ul,li{
            padding: 0;
            margin: 0;
            list-style: none;
            background-color: white;
        }
        .c-red{
            color: #ff0000;
        }
        .font-26{
            font-size: 26px;
        }
        .integral-total{
            padding-left: 20px;
            margin-bottom: 10px;
            background-color: white;
        }
        .integral-list ul {
            padding-left: 20px;
        }
        .integral-list ul li,
        .integral-total {
            padding-right: 20px;
            height: 50px;
            line-height: 50px;
            font-size: 0;
            border-bottom: 1px solid #ebebeb;
        }
        .integral-list ul li:last-child{
            border-bottom: none;
        }
        .integral-list ul li>span,
        .integral-total>span {
            display: inline-block;
            width: 50%;
            font-size: 17px;
        }
        .integral-list ul li>span:first-child,
        .integral-total>span:first-child{
            text-align: left;
        }
        .integral-list ul li>span:last-child,
        .integral-total>span:last-child{
            text-align: right;
        }
    </style>
</head>
<body>
<div class="integral-total">
    <span>达标积分：</span>
    <span class="c-red"><span class="font-26">${(standard)!}</span>分</span>
</div>
<div class="integral-list">
    <ul>
    <#list list as item>

        <#if item_index!=0>
            <li>
                <span>${item.type.dictName}</span>
                <span class="c-red">获取${(item.value)!}分</span>
            </li>
        </#if>

    </#list>
    </ul>
</div>
<script src="${urlPath}/static/back/lib/fontsize.js"></script>
</body>
</html>