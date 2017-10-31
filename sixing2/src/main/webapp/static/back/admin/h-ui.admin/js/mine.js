/**
 * Created by lihuan on 2016/11/28.
 */
/*
 参数解释：
 title	标题
 url		请求的url
 id		需要操作的数据id
 w		弹出层宽度（缺省调默认值）
 h		弹出层高度（缺省调默认值）
 */

/*管理员-增加*/
function admin_add(title,url,w,h){
    layer_show(title,url,w,h);
}

/*管理员-删除*/
function admin_del(obj,url,ctx){
    layer.confirm('确认要删除吗？',function(index){
        //此处请求后台程序，下方是成功后的前台处理……
    	$.ajax({
			url:url,
			dataType:"json",
			success:function(data){
                layer.msg(data.msg,{icon:1,time:1000},function () {
                    if(data.status==1) {
                        window.parent.location.reload(true);
                    }
                });
			}
		})
    });
}

$.postJSON = function(url, data, callback) {
    $.post(url, data, function (result) {
        if(result.status==1) {
            if(null!=callback){
                callback(result);
            }
            layer.msg(result.msg, {icon: 1, time: 1000});
        }else{
            layer.msg(result.msg, {icon: 1, time: 1000});
        }
    }, "json");
};

/*管理员-删除*/
function admindel(url,obj){
    layer.confirm('确认要删除吗？',function(index){
        //此处请求后台程序，下方是成功后的前台处理……
        $.postJSON(url,null,function(data) {
            $(obj).parents("tr").remove();
        });
    });
}

/*批量-删除*/
function batchDel(url,ids){
    if(ids==null||ids=='') {
        layer.msg('至少选一个!', {icon: 1, time: 1000});
        return;
    }
    layer.confirm('确认要删除吗？',function(index){
        //此处请求后台程序，下方是成功后的前台处理……
        $.postJSON(url+"/"+ids,null, function(data) {
            $.each(ids.split(","), function (index, e) {
                $('#ck_' + e).parents("tr").remove();
            })
        });
    });
}
function ids() {
    var ids="";
    $('input[name="items"]:checked').each(function(){
        if($(this).attr("disabled")!="disabled"){
            ids+=$(this).val()+",";
        }
    });
    return ids.substr(0,ids.length-1);
}
/*管理员-编辑*/
function admin_edit(title,url,w,h){
    layer_show(title,url,w,h);
}

/*复制链接*/
function target_copy(content){
	 layer.open({
         type: 1,
         title:"复制地址",
         area: ['520px', '200px'], //宽高
         content: content
     });
}

/*管理员-停用*/
function admin_stop(obj,id){
    layer.confirm('确认要停用吗？',function(index){
        //此处请求后台程序，下方是成功后的前台处理……

        $(obj).parents("tr").find(".td-manage").prepend('<a onClick="admin_start(this,id)" href="javascript:;" title="启用" style="text-decoration:none"><i class="Hui-iconfont">&#xe615;</i></a>');
        $(obj).parents("tr").find(".td-status").html('<span class="label label-default radius">已禁用</span>');
        $(obj).remove();
        layer.msg('已停用!',{icon: 5,time:1000});
    });
}

/*管理员-启用*/
function admin_start(obj,id){
    layer.confirm('确认要启用吗？',function(index){
        //此处请求后台程序，下方是成功后的前台处理……


        $(obj).parents("tr").find(".td-manage").prepend('<a onClick="admin_stop(this,id)" href="javascript:;" title="停用" style="text-decoration:none"><i class="Hui-iconfont">&#xe631;</i></a>');
        $(obj).parents("tr").find(".td-status").html('<span class="label label-success radius">已启用</span>');
        $(obj).remove();
        layer.msg('已启用!', {icon: 6,time:1000});
    });
}

//修改layer样式
layer.config({
    skin: 'layer-title'
});

//分页
function demo(totalPage, pageNo){
    //显示分页
    laypage({
        cont: 'divPager', //容器。值支持id名、原生dom对象，jquery对象。【如该容器为】：<div id="page1"></div>
        pages: totalPage, //通过后台拿到的总页数
        curr: pageNo || 1, //当前页
        groups: 4,
        skin:'#dd514c',
        skip: true,
        jump: function(obj, first){ //触发分页后的回调
            if(!first){ //点击跳页触发函数自身，并传递当前页：obj.curr
            	$("#pageNo").val(obj.curr);
                document.forms[0].submit();
            }
        }
    });
};

function tree_layer(that,ctx,callback){
    admin_add('组织结构',ctx+'/static/back/tree-layer.html?id='+that.id+'&fun='+callback,'350','500')
}
/*排序*/
function changeRank(url){
    $.ajax({
        url: url,
        dataType: "json",
        success: function (data) {
            layer.msg(data.msg, {icon: 1, time: 1000}, function () {
                if (data.status == 1) {
                    // window.location.reload(true);
                    $("#form-submit").submit();
                }
            });
        }
    });
}
/*转让群主*/
function newOwner(url){
    $.ajax({
        url: url,
        dataType: "json",
        success: function (data) {
            layer.msg(data.msg, {icon: 1, time: 1000}, function () {
                $("#form-submit").submit();
            });
        }
    });
}