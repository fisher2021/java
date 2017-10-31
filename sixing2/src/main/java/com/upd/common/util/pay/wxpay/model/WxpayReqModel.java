package com.upd.common.util.pay.wxpay.model;

import com.upd.common.util.pay.wxpay.config.WxpayConfig;
import com.upd.common.util.pay.wxpay.util.MD5Util;

import java.io.Serializable;

public class WxpayReqModel implements Serializable {

	private static final long serialVersionUID = 1699678299514885968L;

	private String appid;
	
	private String noncestr;
	
	private String packageValue;
	
	private String partnerid;
	
	private String prepayid;
	
	private String timestamp;
	
	private String sign;

	public String getAppid() {
		return appid;
	}

	public void setAppid(String appid) {
		this.appid = appid;
	}

	public String getNoncestr() {
		return noncestr;
	}

	public void setNoncestr(String noncestr) {
		this.noncestr = noncestr;
	}

	public String getPackageValue() {
		return packageValue;
	}

	public void setPackageValue(String packageValue) {
		this.packageValue = packageValue;
	}

	public String getPartnerid() {
		return partnerid;
	}

	public void setPartnerid(String partnerid) {
		this.partnerid = partnerid;
	}

	public String getPrepayid() {
		return prepayid;
	}

	public void setPrepayid(String prepayid) {
		this.prepayid = prepayid;
	}

	public String getTimestamp() {
		return timestamp;
	}

	public void setTimestamp(String timestamp) {
		this.timestamp = timestamp;
	}

	/**
	 * 生成签名字符串
	 */
	public void sign(String type) {
		StringBuilder sb = new StringBuilder();
		if (this.appid != null) {
			sb.append("appid=");
			sb.append(this.appid);
			sb.append("&");
		}
		if (this.noncestr != null) {
			sb.append("noncestr=");
			sb.append(this.noncestr);
			sb.append("&");
		}
		if (this.packageValue != null) {
			sb.append("package=");
			sb.append(this.packageValue);
			sb.append("&");
		}
		if (this.partnerid != null) {
			sb.append("partnerid=");
			sb.append(this.partnerid);
			sb.append("&");
		}
		if (this.prepayid != null) {
			sb.append("prepayid=");
			sb.append(this.prepayid);
			sb.append("&");
		}
		if (this.timestamp != null) {
			sb.append("timestamp=");
			sb.append(this.timestamp);
			sb.append("&");
		}
		sb.append("key=");
		if(type == null || type.equals("buyer")){
			sb.append(WxpayConfig.sign_key);
		}else{
			sb.append(WxpayConfig.sign_key_seller);
		}
  		System.out.println("===2====:"+sb.toString());
		this.sign = MD5Util.MD5Encode(sb.toString(), "UTF-8").toUpperCase();
	}

	public String getSign() {
		return sign;
	}

	public void setSign(String sign) {
		this.sign = sign;
	}

	
	
	
	
}
