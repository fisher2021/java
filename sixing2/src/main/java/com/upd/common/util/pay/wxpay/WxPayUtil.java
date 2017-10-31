package com.upd.common.util.pay.wxpay;

import com.alibaba.fastjson.JSON;
import com.upd.common.util.HttpKit;
import com.upd.common.util.StrKit;
import com.upd.common.util.pay.wxpay.config.WxpayConfig;
import com.upd.common.util.pay.wxpay.model.WxpayReqModel;
import com.upd.common.util.pay.wxpay.util.MD5;
import com.upd.common.util.pay.wxpay.util.MD5Util;
import com.upd.common.util.security.MD5Utils;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;

public class WxPayUtil {
	/**
	 *<p>Title: name</p>
	 *<p>Description:统一下单</p>
	 */
	public String unifiedOrder(String out_trade_no,String total_fee,String attach) {
		Map<String, String> params=new HashMap<>();
		params.put("body", "指尖上的党建-党费缴纳");
		params.put("out_trade_no",out_trade_no);
		params.put("total_fee",total_fee);
		params.put("spbill_create_ip","127.0.0.1");
		params.put("appid", WxpayConfig.appid);
		params.put("mch_id",WxpayConfig.mch_id);
		params.put("nonce_str",String.valueOf(new Random().nextInt(100)));
		params.put("trade_type",WxpayConfig.trade_type_app);
		params.put("notify_url",WxpayConfig.notify_url);
        params.put("attach",attach);
		params.put("sign",PaymentKit.createSign(params, WxpayConfig.sign_key_seller));
//        params.put("sign",PaymentKit.createSign(params, WxpayConfig.sign_key));
		System.out.println(JSON.toJSONString(params));
		String xmlStr = HttpKit.post(WxpayConfig.unified_order_url, PaymentKit.toXml(params));
		Map<String, String> result=PaymentKit.xmlToMap(xmlStr);
		return appPay(result);
	}
	
	/**
	 *<p>Title: appPay</p>
	 *<p>Description:支付</p>
	 */
	public  String appPay(Map<String, String> params) {
		boolean flag=PaymentKit.verifyNotify(params, WxpayConfig.sign_key_seller);//验签
//        boolean flag=PaymentKit.verifyNotify(params, WxpayConfig.sign_key);//验签
		if(flag&& StrKit.notBlank(params.get("return_code"))&&params.get("return_code").toString().equals("SUCCESS")){
//			Map<String, String> app=new HashMap<>();
			String nonce_str=String.valueOf(new Random().nextInt(100));
			String timestamp=String.valueOf(System.currentTimeMillis() / 1000);
//			app.put("appid", params.get("appid"));
//			app.put("prepayid", params.get("prepay_id"));
//			app.put("noncestr",nonce_str);
//			app.put("package","Sign=WXPay");
//			app.put("partnerid",WxpayConfig.mch_id);
//			app.put("timestamp",timestamp);
//			app.put("sign",PaymentKit.createSign(app, WxpayConfig.sign_key_seller));
//			System.out.println("app:"+JsonKit.toJson(app));
			WxpayReqModel wxpayReqModel = new WxpayReqModel();
			wxpayReqModel.setAppid(params.get("appid"));
			wxpayReqModel.setNoncestr(nonce_str);
			wxpayReqModel.setPackageValue("Sign=WXPay");
			wxpayReqModel.setPartnerid(WxpayConfig.mch_id);
			wxpayReqModel.setPrepayid( params.get("prepay_id"));
			wxpayReqModel.setTimestamp(timestamp);
			wxpayReqModel.sign("seller");
			return JSON.toJSONString(wxpayReqModel);
		}
		return null;
	}
	public static void main(String[] args) {
//		String res=new WxPayUtil().unifiedOrder("201706091629583","190901");
//		System.out.println("res:"+res);
	}

}
