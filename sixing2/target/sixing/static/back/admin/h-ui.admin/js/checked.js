/**
 * Created by lihuan on 2017/1/3.
 */

$().ready(function() {

    $(document).on("click",".authority-menu-icon",function(){
        var $this=$(this).parent().next(".child-panel");
        $this.slideToggle("",function(){
            if ($this.is(':hidden')) {
                $(this).prev().find(".authority-menu-icon").removeClass("authority-menu-icon-up").addClass("authority-menu-icon-down");
            }
            else{
                $(this).prev().find(".authority-menu-icon").removeClass("authority-menu-icon-down").addClass("authority-menu-icon-up");
            }
        });
    });

    $(document).on("click",".parent-box input",function(){
        if($(this).is(":checked")){
            $(this).parent().next(".child-panel").find("input").prop("checked", true)
        }else{
            $(this).parent().next(".child-panel").find("input").prop("checked", false);
        }
    });

    $(document).on("click",".child-panel input",function(){
        var chknum = $(this).parent().siblings().size()+1;//选项总个数
        var nochk=null;
        if($(this).prop("checked")==false){
            nochk=0;
        }
        else{
            $(this).parents(".child-panel").prev().find("input").prop("checked",true);
        }
        $(this).parents(".child-panel").find("input:checkbox").each(function () {
            if($(this).prop("checked")==false){
                nochk++;
            }
        });
        if(chknum==nochk){//不全选
            $(this).parents(".child-panel").prev().find("input").prop("checked",false);
        }
    });
});