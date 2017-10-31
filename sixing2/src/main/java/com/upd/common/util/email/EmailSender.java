package com.upd.common.util.email;

import javax.mail.Authenticator;
import javax.mail.PasswordAuthentication;

/**
 * 邮件发送者
 * 
 * @author liufang
 * 
 */
public class EmailSender  extends Authenticator {
	/**
	 * 登录名
	 *
	 * @return
	 */
	public String userName = "zhangshoubing@unionbigdata.com";
	/**
	 * 密码
	 *
	 * @return
	 */
	public String password = "zhangshoubing@123";

	/**
	 * 发送服务器IP
	 * 
	 * @return
	 */
	public String host="mail.unionbigdata.com";
	/**
	 * 发送服务器端口
	 *
	 * @return
	 */
	public int port=25;

	public int getPort() {
		return port;
	}

	public void setPort(int port) {
		this.port = port;
	}

	/**
	 * 发送编码
	 * 
	 * @return
	 */
	public String encoding="UTF-8";



	/**
	 * 发件人
	 *
	 * @return
	 */
	public String senduser;

	public String getSenduser() {
		return senduser;
	}

	public void setSenduser(String senduser) {
		this.senduser = senduser;
	}

	public EmailSender() {
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getHost() {
		return host;
	}

	public void setHost(String host) {
		this.host = host;
	}

	public String getEncoding() {
		return encoding;
	}

	public void setEncoding(String encoding) {
		this.encoding = encoding;
	}


	protected PasswordAuthentication getPasswordAuthentication() {
		return new PasswordAuthentication(userName, password);
	}

}
