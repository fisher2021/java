package com.upd.common.util.ueditor.upload;

import com.upd.common.util.ueditor.define.State;
import com.upd.common.util.ueditor.upload.Base64Uploader;
import com.upd.common.util.ueditor.upload.BinaryUploader;

import java.util.Map;
import javax.servlet.http.HttpServletRequest;

public class Uploader {
	private HttpServletRequest request = null;
	private Map<String, Object> conf = null;

	public Uploader(HttpServletRequest request, Map<String, Object> conf) {
		this.request = request;
		this.conf = conf;
	}

	public final State doExec() {
		
		State state = null;

		if ("true".equals(this.conf.get("isBase64"))) {
			state = Base64Uploader.save(this.request,
					this.conf);
		} else {
			state = BinaryUploader.save(this.request, this.conf);
		}

		return state;
	}
}
