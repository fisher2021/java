package com.upd.business.utils;

import org.apache.log4j.Logger;

import java.util.ResourceBundle;

/**
 * 配置文件工具类
 * @author cao.xin
 * 2017年1月12日
 */
public class OtherConfigBundle {
	//日志
	private static Logger logger = Logger.getLogger(OtherConfigBundle.class);
	
	private static ResourceBundle resourceBundle;
	
	static{
		//读取配置文件
		resourceBundle = ResourceBundle.getBundle("otherConfig");
	}
	
	/**
	 * 通过key获取properties中对应的值
	 * @param key
	 * @return
	 */
	public static String getConfig(String key){
		try {
			return resourceBundle.getString(key);
		} catch (Exception e) {
			logger.error(e.getMessage(),e);
		}
		return "";
	}
}
