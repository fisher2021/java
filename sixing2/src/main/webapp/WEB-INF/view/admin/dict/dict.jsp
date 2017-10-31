<%--
  Created by IntelliJ IDEA.
  User: admin
  Date: 2016/5/3
  Time: 15:32
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title></title>
    <link rel="stylesheet" href="${ctx}/static/admin/css/demo.css" type="text/css">
    <link rel="stylesheet" href="${ctx}/static/admin/assets/css/fonts/linecons/css/linecons.css">
    <link rel="stylesheet" href="${ctx}/static/admin/assets/css/fonts/fontawesome/css/font-awesome.min.css">
  
    <link rel="stylesheet" href="${ctx}/static/admin/assets/css/bootstrap.css">
    <link rel="stylesheet" href="${ctx}/static/admin/assets/css/xenon-core.css">
    <link rel="stylesheet" href="${ctx}/static/admin/assets/css/xenon-forms.css">
    <link rel="stylesheet" href="${ctx}/static/admin/assets/css/xenon-components.css">
    <link rel="stylesheet" href="${ctx}/static/admin/assets/css/xenon-skins.css">
    <link rel="stylesheet" href="${ctx}/static/admin/css/common/custom.css">
    <link rel="stylesheet" href="${ctx}/static/admin/assets/css/zTreeStyle.css" type="text/css">
    <script type="text/javascript" src="${ctx}/static/admin/assets/js/jquery-1.8.0.min.js"></script>
    <script type="text/javascript" src="${ctx}/static/admin/assets/js/jquery.ztree.core.js"></script>
    <script type="text/javascript" src="${ctx}/static/admin/assets/js/jquery.ztree.all.min.js"></script>

</head>
<body class="page-body" style="width: 100%">
<div class="main-content">
    <div class="row">
        <div class="col-md-4">
            <ul id="tree" class="ztree"  style="width: 250px;height: 600px;float: left"></ul>
        </div>
        <div class="col-md-8">
            <div class="row panel-body" style="border: 1px solid #e4e4e4;background-color: #FFFFFF">
                <div class="row">
                    <div class="col-sm-8"></div>
                    <div class="col-sm-4">
                        <button class="btn btn-pink" onclick="addDictType()">
                            <i class="fa-plus"></i>
                            <span>新增字典类型</span>
                        </button>
                        <button class="btn btn-pink" onclick="addDict()">
                            <i class="fa-plus"></i>
                            <span>新增字典项</span>
                        </button>
                    </div>
                </div>
                <div class="row" >
                    <%--类型分类--%>
                    <form class="form-horizontal" method="post" id="dictTypeForm" >
                        <input type="hidden" name="id" id="typeId" class="dictType">
                        <div id="dictTypeDiv" style="height: 570px;margin: 10px;display: none">
                            <label class="panel-heading" id="typeLabel" style="color: #666666;border-bottom: 1px solid #e4e4e4;width: 100%;font-size: 18px" >DictType</label>
                            <div class="form-group" style="margin-top: 20px">
                                <label class="control-label col-sm-3" style="color: #666666;font-size: 14px">字典标识符：</label>
                                <div class="col-sm-6">
                                    <input class="form-control" type="text" name="dictTypeId" id="dictTypeId">
                                </div>
                            </div>
                            <div class="form-group">
                                <label class="control-label col-sm-3" style="color: #666666;font-size: 14px">字典标识符名称：</label>
                                <div class="col-sm-6">
                                    <input type="text" class="form-control" name="dictTypeName" id="dictTypeName">
                                </div>
                            </div>
                            <div class="form-group" style="text-align: center">
                                    <input type="button" class="btn btn-info" style="width: 100px" onclick="submitF()" value="提交">
                            </div>
                        </div>
                    </form>

                    <%--dict--%>
                    <form class="form-horizontal" method="post" id="dictForm" >
                        <input type="hidden" name="id" id="id" class="dictId">
                        <input type="hidden" name="parentId" id="dictParentId">
                        <input type="hidden" name="sort" id="sort">
                        <input type="hidden" name="papa" id="papa">
                        <input type="hidden" id="mine" id="mine">
                        <div id="dictDiv" style="height: 570px;margin: 10px;display: none">
                            <label class="panel-heading" style="color: #666666;border-bottom: 1px solid #e4e4e4;width: 100%;font-size: 18px" id="dictLabel">dict</label>
                            <div class="row">
                                <div class="col-sm-1">

                                </div>

                                <div class="col-sm-10">
                                    <div style="margin-bottom: 20px"></div>
                                    <div class="row">
                                        <div class="form-group">
                                            <label class="control-label col-sm-3" style="color: #666666;font-size: 14px">所属上级:</label>
                                            <div class="col-sm-6">
                                                <input class="form-control" type="text" id="baba" disabled>
                                            </div>
                                        </div>
                                    </div>
                                    <div style="margin-bottom: 20px"></div>
                                    <div class="row">
                                        <div class="form-group">
                                            <label class="control-label col-sm-3" style="color: #666666;font-size: 14px">字典标识符：</label>
                                            <div class="col-sm-6">
                                                <input class="form-control" type="text" name="dictId" id="dictId">
                                            </div>
                                        </div>
                                    </div>
                                    <div style="margin-bottom: 20px"></div>
                                    <div class="row">
                                        <div class="form-group">
                                            <label class="control-label col-sm-3" style="color: #666666;font-size: 14px">字典标识符名称：</label>
                                            <div class="col-sm-6">
                                                <input class="form-control" type="text" name="dictName" id="dictName">
                                            </div>
                                        </div>
                                    </div>
                                    <div style="margin-bottom: 20px"></div>
                                    <div class="row">
                                        <div class="form-group">
                                            <label class="control-label col-sm-3" style="color: #666666;font-size: 14px">备注：</label>
                                            <div class="col-sm-6">
                                                <input class="form-control" type="text" name="remark" id="remark">
                                            </div>
                                        </div>
                                    </div>
                                    <div style="margin-bottom: 20px"></div>
                                    <div class="row">
                                        <div class="form-group" style="text-align: center">
                                            <input class="btn btn-info" style="width: 100px" type="button" onclick="submitFF()" value="提交">
                                        </div>
                                    </div>

                                </div>
                            </div>


                        </div>
                    </form>
                </div>


            </div>
        </div>
    </div>
</div>

<script src="${ctx}/static/admin/assets/js/common.js"></script>
<script type="text/javascript">


    //addDictType
    function addDictType(){
        ClearForm("dictTypeForm");
        $("#typeId").val(0);
        $("#dictTypeDiv").show();
        $("#dictDiv").hide();
        $("#typeLabel").html("新增父节点");
    }

    //addDict
    function addDict(){
        var parent = $("#dictParentId").val();
        if(parent==""){
            alert("请选择左边节点作为父节点");
        }else{
            $("#dictTypeDiv").hide();
            $("#dictDiv").show();
            $(".dictId").val("");
            $("#dictId").val("");
            $("#dictName").val("");
            $("#remark").val("");
            $("#dictLabel").html("新增子节点");
            $("#baba").val($("#mine").val());
        }
        $(".dictId").val(0);
    }



    //配置
    var setting = {
        async: {
            enable: true,
            url:"${ctx}/dictionary/getDate",
            autoParam:["id","sort"],
            otherParam:{"otherParam":"zTreeAsyncTest"},
            dataFilter: filter
        },
        callback: {
            onClick: onClick
        }
    };
    //过滤
    function filter(treeId, parentNode, childNodes) {
        if (!childNodes) return null;
        for (var i=0, l=childNodes.length; i<l; i++) {
            childNodes[i].name = childNodes[i].name.replace(/\.n/g, '.');
        }
        return childNodes;
    }
    //点击事件
    function onClick(event, treeId, treeNode, clickFlag) {
        $("#id").val(treeNode.id);
        if(treeNode.sort==0){
            getDictType(treeNode.id);
            $("#sort").val(treeNode.sort);
            $("#dictTypeDiv").show();
            $("#dictDiv").hide();
            $("#papa").val(treeNode.id);
            $("#baba").val(treeNode.name);
            $("#mine").val(treeNode.name);
            $("#typeLabel").html("父节点详情");
        }else{
            getDict(treeNode.id);
            $("#sort").val(treeNode.sort);
            $("#dictDiv").show();
            $("#dictTypeDiv").hide();
            $("#dictLabel").html("子节点详情");
        }
        $("#dictParentId").val(treeNode.id);
    }
    //初始化
    $(document).ready(function(){
        $.fn.zTree.init($("#tree"), setting);
    });

    //dictType数据
    function getDictType(id){
        $.ajax({
            type: 'POST',
            url:'${ctx}/dictionary/getDictType?id='+id,
            dataType:'JSON',
            success: function(data){
                $(".dictType").val(data.id);
                $("#dictTypeId").val(data.dictTypeId);
                $("#dictTypeName").val(data.dictTypeName);
            }
        });
    }
    //dict数据
    function getDict(id){
        $.ajax({
            type: 'POST',
            url:'${ctx}/dictionary/getDict?id='+id,
            dataType:'JSON',
            success: function(data){
                $(".dictId").val(data.id);
                $("#dictId").val(data.dictId);
                $("#dictName").val(data.dictName);
                $("#remark").val(data.remark);
                $("#papa").val(data.dictType.id);
                if(data.parent==null || data.parent=="null" || data.parent=="" || data.parent.dictName=="undefined"){
                    $("#baba").val(data.dictType.dictTypeName);
                }else{
                    $("#baba").val(data.parent.dictName);
                }
                $("#mine").val(data.dictName);
            }
        });
    }



    function refresh(){
        var zTree = $.fn.zTree.getZTreeObj("tree");
        zTree.reAsyncChildNodes(null, "refresh");
    }


    //提交类型
    function submitF(){
        var id= $("#typeId").val();
        if(id==""){
            $("#typeId").val("0");
        }
        var params = $("#dictTypeForm").serialize();
        $.ajax({
            type: 'POST',
            url:'${ctx}/dictionary/addDictType',
            data:params,
            dataType:'JSON',
            error:function(XMLHttpRequest, textStatus, errorThrown){
                alert('请联系管理员!',textStatus,'error');
            },
            success: function(data){
                flag = data.flag;
                message = data.message;
                if(flag){
                    ClearForm("dictTypeForm");
                    $("#dictTypeDiv").hide();
                    refresh();
                }else{
                    alert(message);
                }
            }
        });
    }
    //提交dict
    function submitFF(){
        var id= $("#id").val();
        if(id==""){
            $("#id").val("0");
        }
        var params = $("#dictForm").serialize();
        $.ajax({
            type: 'POST',
            url:'${ctx}/dictionary/addDict',
            data:params,
            dataType:'JSON',
            error:function(XMLHttpRequest, textStatus, errorThrown){
                alert('请联系管理员!',textStatus,'error');
            },
            success: function(data){
                flag = data.flag;
                message = data.message;
                if(flag){
                    ClearForm("dictForm");
                    $("#dictDiv").hide();
                    refresh();
                }else{
                    alert(message);
                }
            }
        });
    }

</script>
</body>
</html>
