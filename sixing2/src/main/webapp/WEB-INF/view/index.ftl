<!DOCTYPE HTML>
<html>
<head>
    <#include "common/hear.ftl">
    <title>指尖党建后台运营管理系统</title>
    <style>
        body{
            min-width: 1000px;
        }
    </style>
</head>
<body>
<header class="navbar-wrapper">
    <div class="navbar navbar-fixed-top">
        <div class="container-fluid cl">
            <span class="logo navbar-logo f-l mr-10 hidden-xs"> </span>
            <nav class="nav navbar-nav">
            </nav>
            <nav id="Hui-userbar" class="nav navbar-nav navbar-userbar hidden-xs">
                <ul class="cl">
                    <li>${logedOperator.role.name }</li>
                    <li class="dropDown dropDown_hover" style="margin-right: 25px;"> <a href="#" class="dropDown_A">${logedOperator.name } <i class="Hui-iconfont">&#xe6d5;</i></a>
                        <ul class="dropDown-menu menu radius box-shadow">
                            <li><a href="${ctx }/back/admin/logout">切换账户</a></li>
                            <li class="modify-pwd"><a>修改密码</a></li>
                            <li><a href="${ctx }/back/admin/logout">退出</a></li>
                        </ul>
                    </li>
                </ul>
            </nav>
        </div>
    </div>
</header>
<aside class="Hui-aside">
    <input runat="server" id="divScrollValue" type="hidden" value="" />
    <div class="menu_dropdown bk_2">
        <#list menuList as menu>
            <#if !menu.parent?exists>
                <dl>
                    <dt><i class="Hui-iconfont title-icon">${menu.display!''}</i>${menu.name!''}<i class="Hui-iconfont menu_dropdown-arrow">&#xe6d5;</i></dt>
                    <dd>
                        <ul>
                            <#list menuList as child>
                                <#if child.parent?exists&&menu.id==child.parent.id>
                                    <li><a data-href="<#if child.targetUrl?exists>${ctx }/${child.targetUrl!''}<#else>${ctx}/building.html</#if>" data-title="${child.name!'' }" href="javascript:void(0)">${child.name!'' }</a></li>
                                </#if>
                            </#list>
                        </ul>
                    </dd>
                </dl>
            </#if>
        </#list>
    </div>
</aside>

<div class="dislpayArrow hidden-xs"><a class="pngfix" href="javascript:void(0);" onClick="displaynavbar(this)"></a></div>
<section class="Hui-article-box">
    <div id="Hui-tabNav" class="Hui-tabNav hidden-xs">
        <div class="Hui-tabNav-wp">
            <ul id="min_title_list" class="acrossTab cl">
            </ul>
        </div>
        <div class="Hui-tabNav-more btn-group"><a id="js-tabNav-prev" class="btn radius btn-default size-S" href="javascript:;"><i class="Hui-iconfont">&#xe6d4;</i></a><a id="js-tabNav-next" class="btn radius btn-default size-S" href="javascript:;"><i class="Hui-iconfont">&#xe6d7;</i></a></div>
    </div>
    <div id="iframe_box" class="Hui-article">
        <div class="show_iframe">
            <div style="display:none" class="loading"></div>
            <iframe scrolling="yes" frameborder="0" src="../../welcome.html"></iframe>
        </div>
    </div>
</section>

<script type="text/javascript">
    //修改layer样式
    layer.config({
        skin: 'layer-title'
    });

    //修改密码弹出层
    $('.modify-pwd').click(function(){
        layer.open({
            type: 2,
            area: ['500px', '400px'],
            fix: false, //不固定
            maxmin: true,
            shade:0.4,
            title: '修改密码',
            content: '${ctx}/back/admin/changePwd'
        });
    })
</script>
</body>
</html>