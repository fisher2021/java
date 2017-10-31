/**  
 * <b>项目名：</b>表单自定义系统<br/>  
 * <b>包名：</b>com.张守兵.base.util.impl<br/>  
 * <b>文件名：</b>FileUtils.java<br/>  
 * <b>版本信息：</b><br/>  
 * <b>日期：</b>2013-10-8-上午10:32:23<br/>  
 * <b>Copyright (c)</b> 2013太极开放公司-版权所有<br/>   
 */
package com.upd.common.util;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.MappedByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.channels.FileChannel.MapMode;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.util.Assert;
import org.springframework.web.multipart.MultipartFile;
import org.apache.commons.lang.StringUtils;

import javax.servlet.http.HttpServletResponse;

/**
 * <b>类名称：</b>FileUtils<br/>
 * <b>类描述：</b><br/>
 * <b>创建人：</b>张守兵<br/>
 * <b>修改人：</b>张守兵<br/>
 * <b>修改时间：2015-05-14
 * <b>修改备注：</b><br/>
 * 
 * @version 1.0.0<br/>
 */
public class FileUtils {
	protected static final Log log = LogFactory.getLog(FileUtils.class);

	/**
	 *            文件路径
	 * @param suffix
	 *            后缀名, 为空则表示所有文件
	 * @param isdepth
	 *            是否遍历子目录
	 * @return list
	 */
	public static List<String> getFileList(String dir, String suffix, boolean isdepth) {
		Assert.hasText(dir);
		File file = new File(dir);
		Assert.notNull(file);
		Assert.isTrue(file.isDirectory());
		List<String> fileList = new ArrayList<String>();
		return getFileList(fileList, file, suffix, isdepth);
	}

	private static List<String> getFileList(List<String> fileList, File file, String suffix, boolean isdepth) {
		// 若是目录, 采用递归的方法遍历子目录
		if (file.isDirectory()) {
			File[] t = file.listFiles();

			for (int i = 0; i < t.length; i++) {
				if (isdepth || t[i].isFile()) {
					getFileList(fileList, t[i], suffix, isdepth);
					log.debug(file.getAbsolutePath());
				}
			}
		} else {
			String filePath = file.getAbsolutePath();
			if (!suffix.equals("")) {
				int begIndex = filePath.lastIndexOf("."); // 最后一个.(即后缀名前面的.)的索引
				String tempsuffix = "";

				if (begIndex != -1) {
					tempsuffix = filePath.substring(begIndex + 1, filePath.length());
					if (tempsuffix.equals(suffix)) {
						fileList.add(filePath);
						log.debug(file.getAbsolutePath());
					}
				}
			} else {
				fileList.add(filePath);
				log.debug(file.getAbsolutePath());
			}
		}
		return fileList;
	}

	public static String readFile(File file, long position, long size) throws IOException {
		FileChannel fc = null;
		StringBuffer str = new StringBuffer();
		MappedByteBuffer mbb = null;
		try {
			fc = new RandomAccessFile(file, "r").getChannel();
			mbb = fc.map(MapMode.READ_ONLY, position, size);
			//mbb.flip();

			for (int j = 0; j < size; j++) {
				str.append((char) mbb.get(j));
			}
		} catch (Exception ex) {
			log.error("", ex);
		} finally {
			fc.close();
			mbb = null;
		}

		return str.toString();
	}
	/**保存文件
	 * @param stream
	 * @param path
	 * @param filename
	 * @throws IOException
	 */
	public static String saveFileFromInputStream(InputStream stream,String path,String filename) throws IOException
	{
		String location= "images/content/"+new FileUtils().randomName() + filename.substring(filename.lastIndexOf("."));
		//System.out.println(path + location);
		File f = new File(path + location);
		if(!f.exists()){
			f.getParentFile().mkdirs();
		}
		FileOutputStream fs=new FileOutputStream(path + location);
		byte[] buffer =new byte[1024*1024];
		int bytesum = 0;
		int byteread = 0;
		while ((byteread=stream.read(buffer))!=-1)
		{
			bytesum+=byteread;
			fs.write(buffer,0,byteread);
			fs.flush();
		}
		fs.close();
		stream.close();
		return location.replace("\\", "/");
	}
	public static String saveFileFromInputStreamBypath(InputStream stream,String path,String filename) throws IOException
	{
		String location= new FileUtils().randomName() + filename.substring(filename.lastIndexOf("."));
		//System.out.println(path + location);
		File f = new File(path + location);
		if(!f.exists()){
			f.getParentFile().mkdirs();
		}
		FileOutputStream fs=new FileOutputStream(path + location);
		byte[] buffer =new byte[1024*1024];
		int bytesum = 0;
		int byteread = 0;
		while ((byteread=stream.read(buffer))!=-1)
		{
			bytesum+=byteread;
			fs.write(buffer,0,byteread);
			fs.flush();
		}
		fs.close();
		stream.close();
		return location.replace("\\", "/");
	}
	private String randomName(){
		Date date = new Date();
		StringBuilder str = new StringBuilder();
		str.append(date.getYear());
		str.append(File.separator);
		str.append(date.getMonth()+1);
		str.append(File.separator);
		str.append(date.getDate());
		str.append(File.separator);
		str.append(date.getTime());
		return str.toString();
	}
	public static String saveFileAsUrl(String Url, String targetDirPath){
		if(Url==null||Url.equals("")){
			return null;
		}
		int subfixxidex=Url.lastIndexOf(".");
		//如果不存在后缀
		if(subfixxidex<=0){
			return null;
		}
		if(Url.endsWith("_.webp")){
			Url= Url.substring(0,Url.length()-6);
		}
		String ext = Url.substring(Url.lastIndexOf("."));
		if(ext.length()>5){
			ext=".jpg";
		}


		if(checkIsImage(ext)==false){
			//return null;
		};

	/*	String ext = ".jpg";*/

		// 此方法只能用HTTP协议
		// 保存文件到本地
		// Url是文件下载地址,fileName 为一个全名(路径+文件名)文件
		String  targetFilePath=null;
		URL url;
		DataOutputStream out = null;
		DataInputStream in = null;
		try {
			url = new URL(Url);
			HttpURLConnection connection = (HttpURLConnection) url.openConnection();
			in = new DataInputStream(connection.getInputStream());
			//目标文件后缀
			String  filename = StringUtil.getUUID() + ext;
			//文件分割符
			String separtor = "/";
			String temtargetFilePath=targetDirPath+separtor + filename;
			File targetFile = new File(temtargetFilePath);
			if (!targetFile.getParentFile().exists())
				targetFile.getParentFile().mkdirs();
			out = new DataOutputStream(new FileOutputStream(targetFile));
			byte[] buffer = new byte[4096];
			int count = 0;
			while ((count = in.read(buffer)) > 0) {
				out.write(buffer, 0, count);
			}
			System.out.println("文件路径-"+Url+"下载成功");
			targetFilePath=temtargetFilePath;
		}
		catch (Exception e) {
			e.printStackTrace();
			System.out.println("文件路径-"+Url+"下载失败");
			return  null;
		}finally{
			try {
				if(out != null){
					out.close();
				}
				if(in != null){
					in.close();
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return targetFilePath;
	}
	public  static String saveFileAsFile(File file,String targetDirPath,String fileName ) {
		String  targetFilePath=null;
		try
		{
			int subfixxidex=fileName.lastIndexOf(".");
			//如果不存在后缀
			if(subfixxidex<=0){
				return null;
			}
			String ext = fileName.substring(fileName.lastIndexOf("."));
			if(checkIsImage(ext)==false){
				return null;
			};

			fileName = StringUtil.getUUID() + ext;
			//文件分割符
			//String separtor = File.separator;
			String separtor="/";
			String temtargetFilePath=targetDirPath+separtor + fileName;
			File targetFile = new File(temtargetFilePath);
			if (!targetFile.getParentFile().exists())
				targetFile.getParentFile().mkdirs();
			org.apache.commons.io.FileUtils.copyFile(file, targetFile);
			targetFilePath=temtargetFilePath;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
		return targetFilePath;
	}
	public static String doUpload(String realPath ,MultipartFile file,String targetDirPath){
		String fileName = file.getOriginalFilename();
		if(!StringUtils.isEmpty(fileName)){
			String ext = fileName.substring(fileName.indexOf("."));
			fileName = StringUtil.getUUID() + ext;

			File targetFile = new File(realPath + "/"+targetDirPath+"/" + fileName);
			if(!targetFile.exists()){
				targetFile.mkdirs();
			}
			//保存
			try {
				file.transferTo(targetFile);
			} catch (Exception e) {
				e.printStackTrace();
			}
			return targetDirPath+"/" + fileName;
		}
		return null;
	}
	/**
	 *
	 * @param Url 下载路径
	 * @param filepath 保存路径
	 * @return
	 */
	public static boolean saveUrlAs(String Url, String filepath){
		// 将文件拷贝到本地
		File targetFile = new File(filepath);
		if (!targetFile.getParentFile().exists())
			targetFile.getParentFile().mkdirs();

		// 此方法只能用HTTP协议
		// 保存文件到本地
		// Url是文件下载地址,fileName 为一个全名(路径+文件名)文件
		boolean bo=false;

		URL url;
		DataOutputStream out = null;
		DataInputStream in = null;
		try {
			url = new URL(Url);
			HttpURLConnection connection = (HttpURLConnection) url.openConnection();
			in = new DataInputStream(connection.getInputStream());
			out = new DataOutputStream(new FileOutputStream(targetFile));
			byte[] buffer = new byte[4096];
			int count = 0;
			while ((count = in.read(buffer)) > 0) {
				out.write(buffer, 0, count);
			}
			System.out.println("文件路径-"+Url+"下载成功");
			bo=true;
		}
		catch (Exception e) {
			e.printStackTrace();
			System.out.println("文件路径-"+Url+"下载失败");
		}finally{
			try {
				if(out != null){
					out.close();
				}
				if(in != null){
					in.close();
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return bo;
	}
	/**
	 * 删除单个文件
	 * @param   sPath    被删除文件的文件名
	 * @return 单个文件删除成功返回true，否则返回false
	 */
	public static boolean deleteFile(String sPath) {
		boolean	flag = false;
		File	file = new File(sPath);
		// 路径为文件且不为空则进行删除
		if (file.isFile() && file.exists()) {
			file.delete();
			flag = true;
		}
		log.debug("file delte -"+flag);
		return flag;
	}
	// 检查是否是图片格式
	public static boolean checkIsImage(String imgStr) {
		boolean flag = false;
		if (imgStr != null) {
			if (imgStr.equalsIgnoreCase(".gif")
					|| imgStr.equalsIgnoreCase(".jpg")
					|| imgStr.equalsIgnoreCase(".jpeg")
					|| imgStr.equalsIgnoreCase(".png")) {
				flag = true;
			}
		}
		return flag;
	}

	public static void main(String[]args){

	}
}
