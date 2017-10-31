package com.upd.common.util;

import java.net.InetAddress;
import java.net.UnknownHostException;

public class LocalHost {
	
	
	public static String  ipString() {
		String hostAddress="";
		try {
			hostAddress = InetAddress.getLocalHost().getHostAddress();
		} catch (UnknownHostException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return hostAddress;
	}
}
