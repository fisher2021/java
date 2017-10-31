package com.upd.common.util.pay.alipay.config;

import com.upd.common.util.pay.alipay.sign.RSA;
import com.upd.common.util.pay.alipay.util.AlipayCore;
import com.upd.common.util.prop.PropKit;

import javax.servlet.http.HttpServletRequest;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Map;

public class AlipayConfig {
//	public static String partner = "2088421644927885";
//	public static String seller_id = "haiwaixunyi@qq.com";
    public static String partner = "2088721253902210";
    public static String seller_id = "1462891112@qq.com";
	public static String log_path = "E:\\";
	public static String input_charset = "utf-8";
	public static String sign_type = "RSA";
	// 商户的私钥
//	public static String key = "e47qzy9shqpqs6xqr5cz9zjytqg94sjf";
    public static String key = "h071ai24762ies398m6debxxk6588ck7";
//	public static final String NOTIFY_URL="http://139.196.105.129:8080/onlineEduFront/repay/zhifubaopaybacks";
	//服务端回调用
//	public static final String ALIPAY_PUBLIC_KEY="MIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQCnxj/9qwVfgoUh/y2W89L6BkRAFljhNhgPdyPuBV64bfQNN1PjbCzkIM6qRdKBoLPXmKKMiFYnkd6rAoprih3/PrQEB/VsW8OoM8fxn67UDYuyBTqA23MML9q1+ilIZwBC2AQ2UBVOrFXfFl75p6/B5KsiNG9zpgmLCUYuLkxpLQIDAQAB";
    public static final String ALIPAY_PUBLIC_KEY="MIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQCnxj/9qwVfgoUh/y2W89L6BkRAFljhNhgPdyPuBV64bfQNN1PjbCzkIM6qRdKBoLPXmKKMiFYnkd6rAoprih3/PrQEB/VsW8OoM8fxn67UDYuyBTqA23MML9q1+ilIZwBC2AQ2UBVOrFXfFl75p6/B5KsiNG9zpgmLCUYuLkxpLQIDAQAB";
    //OPENSSL
//	public static final String PUBLIC_KEY="MIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQDXGxunggl7s6xEsXexgklYNHTCu+8e7aqO7saC9O3Away5QhajOfU6ETMatSmBGAOpN4QC5qrQVyqtvGL3J7ALjVLEvtuyReC1J5w/7KOraJysl+n0CaMwKvTcW5dUH4MGfs21jllg30WKnPlzy044WBliWxS7vKxXD2kMkKa1lwIDAQAB";
//	public static final String PRIVATE_KEY="MIICeAIBADANBgkqhkiG9w0BAQEFAASCAmIwggJeAgEAAoGBANcbG6eCCXuzrESxd7GCSVg0dMK77x7tqo7uxoL07cDBrLlCFqM59ToRMxq1KYEYA6k3hALmqtBXKq28YvcnsAuNUsS+27JF4LUnnD/so6tonKyX6fQJozAq9Nxbl1QfgwZ+zbWOWWDfRYqc+XPLTjhYGWJbFLu8rFcPaQyQprWXAgMBAAECgYAU/+J9QeanGu1Qb3rd63g5kjVr5Wj8vfsOpSTgGAU18jJoi5kmKjxUhOVWUCVTIwMIfBD5L4exezEBcQjAVDGNYVR72kQEdnJKW3GZeVFfez2i9yLTKYSEvIWL1kZBkCboeryoAoePOpi5kTguJSDsUf8Y5JI1TMazm1KowmAw8QJBAO+l0stfuCafAnX4RkGtJKzTy3GG3FU7Jcya2dkX/8bpjamw1yUmsdtw1pwrTth8BGhR6ycaZSOjDDTcBXZJ1hUCQQDlyJYh52vmKy3IcwsTmhDAipyeHOYTVC8jcR9n7OPlBkffEgkYykK8IVxMR2hbc3mugIckKz0cRvnyjDlltVP7AkEA4uaLfjmj3xVgvBeVVau89NIXZub8iMpW0LGxH8aES25ozcj8+1T2w0HEQNsUbQ6HEcq2LjDkn+hJr2a+2S+xvQJBAL0vhxxm8xlQV6N/xJDg+gXsmcI8PNmRKDZf/uGL0p5Pq4Dn0oGMlaFbPii3ah5RVs/rllibGPddIJEoDmHFB10CQQCW14QDfQoMjpAX/urr/J/BNf/E93lYgOylajfvVYvUslOmgZhxbuHFA15Ds8rIM4cIyTtw56G8PEvfJDSw2xTE";
    //RSA
    public static final String PUBLIC_KEY="MIIBIjANBgkqhkiG9w0BAQEFAAOCAQ8AMIIBCgKCAQEAj5GplAbdXtD/FNy92d3TeZjErWDwboHljMkmg3bo6sfw3WLqQgbPXZ3x5+wRlsbp2LH63Nkm9extAspUWoNGAp5B9s0XpW0YUcWXJRqGsTlThRLpr1QW1j/b3/ycCvevqIIJA4w/4YdTBo3Kw6Hnch0oJHlBfITTTaHD86zO/+SoI0cqWgjXLEo1RkM/XBJAqTMRXNyGrFTTwUEcHrFKAe0DY1dFmT4YtAX+YToCBPjRDKjJ/88c/e4WQ2nz5d1fky9pzVyX1hPN/CDXU26pTzW8q10qIt3X6U7jCmyxO3P+ZzA6ZIsI5h9EqEPf/StBxiiu8qYIbChqJ6xPSwsgywIDAQAB";
    public static final String PRIVATE_KEY="MIIEvQIBADANBgkqhkiG9w0BAQEFAASCBKcwggSjAgEAAoIBAQCPkamUBt1e0P8U3L3Z3dN5mMStYPBugeWMySaDdujqx/DdYupCBs9dnfHn7BGWxunYsfrc2Sb17G0CylRag0YCnkH2zRelbRhRxZclGoaxOVOFEumvVBbWP9vf/JwK96+oggkDjD/hh1MGjcrDoedyHSgkeUF8hNNNocPzrM7/5KgjRypaCNcsSjVGQz9cEkCpMxFc3IasVNPBQRwesUoB7QNjV0WZPhi0Bf5hOgIE+NEMqMn/zxz97hZDafPl3V+TL2nNXJfWE838INdTbqlPNbyrXSoi3dfpTuMKbLE7c/5nMDpkiwjmH0SoQ9/9K0HGKK7ypghsKGonrE9LCyDLAgMBAAECggEAGyELSk2Bx0j26ppLxb+FFpUnlRucU0V4ewIHpQ/zM2pUuYDXg2KGDMqry4dwS22LAsee/9yI4C+s6boR9oiX0ibVkC1xKnFTByCvTvOUQCj0/1BzmA3rwtCOW+UAF5J2PwFTcrgt2hAYQETK6+zML2QTEdFX7o+q3iCTRpiBBRmGzi4ehgC8LJdJD7huL+CAO0g7vEAJ9vbrE3z+wk+ECqVaw6Ck1XPHd7y8IKiNN6I6KeUs0wzJXQW6MCd8ROg3AAEK+A9YLcLCVjbM0RMJKY73+wJlW3B3KVP9rit+7QwrUJnAwfPsA2pytaByN8Bhze6OK2fSQE/zB0HWpMJGAQKBgQDgqafGKpfeBRoD7o6ae/0/GcMtYUiE4IAy4VLawS/vuVMYnsqyLHHzo/v/LjdfoxlZ7rovB45tVaC16pjdPXSVEGTr5MClw01x+JHF9ahZ80BWg22vZ7/8OnajO5A4cMb7UBz//6SIed2OFx6ft7U8rsCMbAcFtmHQnZezDdKjcwKBgQCjmEfPl/kybZD8RyMoV4J7XPz5uDFQs1Y9b08eUfb310ps5bgPVHmFZ0UfAU0YyWiwaq34ZtrjtosT0n93pFj/7CI+jB3s11g3xtfFIZTRFVdE3/LHgnApNOX+778YXfXvT9v3ApIz3jtNYxsqmmjsUfElS/RkcZiOecXvc5wnSQKBgFF9uJmtAYFLIHCkq51ltCqbUay4SPHJ7TaHfOJUiOrPslCP6L5QWNUjeljJjtRl+OAiM+6zMvp9wNm7ys8VZ6BaXgEi7K4z9XeNznKb9QELtx6qdbLWU1FOc5IqHNocLlEiciJ5vFi0u6S6xiTxIhBs4vMMjE+KJFjjkQ8lyG35AoGASFcqSg31z+rLVgNO9KPduxdfdj2Q8RKcbAYwgkPUZbv52TWrsnu0UN1dn1w/71oAxSInU9SHrnImBdnrK8is4l5UtjvXb8e55tAy0aUL3XkGYhp1jOuNFWui2JzDb8781ZoGtq+YXv9CUfIlLS2if/2sGMYBh/kDcN1APaZtwqECgYEAhS96n8pMSZDzbl3VnwV6maU64BEGeuqF8upY7qaZ92cjwXiklWQ3z3h9nNS/3E84k9CsSZevhC5LhfowjtE2ZvhkTj/iSpcLJu6PsFrwvpVvSnNE0xhpGDGDcXpoCH2H+DccKeFJJDIv4q3lEmFLAMDYCTwTrUODG5ysRRrv1SI=";
    public static Map<String,String> getParameter(String out_trade_no,String subject,String body,String total_fee) {
		LinkedHashMap<String, String> map=new LinkedHashMap<String, String>();
		map.put("partner", partner);
		map.put("seller_id", seller_id);
		map.put("out_trade_no", out_trade_no);
		map.put("subject", subject);
		map.put("body", body);
		map.put("total_fee", total_fee);
		map.put("notify_url", PropKit.get("ali_callback"));
		map.put("service", "mobile.securitypay.pay");
		map.put("payment_type", "1");
		map.put("_input_charset", input_charset);
		map.put("it_b_pay", "30m");
		map.put("return_url", "m.alipay.com");
		String sign = null;
		try {
			sign = URLEncoder.encode(RSA.sign(AlipayCore.createLinkString(map,false), input_charset), input_charset);
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		map.put("sign", sign);
		map.put("sign_type", sign_type);
		return map;
	}
	public static Map<String,String> getParams(HttpServletRequest request){
		Map<String,String[]> requestParams = request.getParameterMap();
    	Map<String,String> params = new HashMap<String,String>();
		for (Iterator<String> iter = requestParams.keySet().iterator(); iter.hasNext();) {
			String name =  iter.next();
			String[] values = requestParams.get(name);
			String valueStr = "";
			for (int i = 0; i < values.length; i++) {
				valueStr = (i == values.length - 1) ? valueStr + values[i]
						: valueStr + values[i] + ",";
			}
			//乱码解决，这段代码在出现乱码时使用。如果mysign和sign不相等也可以使用这段代码转化
			//valueStr = new String(valueStr.getBytes("ISO-8859-1"), "gbk");
			params.put(name, valueStr);
		}
		return  params;
	}
}

