<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<c:set var="ctx" value="${pageContext.request.contextPath}"/>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<meta name="description" content="网站描述" />
<meta name="keywords" content="关键字" />
<link href="${ctx }/static/css/auth/index.css" rel="stylesheet" type="text/static/css" />
<link href="${ctx }/static/css/auth/admincp5.css" rel="stylesheet" type="text/static/css" />
<link href="${ctx }/static/css/auth/admincp13.css" rel="stylesheet" type="text/static/css" />
	<Script src="${ctx }/static/js/jquery-1.9.1.min.js"></Script>
 <script type="text/javascript" src="${ctx }/static/js/auth/menu/indexMenu.js"></script>
<title>后台管理系统</title>
<script>
	var choosen_index;
	var choosen;
	function menuclick  (){
		$(".admincp_l").find("li").click(function(){
		var html = $(this).html();
		var cur_index;
		 //设置choosen 为第一个菜单
		var menuclass = $(this).attr("class");
		$(".admincp_l").find("li").each(function(index,item){
				if(choosen_index==null&&index==0){
				 choosen=$(item);
				 choosen_index = index;
				}
			    if(html==$(item).html()){
					cur_index = index;
				}
			});
			//如果2次点击同一个菜单 不做变化	
			if(cur_index==choosen_index){
		     	return;
			}
		   //设置上一个被选中的菜单 样式还原为灰色
		   var lastMenuClass=choosen.attr("class");
		   if(lastMenuClass.indexOf("_h")>0){
		   	 lastMenuClass=lastMenuClass.substring(0,lastMenuClass.indexOf("_h"));
		   }
			choosen.attr("class",lastMenuClass);
			//设置当前被选中的菜单样式
           if(menuclass.indexOf("_h")==-1){
				menuclass=menuclass+"_h";
				$(this).attr("class",menuclass)
			}
			choosen=$(this);
			choosen_index=cur_index;
		});
		
	};
</script>
<script type="text/javascript">
var base='${ctx }';
</script>

</head>

<body>
<%@ include file="common/innerhead.jsp" %>
		<div class="admincp">
			<div class="admincp_l" id="menuLeft">
			</div>
			<div class="admincp_r">
			 <iframe frameborder="0" id="framecont" width="100%" onLoad="iFrameHeight()" src="${ctx }/static/view/blank.jsp" name="framecont" style="overflow-x:hidden;"> </iframe>
			</div>
			<div style="clear:both"></div>
		</div>
<script type="text/javascript" language="javascript">   
		function iFrameHeight() {   
		var ifm= document.getElementById("framecont");   
		var subWeb = document.frames ? document.frames["framecont"].document : ifm.contentDocument;   
		if(ifm != null && subWeb != null) {
			 if(subWeb.body.scrollHeight<800){
				ifm.height = 800;
			}else{
		   		ifm.height = subWeb.body.scrollHeight;
		  	}
		}
		if(subWeb.getElementsByTagName("div")[0]!=null&&subWeb.getElementsByTagName("div")[0].style.background=="#ffffff")
			subWeb.getElementsByTagName("div")[0].style.height=ifm.height;
	} 
</script>
</body>
</html>