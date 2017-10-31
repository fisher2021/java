<%--
  Created by IntelliJ IDEA.
  User: xiao.tao
  Date: 2016/5/20
  Time: 18:05
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
"http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
    <title>完整demo</title>
    <meta http-equiv="Content-Type" content="text/html;charset=utf-8"/>



</head>
<body>
    <script id="editor" type="text/plain" style="height: 300px"/>

<!--编辑器-->
<script type="text/javascript" charset="utf-8" src="${ctx}/static/admin/js/zh_CN/uEditor/ueditor.config.js"></script>
<script type="text/javascript" charset="utf-8" src="${ctx}/static/admin/js/zh_CN/uEditor/ueditor.all.min.js"> </script>
<!--建议手动加在语言，避免在ie下有时因为加载语言失败导致编辑器加载失败-->
<!--这里加载的语言文件会覆盖你在配置项目里添加的语言类型，比如你在配置项目里配置的是英文，这里加载的中文，那最后就是中文-->
<script type="text/javascript" charset="utf-8" src="${ctx}/static/admin/js/zh_CN/uEditor/lang/zh-cn/zh-cn.js"></script>
<script src="${ctx}/static/admin/js/zh_CN/uEditor/myUEditor.js"></script>
<script type="text/javascript">
    UE.Editor.prototype._bkGetActionUrl = UE.Editor.prototype.getActionUrl;
    UE.Editor.prototype.getActionUrl = function(action) {
        if (action == 'uploadimage') {
            return WEBPATH+'${ctx }/back/article/uploadImage';
        } else if (action == 'uploadvideo') {
            return '';
        } else {
            return this._bkGetActionUrl.call(this, action);
        }
    }
</script>
</body>
</html>
