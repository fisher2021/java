package com.upd.common.util.pay.wxpay.util;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;

public class WxpayHttpUtil {

	/**
	 * Http Post提交
	 * 
	 * @param url
	 * @param encode
	 */
	public static String[] post(String url, String xml, String encode) {
		String[] ret = new String[2];
		String result = "";
		try {
			byte[] xmlBytes = xml.getBytes(encode);
			URL urlObj = new URL(url);
			HttpURLConnection conn = null;
			// 打开链接
			conn = (HttpURLConnection) urlObj.openConnection();
			conn.setRequestMethod("POST");
			conn.setDoOutput(true);
			conn.setDoInput(true);
			conn.setRequestProperty("charset", encode);
			conn.setRequestProperty("Content-Type", "text/xml");
			// 写入输出流，进行post提交
			OutputStream out = null;
			out = conn.getOutputStream();
			out.write(xmlBytes);

			// 取得http请求返回code
			ret[0] = String.valueOf(conn.getResponseCode());
			// 取得返回xml字符串
			InputStream in = null;
			in = conn.getInputStream();
			BufferedReader reader = new BufferedReader(new InputStreamReader(in));
			String line = null;
			while ((line = reader.readLine()) != null) {
				result += line;
			}
			reader.close();

			conn.disconnect();
			ret[1] = result;
		} catch (Exception e) {
			return null;
		}

		return ret;
	}

	public static String[] post(String url, String xml) {
		return post(url, xml, "UTF-8");
	}
}