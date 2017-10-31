package com.upd.common.util;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import javax.servlet.http.HttpServletResponse;

/**
 * @(#)StringUtil.java 
 *       
 * 系统名称：     
 * 版本号：      1.0
 *  
 * Copyright (c)   ,All rights reserved 
 * 
 * 作者: 	  xiaotao
 * 创建日期:    2015年11月25日
 * 
 * 包名：
 * 功能描述：
 * 公用方法描述：
 * 
 * 修改人：
 * 修改日期：
 * 修改原因：
 * 
 **/
public class FileDownLoadUtil {
	
	 /**
	 * 
	 * @description 文件下载
	 * @createDate 2015-12-17
	 * @updateDate   
	 * @author xiaotao
	 * @return  
	 */
	 public static void download(String path, HttpServletResponse response,String fileName) {
	     try {  
		     // path是指欲下载的文件的路径。  
		     File file = new File(path);  
		     // 取得文件名。  
		     String filename = fileName;
		     // 以流的形式下载文件。  
		     InputStream fis = new BufferedInputStream(new FileInputStream(path));  
		     byte[] buffer = new byte[fis.available()];  
		     fis.read(buffer);  
		     fis.close();  
		     // 清空response  
		     response.reset();  
             //设置response的Header
		     response.addHeader("Content-Disposition", "attachment;filename=" + new String(filename.getBytes()));
		     response.addHeader("Content-Length", "" + file.length());
		     OutputStream toClient = new BufferedOutputStream(response.getOutputStream());
		     response.setContentType("application/vnd.ms-excel;charset=UTF-8");
		     toClient.write(buffer);  
		     toClient.flush();  
		     toClient.close();  
	     } catch (IOException ex) {  
	            ex.printStackTrace();  
	        }  
	    }  

}

