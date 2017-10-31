<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ include file="../common/taglibs.jsp" %>

<!DOCTYPE HTML>
<html>
<head>
    <%@ include file="../common/head.jsp" %>
	<title>会议室添加</title>
</head>
<body>
<article class="page-container layer-container">
    <form class="form form-horizontal" id="form-submit" enctype="multipart/form-data" method="post">
        <c:if test="${not empty room }">
            <input type="hidden" name="id" value="${room.id }">
        </c:if>
        <div class="row cl">
            <label class="form-label col-xs-4 col-sm-3">会议室名称：</label>
            <div class="formControls col-xs-7 col-sm-8">
                <input type="text" class="input-text" autocomplete="off" value="${room.name }" placeholder="请输入会议室名称" id="name" name="name" />
            </div>
        </div>
        <div class="operate row cl cont" >
            <label class="form-label col-xs-4 col-sm-3">群主账号：</label>
            <div class="formControls col-xs-7 col-sm-8">
                <input type="number" class="input-text" autocomplete="off" value="${room.owner }" placeholder="群主账号" id="owner" name="owner" style="width: 150px;"/>
            </div>
        </div>
        <div class="row cl">
            <label class="form-label col-xs-4 col-sm-3">所属党会类型：</label>
            <div class="formControls col-xs-6 col-sm-8">
                <select name="type" id="type">
                    <option value="">请选择党会类型</option>
                    <c:forEach items="${types }" var="type">
                        <option value="${type }" <c:if test="${type == room.type}">selected</c:if>>${type.desc }</option>
                    </c:forEach>
                </select>
            </div>
        </div>
        <c:if test="${logedOperator.org.level != 'PARTY_BRANCH'}">
            <div class="row cl org_div" style="display: ${room.type =='DXZH' ? 'inline-block' : 'none'}">
                <label class="form-label col-xs-4 col-sm-3">所属组织：</label>
                <div class="formControls col-xs-6 col-sm-8">
                    <select name="org.id">
                        <option value="">请选择所属组织</option>
                        <c:forEach items="${orgs }" var="org">
                            <c:if test="${org.level eq 'PARTY_BRANCH'}">
                                <option value="${org.id }" <c:if test="${org.id == room.org.id}">selected</c:if>>${org.name }</option>
                            </c:if>
                        </c:forEach>
                    </select>
                </div>
            </div>
        </c:if>
        <c:if test="${logedOperator.org.level == 'PARTY_BRANCH'}">
            <input type="hidden" name="org.id" value="${logedOperator.org.id }">
        </c:if>
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
        $(document).on("change","#type",function(){
            if($(this).val()=="DXZH" ){
                $(".org_div").show();
            }else{
                $(".org_div").find("select").val("");
                $(".org_div").hide();
            }
        });
        $().ready(function() {
            // 在键盘按下并释放及提交后验证提交表单
            $("#form-submit").validate({
                rules: {
                    name: {
						required:true,
                        maxlength:10,
					},
                    'org.id': {
                        required:true,
                    },
                    owner: {
                        required:true
                    },
                    type:{
                        required:true
                    }
                },
                messages: {
                    owner: "请输入群主账号",
                    name:"请输入会议室名称",
                    type:"请选择党会类型",
                    'org.id':""
                }
            });
            $("#submit").click(function(){
                if($("#form-submit").valid()){
                    var owner = $("#owner").val();
                    $.ajax({
                        url:"${ctx}/back/room/ajax/exist",
                        type:"post",
                        data:{
                            "owner" : owner
                        },
                        dataType:"json",
                        success:function(data){
                            if(data.data){
                                $("#submit").attr("disabled",true);
                                $("form").ajaxSubmit(ajaxFormOption);
                            }else {
                                layer.msg("群主账号不存在！",{icon:3,time:2000});
                                $("#owner").focus();
                            }
                        }
                    });
                }
            });
        });
        
        var ajaxFormOption = {
   			 type: "post",  //提交方式
   			 dataType:"json",
   			 url: "${ctx }/back/room/ajax/addOrEdit", //请求url
   			 success: function (data) { //提交成功的回调函数
                 if(data.status==1) {
                     layer.msg(data.msg,{icon:1,time:1000},function () {
                         window.parent.location.reload(true);
                     });
                 }else{
                     layer.msg(data.msg,{icon:1,time:1000},function () {
                         $("#submit").attr("disabled",false);
                     });
                 }
   			 }
   		};
    </script>
    <!--/请在上方写此页面业务相关的脚本-->
</body>
</html>