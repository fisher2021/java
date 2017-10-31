<#assign urlPath=request.contextPath>
<!DOCTYPE html>
<html style="height: 100%;width: 100%">
<head>
    <meta charset="utf-8">
    <title>${info.title}</title>
    <meta name="viewport" content="width=device-width, initial-scale=1,maximum-scale=${maximum}, user-scalable=${user},target-densitydpi=device-dpi">
    <meta name="apple-mobile-web-app-capable" content="yes">
    <meta name="apple-mobile-web-app-status-bar-style" content="black">
    <link rel="stylesheet" href="${urlPath}/static/back/css/detail.css">
    <link rel="stylesheet" type="text/css" href="${urlPath}/static/back/lib/Hui-iconfont/1.0.7/iconfont.css" />
    <style>
        .pause{
            position: fixed;
            bottom: 80px;
            right:20px;
        }
        .pause img{
            width: 2rem;
            height: 2rem;
        }
        .show-hide.active{
            display: none;
        }
    </style>
</head>
<body style="height: 100%;width: 100%;overflow: hidden">
    <div style="text-align: right" class="pause">
        <a data-id="1" class="show-hide"><img src="${urlPath}/static/back/img/play.png" alt=""></a>
        <a data-id="0" class="show-hide active"><img src="${urlPath}/static/back/img/stop.png" alt=""></a>
    </div>
    <div style="height: 100%;width: 100%;overflow: scroll">
        <div class="hot-content" id="getDetail">
        <#if info.type.dictId!="gzgf">
            <h3>${info.title}</h3>
            <p class="time c-999 clearfix">
                <span class="f-l">
                    <span>${info.createTime?substring(0,16)}</span><span class="c-red ml-1">${info.author!""}</span>
                </span>
                <#if (info.count)??>
                    <span class="f-r">
                        <span class="read"><img src="${urlPath}/static/back/img/read.png ">${info.count!""}</span>
                    </span>
                </#if>
            </p>
        </#if>
            <div id="article">
                <div class="content-detail c-333">${info.content!""}</div>
            </div>
        <#--<div id="article">正在朗读的文字：<font color="red" id="font"></font></div>-->
            <audio id="m" src="http://tts.baidu.com/text2audio?idx=1&tex=${info.title}&cuid=baidu_speech_demo&cod=2&lan=zh&ctp=1&pdt=1&spd=4&per=0&vol=5&pit=5"></audio>
            <div id="audioarr" style="display:none">0</div>

    </div>
        <script src="${urlPath}/static/back/lib/jquery/1.9.1/jquery.min.js"></script>
        <script src="${urlPath}/static/back/lib/fontsize.js"></script>
        <script>
            $(function () {
                //代码来自guigs.cn
                //转载代码申明作者装BQQ:790131300
                var text=document.getElementById("article").innerText;
                var texts=Math.ceil(text.length/200);
                var textarr=new Array();
                //暂停或播放
                $('.show-hide').on('click',function () {
                    var ts = $(this),
                            id = ts.attr('data-id');
                    p(id);
                    ts.addClass('active').siblings('.show-hide').removeClass('active');
                });
                function p(id){
                    var au=document.getElementById("m");
                    if(id==1){
                        au.play();
                    }else{
                        au.pause();
                    }
                }

                //文字分段
                for(i=0;i<=texts;i++){
                    textarr[i]=text.substr(i*500,500);
                }
                //百度朗读接口
                function audiosrc(text){
                    return "http://tts.baidu.com/text2audio?idx=1&tex="+text+"&cuid=baidu_speech_demo&cod=2&lan=zh&ctp=1&pdt=1&spd=4&per=0&vol=5&pit=5";
                }
                /*修改内容*/
                function xiugai(id,text){document.getElementById(id).innerHTML=text;}
                //监控段落朗读，完毕切换下一段
                window.setInterval(x,1);
                function x(){
                    var x=document.getElementById("m");
                    var audioarrid=parseInt(document.getElementById("audioarr").innerText);
                    if(x.ended==true){
                        if(audioarrid<texts){
                            x.src=audiosrc(textarr[audioarrid]);
                            x.play();
                            xiugai("audioarr",audioarrid+1);
                            xiugai("font",textarr[audioarrid]);
                        }
                    }
                }
            });
        </script>
</body>
</html>