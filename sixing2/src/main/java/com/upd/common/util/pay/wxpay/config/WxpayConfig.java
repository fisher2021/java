package com.upd.common.util.pay.wxpay.config;

import com.upd.common.util.prop.PropKit;

public class WxpayConfig {

    // 应用APPID
//    public static String appid = "wxa8349a8795de2b31";
    // 商户号
//    public static String mch_id = "1396137202";
    // 签名用key
//    public static String sign_key = "b245bad1165b22f1a31e91732ff393c0";

    //卖家端 应用appid
    public static String appid_seller = "wx3fa125d3111ac28f";

    //卖家端 商户号
    public static String mch_id_seller = "1379354602";

    //卖家端 签名key
//    public static String sign_key_seller = "yNKtRizxCPidz1JXYE7374eMpjvu0Key";

    public static String unified_order_url = "https://api.mch.weixin.qq.com/pay/unifiedorder";
    // 查询订单接口
    public static String query_order_url = "https://api.mch.weixin.qq.com/pay/orderquery";

    // 交易类型(APP支付)
    public static String trade_type_app = "APP";
    // 统一下单接口
    public static String notify_url = PropKit.get("wx_callback");  //114.55.89.130:8081

    //统一下单 充值回调
//	public static String notify_url_balance = PropKit.get("buy")+"/wxPay/weixinSuccessForBalance"; //114.55.89.130:8081

    //代客下单 回调
//	public static String notify_url_ordersReplace = PropKit.get("buy")+"/wxPay/weixinSuccessForBalance";  //114.55.89.130:8081

    //业务支持  回调
//	public static String notify_url_techology = PropKit.get("buy")+"/wxPay/weixinSuccessForTechnology"; //114.55.89.130:8081

    // 返回值
    public static String SUCCESS = "SUCCESS";
    public static String FAIL = "FAIL";
    public static String OK = "OK";

    // 应用APPID
    public static String appid = "wx6f1f9181c2f1e6c7";
    // 商户号
    public static String mch_id = "1484697472";
    // 签名用key
    public static String sign_key = "4ffGzQLjXPFrq0xpQPLhOmbAXEcSct1S";
    //卖家端 签名key
    public static String sign_key_seller = "4ffGzQLjXPFrq0xpQPLhOmbAXEcSct1S";

}