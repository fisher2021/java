package com.upd.common.util.ueditor.upload;

import com.upd.business.utils.OtherConfigBundle;
import com.upd.common.util.ueditor.ConfigManager;
import com.upd.common.util.ueditor.PathFormat;
import com.upd.common.util.ueditor.define.AppInfo;
import com.upd.common.util.ueditor.define.BaseState;
import com.upd.common.util.ueditor.define.FileType;
import com.upd.common.util.ueditor.define.State;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import com.upd.common.util.ueditor.upload.StorageManager;
import org.apache.commons.codec.binary.Base64;

public final class Base64Uploader {

	public static State save(HttpServletRequest request, Map<String, Object> conf) {
	    String filedName = (String) conf.get("fieldName");
		String fileName = request.getParameter(filedName);
		byte[] data = decode(fileName);

		long maxSize = ((Long) conf.get("maxSize")).longValue();

		if (!validSize(data, maxSize)) {
			return new BaseState(false, AppInfo.MAX_SIZE);
		}

		String suffix = FileType.getSuffix("JPG");

		String savePath = PathFormat.parse((String) conf.get("savePath"),
				(String) conf.get("filename"));
		
		savePath = savePath + suffix;
		String rootPath = ConfigManager.getRootPath(request,conf);
		String physicalPath = rootPath + savePath;
//        String physicalPath = OtherConfigBundle.getConfig("upload_url") + savePath;

		State storageState = StorageManager.saveBinaryFile(data, physicalPath);

		if (storageState.isSuccess()) {
			storageState.putInfo("url", PathFormat.format(savePath));
			storageState.putInfo("type", suffix);
			storageState.putInfo("original", "");
		}

		return storageState;
	}

	private static byte[] decode(String content) {
		return Base64.decodeBase64(content);
	}

	private static boolean validSize(byte[] data, long length) {
		return data.length <= length;
	}
	
}