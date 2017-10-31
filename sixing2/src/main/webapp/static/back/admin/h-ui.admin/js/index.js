/**
 * Created by lihuan on 2016/12/28.
 */
var _hmt = _hmt || [];
(function() {
    var hm = document.createElement("script");
    hm.src = "//hm.baidu.com/hm.js?080836300300be57b7f34f4b3e97d911";
    var s = document.getElementsByTagName("script")[0];
    s.parentNode.insertBefore(hm, s);

    //选中菜单样式
    $(".Hui-aside").on("click",".menu_dropdown dt",function(){
        $(".menu_dropdown dt,.menu_dropdown dd li").removeClass("active");
        $(this).addClass("active");
    })
    $(".Hui-aside").on("click",".menu_dropdown dd li",function(){
        $(this).siblings().removeClass("active");
        $(this).addClass("active");
    })
})();
var _bdhmProtocol = (("https:" == document.location.protocol) ? " https://" : " http://");
document.write(unescape("%3Cscript src='" + _bdhmProtocol + "hm.baidu.com/h.js%3F080836300300be57b7f34f4b3e97d911' type='text/javascript'%3E%3C/script%3E"));
