//表单异步提交
function subForm(ctx,url){
	$("#submit").attr("disabled",true);
	$.ajax({
		url:url,
		data:$('form').serialize(),
		type:"post",
		dataType:"json",
		success:function(data){
			layer.msg(data.msg,{icon:1,time:1000},function () {
				if(data.status==1) {
					window.parent.location.reload(true);
				}
			});
		}
	})
}

//留言回复
function wordsReply(ctx,url){
	$("#submit").attr("disabled",true);
	$.ajax({
		url:url,
		data:$('form').serialize(),
		type:"post",
		dataType:"json",
		success:function(data){
			if(data.success){
				layer.msg('回复成功!',{icon:1,time:1000});
				setTimeout("window.parent.location.reload(true)",1000);
			}else if(!data.success){
				window.open(ctx+"/back/loginUI","_top");
			}else{
				$("#submit").attr("disabled",false);
			}
		}
	})
}

//批量删除
function deleteMore(ctx,url){
	if($("[name='ids']:checked").length){
		$.ajax({
			url:url,
			data:$('#deleteMore').serialize(),
			dataType:"json",
			success:function(data){
				layer.msg(data.msg,{icon:1,time:1000},function () {
					if(data.status==1) {
						window.location.reload(true);
					}
				});
			}
		})
	}else{
		alert("请选择需要删除的记录！")
	}
}

//批量添加
function addMore(ctx,url){
    if($("[name='ids']:checked").length){
        $.ajax({
            url:url,
            data:$('#addMore').serialize(),
            dataType:"json",
            success:function(data){
                layer.msg(data.msg,{icon:1,time:1000},function () {
                    if(data.status==1) {
                        window.location.reload(true);
                    }
                });
            }
        })
    }else{
        alert("请选择需要添加的对象！")
    }
}

//批量通过审核
function passMore(ctx,url){
	if($("[name='ids']:checked").length){
		$.ajax({
			url:url,
			data:$('#passMore').serialize(),
			dataType:"json",
			success:function(data){
				if(!data.success && data.message=="notLoged"){
					window.open(ctx+"/back/loginUI","_top");
				}else if(data.success){
					layer.msg("批量通过成功！",{icon:1,time:1000});
					setTimeout("window.location.reload(true)",1000);
				}else if(!data.success){
					layer.msg(data.message,{icon:5,time:1000});
				}
			}
		})
	}else{
		alert("请选择需通过的申请！")
	}
}

//导出excel
function exportExcel(url){
	var issueId = $("#issueId").val();
	var checkStatus = $("#checkStatus").val();
	var startDate = $("#startDate").val();
	var endDate = $("#endDate").val();
	
	var params = "?issueId="+issueId+"&checkStatus="+checkStatus+"&startDate="+startDate+"&endDate="+endDate;
	window.location.href=url+encodeURI(params);
}