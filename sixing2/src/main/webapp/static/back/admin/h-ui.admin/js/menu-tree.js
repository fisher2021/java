/**
 * Created by lihuan on 2016/12/30.
 */

var inputId=getUrlParam("id");
var callback=getUrlParam("fun");
var setting = {
    view: {
        dblClickExpand: false,
        showLine: false,
        selectedMulti: false,
        showIcon:false,
        fontCss:{color:"#c2272d"}
    },
    data: {
        simpleData: {
            enable:true,
            idKey: "id",
            pIdKey: "pId",
            rootPId: ""
        }
    },
    callback: {
        beforeClick: function(treeId, treeNode) {
            var zTree = $.fn.zTree.getZTreeObj("treeDemo");
            // zTree.expandNode(treeNode);
            // demoIframe.attr("src",treeNode._href);
            return true;
        },
        onDblClick:function(event, treeId, treeNode) {
            parent.$('#'+inputId).find('input[type=text]').val(treeNode.name);
            parent.$('#'+inputId).find('input[type=hidden]').val(treeNode.id);
            if(null!=callback&&callback!=''&&callback!='undefined') {
                var param ={ id:treeNode.id, name:treeNode.name,objId:inputId};
                eval('parent.' + callback + '(param);');
            }
            layer_close();
        }
    }
};
function showCode(str) {
    if (!code) code = $("#code");
    code.empty();
    code.append("<li>"+str+"</li>");
}
function hr(url){
    console.log(this);
    $("#iframe_href").attr('src',url)
}
function getUrlParam(name) {
    var reg = new RegExp("(^|&)" + name + "=([^&]*)(&|$)"); //构造一个含有目标参数的正则表达式对象
    var r = window.location.search.substr(1).match(reg); //匹配目标参数
    if (r != null) return unescape(r[2]); return null; //返回参数值
}