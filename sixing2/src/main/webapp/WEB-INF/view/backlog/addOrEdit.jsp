<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ include file="../common/taglibs.jsp" %>

<!DOCTYPE HTML>
<html>
<head>
    <%@ include file="../common/head.jsp" %>
	<title>待办事项添加</title>
    <style>
        .year{
            display: none;
        }
        .orgtable{
            margin: 20px 100px;
        }
        #tree-frame{width:100%;height: 500px;}
        .iframeText{margin-top: 15px;width: 50%;height: 500px;float:right }
    </style>
</head>
<body>
<article class="page-container layer-container">
    <form class="form form-horizontal" id="form-submit" enctype="multipart/form-data" method="post">
        <c:if test="${not empty backlog }">
            <input type="hidden" name="id" value="${backlog.id }">
        </c:if>
        <div class="row cl">
            <label class="form-label col-xs-4 col-sm-3">指派事项：</label>
            <div class="formControls col-xs-6 col-sm-8">
                <select name="type" id="type" onchange="change()">
                    <option value="">请选择指派事项</option>
                    <c:forEach items="${type }" var="type">
                        <option value="${type }" <c:if test="${type == backlog.type }">selected</c:if>>${type.desc }</option>
                    </c:forEach>
                </select>
            </div>
        </div>
        <div class="operate row cl cont" >
            <label class="form-label col-xs-4 col-sm-3">任务编号：</label>
            <div class="formControls col-xs-7 col-sm-8">
                <input type="number" class="input-text" autocomplete="off" value="${backlog.missionId }" placeholder="编号"
                       id="missionId" name="missionId"/>
            </div>
        </div>
        <div class="year row cl">
            <label class="form-label col-xs-4 col-sm-3">指定缴费月份：</label>
            <div class="formControls col-xs-7 col-sm-8">
                <input type="text" class="input-text radius select_user" placeholder="请选择指定缴费月份" readonly onclick="WdatePicker({lang:'zh-cn',dateFmt:'yyyy-MM'})" id="year" style="margin-right: 0;" name="year"></span>
            </div>
        </div>
        <div class="row cl">
            <label class="form-label col-xs-4 col-sm-3">指派时间：</label>
            <div class="formControls col-xs-7 col-sm-8">
                <input type="text" class="input-text radius select_user" placeholder="请选择指派时间" readonly onclick="WdatePicker({lang:'zh-cn',dateFmt:'yyyy-MM-dd'})" id="doDate" style="margin-right: 0;" name="doDate" value="${fn:substring(backlog.doDate, 0, 10) }"></span>
            </div>
        </div>
        <div class="row cl cont">
            <label class="form-label col-xs-4 col-sm-3">主题：</label>
            <div class="formControls col-xs-7 col-sm-8">
                <textarea id="subject" name="subject" style="width:100%;height:50px"  placeholder="限50字" >${backlog.subject }</textarea>
            </div>
        </div>
        <c:if test="${sessionScope.get('logedOperator').org.level != branch}">
            <div class="row cl">
                <label class="form-label col-xs-4 col-sm-3">指派组织：</label>
                <div class="formControls col-xs-7 col-sm-8">
                    <table class="table table-border table-bordered table-bg table-striped">
                        <thead>
                        <tr class="text-c">
                            <th width="col-sm-1"><input type="checkbox"/></th>
                            <th width="col-sm-2">组织</th>
                        </tr>
                        </thead>
                        <tbody>
                        <c:forEach items="${orgList }" var="org">
                            <tr class="text-c">
                                <td><input type="checkbox" value="${org.id }" name="orgs"
                                        <c:forEach items="${orgChecked }" var="org_checked">
                                            <c:if test="${org_checked.id==org.id }">checked</c:if>
                                        </c:forEach>/><br></td>
                                <td>${org.name }</td>
                            </tr>
                            <span class="parent-title va-m"></span>
                        </c:forEach>
                        </tbody>
                    </table>
                </div>
            </div>
        </c:if>
            <%--<div class="iframeText">--%>
                <%--<iframe id="tree-frame" frameborder="0"  src="${ctx}/back/user/orgUser?orgId=42"></iframe>--%>
            <%--</div>--%>
        <%--</div>--%>
        <div class="row cl">
            <div class="col-xs-9 col-sm-8 col-xs-offset-3 col-sm-offset-4">
                <button type="reset" class="btn btn-success size-M radius" id="reset">重置</button>
                <button type="button" class="btn btn-danger radius ml-50" id="submit">提交</button>
            </div>
        </div>
    </form>
</article>
<script type="text/javascript" src="${ctx }/static/back/lib/zTree/v3/js/jquery.ztree.all-3.5.min.js"></script>
<script type="text/javascript" src="${ctx }/static/back/admin/h-ui.admin/js/menu-tree.js"></script>
    <!--请在下方写此页面业务相关的脚本-->
    <script type="text/javascript">
        var uploadDomain = "${uploadDomain}";
        $().ready(function() {
            // 在键盘按下并释放及提交后验证提交表单
            $("#form-submit").validate({
                rules: {
                    type: {
						required:true,
					},
                    doDate: {
                        required:true,
                    },
                    subject: {
                        required:true,
                        maxlength:50,
                    },
                    missionId:{
						required:true,
					},
                    year:{
                        required:true,
                    }
                },
                messages: {
                    type: "",
                    doDate: "请选择指派时间",
                    missionId:"请输入任务编号",
                    year:"请选择缴费月份"
                }
            });
            $("#submit").click(function(){
                if($("#form-submit").valid()){
                    var type = $("#type").val();
                    if(type == 'GZFK' || type == 'SXHB' || type == 'ZXJF'){
                        $("#submit").attr("disabled",true);
                        $("form").ajaxSubmit(ajaxFormOption);
                    }else {
                        var missionId = $("#missionId").val();
                        $.ajax({
                            url:"${ctx}/back/backlog/ajax/exist",
                            type:"post",
                            data:{
                                "type" : type,
                                "missionId" : missionId
                            },
                            dataType:"json",
                            success:function(data){
                                if(data.data){
                                    $("#submit").attr("disabled",true);
                                    $("form").ajaxSubmit(ajaxFormOption);
                                }else {
                                    layer.msg("任务ID不存在！",{icon:5,time:2000});
                                    $("#missionId").focus();
                                }
                            }
                        });
                    }
                }
            });
            if($("#yes").is(":checked")){
                $(".link").css("display","block");
                $(".cont").css("display","none")
            }else{
                $(".link").css("display","none");
                $(".cont").css("display","block")
            }

            $("input:radio").click(function(){
                if($("#yes").is(":checked")){
                    $(".link").css("display","block");
                    $(".cont").css("display","none")
                }else{
                    $(".link").css("display","none");
                    $(".cont").css("display","block")
                }
            });
        });
        
        var ajaxFormOption = {
   			 type: "post",  //提交方式
   			 dataType:"json",
   			 url: "${ctx }/back/backlog/ajax/addOrEdit", //请求url
   			 success: function (data) { //提交成功的回调函数
                 layer.msg(data.msg,{icon:1,time:1000},function () {
                     if(data.status==1) {
                         window.parent.location.reload(true);
                     }
                 });
   			 }
   		};
        function change() {
            if($("#type").val() == 'GZFK' || $("#type").val() == 'SXHB' || $("#type").val() == 'ZXJF'){
                $(".operate").css("display","none");
            }else{
                $(".operate").css("display","block");
            }
            if($("#type").val() == 'ZXJF'){
                $(".year").css("display","block");
            }else{
                $(".year").css("display","none");
            }
        }

        //点击修改对象修改右边
        <%--$(".orgtable").on("click","td input[type='checkbox']",function () {--%>
            <%--var openId=$(this).val();--%>
            <%--window.frames["frame"].location.href="${ctx}/back/user/orgUser?orgId="+openId;--%>
            <%--$(".iframeText").find("tree-frame").attr("src","${ctx}/back/user/orgUser?orgId="+openId)--%>
            <%--window.frames["frame"].location.href="${ctx}/back/user/orgUser?orgId="+openId;--%>
            <%--window.frames["tree-frame"].location.reload(true);--%>
        <%--})--%>
    </script>
    <!--/请在上方写此页面业务相关的脚本-->
</body>
</html>