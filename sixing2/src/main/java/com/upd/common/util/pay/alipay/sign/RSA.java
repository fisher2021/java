package com.upd.common.util.pay.alipay.sign;

import com.upd.common.util.pay.alipay.config.AlipayConfig;
import org.bouncycastle.jce.provider.BouncyCastleProvider;
import sun.misc.BASE64Decoder;
import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.security.*;
import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;

public class RSA{
	
	public static final String  SIGN_ALGORITHMS = "SHA1WithRSA";
	private static RSAPrivateKey privateKey;
	private static RSAPublicKey publicKey;
	private static Cipher cipher;
	static{
		try {
			cipher =  Cipher.getInstance("RSA/ECB/PKCS1Padding",new BouncyCastleProvider());
			loadPublicKey(AlipayConfig.PUBLIC_KEY);
			loadPrivateKey(AlipayConfig.PRIVATE_KEY);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	/**
	 * 从字符串中加载公钥
	 * @param publicKeyStr 公钥数据字符串
	 * @throws Exception 加载公钥时产生的异常
	 */
	public static void loadPublicKey(String publicKeyStr) throws Exception{
		try {
			BASE64Decoder base64Decoder= new BASE64Decoder();
			byte[] buffer= base64Decoder.decodeBuffer(publicKeyStr);
			KeyFactory keyFactory= KeyFactory.getInstance("RSA");
			X509EncodedKeySpec keySpec= new X509EncodedKeySpec(buffer);
			publicKey= (RSAPublicKey) keyFactory.generatePublic(keySpec);
		} catch (NoSuchAlgorithmException e) {
			throw new Exception("无此算法");
		} catch (InvalidKeySpecException e) {
			throw new Exception("公钥非法");
		} catch (IOException e) {
			throw new Exception("公钥数据内容读取错误");
		} catch (NullPointerException e) {
			throw new Exception("公钥数据为空");
		}
	}
	public static void loadPrivateKey(String privateKeyStr) throws Exception{
		try {
			BASE64Decoder base64Decoder= new BASE64Decoder();
			byte[] buffer= base64Decoder.decodeBuffer(privateKeyStr);
			PKCS8EncodedKeySpec keySpec= new PKCS8EncodedKeySpec(buffer);
			KeyFactory keyFactory= KeyFactory.getInstance("RSA");
			privateKey= (RSAPrivateKey) keyFactory.generatePrivate(keySpec);
		} catch (NoSuchAlgorithmException e) {
			throw new Exception("无此算法");
		} catch (InvalidKeySpecException e) {
			throw new Exception("私钥非法");
		} catch (IOException e) {
			throw new Exception("私钥数据内容读取错误");
		} catch (NullPointerException e) {
			throw new Exception("私钥数据为空");
		}
	}
	//加密
	public static byte[] encrypt(Key key, byte[] data) throws Exception{
		if(key== null)throw new Exception("加密公钥为空, 请设置");
		try {
			cipher= Cipher.getInstance("RSA", new BouncyCastleProvider());
			cipher.init(Cipher.ENCRYPT_MODE, key);
			return writer(data);
		} catch (NoSuchAlgorithmException e) {
			throw new Exception("无此加密算法");
		} catch (NoSuchPaddingException e) {
			e.printStackTrace();
			return null;
		}
	}
	//解密
	public static byte[] decrypt(Key key, byte[] data) throws Exception{
		if (key== null)throw new Exception("解密私钥为空, 请设置");
		try {
			cipher= Cipher.getInstance("RSA", new BouncyCastleProvider());
			cipher.init(Cipher.DECRYPT_MODE, key);
			return writer(data);
		} catch (NoSuchAlgorithmException e) {
			throw new Exception("无此解密算法");
		} catch (NoSuchPaddingException e) {
			e.printStackTrace();
			return null;
		}catch (InvalidKeyException e) {
			throw new Exception("解密私钥非法,请检查");
		}		
	}
	public static byte[] writer(byte[] data){
		try {
			InputStream ins = new ByteArrayInputStream(data);
		    ByteArrayOutputStream writer = new ByteArrayOutputStream();
		        //rsa解密的字节大小最多是128，将需要解密的内容，按128位拆开解密
		        byte[] buf = new byte[128];
		        int bufl;
		        while ((bufl = ins.read(buf)) != -1) {
		            byte[] block = null;

		            if (buf.length == bufl) {
		                block = buf;
		            } else {
		                block = new byte[bufl];
		                for (int i = 0; i < bufl; i++) {
		                    block[i] = buf[i];
		                }
		            }
		            writer.write(cipher.doFinal(block));
		        }
		     return writer.toByteArray();
		}  catch (IllegalBlockSizeException e) {
			System.out.println("密文长度非法");
		} catch (BadPaddingException e) {
			System.out.println("密文数据已损坏");
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}
	/**
	* RSA签名
	* @param content 待签名数据
	* @param input_charset 编码格式
	* @return 签名值
	*/
	public static String sign(String content, String input_charset)
	{
        try 
        {
//        	PKCS8EncodedKeySpec priPKCS8 	= new PKCS8EncodedKeySpec( Base64.decode(privateKey) ); 
//        	KeyFactory keyf 				= KeyFactory.getInstance("RSA");
//        	PrivateKey priKey 				= keyf.generatePrivate(priPKCS8);
            java.security.Signature signature = java.security.Signature.getInstance(SIGN_ALGORITHMS);
			signature.initSign(RSA.privateKey);
            signature.update( content.getBytes(input_charset) );
            byte[] signed = signature.sign();
            return Base64.encode(signed);
        }
        catch (Exception e) 
        {
        	e.printStackTrace();
        }
        return null;
    }
	
	/**
	* RSA验签名检查
	* @param content 待签名数据
	* @param sign 签名值
	* @param ali_public_key 支付宝公钥
	* @param input_charset 编码格式
	* @return 布尔值
	*/
	public static boolean verify(String content, String sign, String ali_public_key, String input_charset)
	{
		try 
		{
			System.out.println("===="+sign);
			KeyFactory keyFactory = KeyFactory.getInstance("RSA");
	        byte[] encodedKey = Base64.decode(ali_public_key);
	        PublicKey pubKey = keyFactory.generatePublic(new X509EncodedKeySpec(encodedKey));
			java.security.Signature signature = java.security.Signature.getInstance(SIGN_ALGORITHMS);
			signature.initVerify(pubKey);
			signature.update( content.getBytes(input_charset) );
			boolean bverify = signature.verify( Base64.decode(sign) );
			return bverify;
		} 
		catch (Exception e) 
		{
			e.printStackTrace();
		}
		return false;
	}
	
	public static void main(String[] args) throws Exception {
//		String deString="partner=\"2088121766757389\"&seller_id=\"2153780245@qq.com\"&out_trade_no=\"0607065150-1650\"&subject=\"测试的商品\"&body=\"该测试商品的详细描述\"&total_fee=\"0.01\"&notify_url=\"http://notify.msp.hk/notify.htm\"&service=\"mobile.securitypay.pay\"&payment_type=\"1\"&_input_charset=\"utf-8\"&it_b_pay=\"30m\"&return_url=\"m.alipay.com\"";
//		System.out.println("===================加密=======================");
//		byte[] b=encrypt(privateKey,deString.getBytes(AlipayConfig.input_charset));
//		System.out.println(Base64.encode(b));
//		//解密
//		System.out.println("===================解密=======================");
//		byte[] d=decrypt(publicKey, b);
//		System.out.println(new String(d,AlipayConfig.input_charset));
//		System.out.println("===================校验=======================");
//		签名
//		String sign1=sign(deString, AlipayConfig.input_charset);
//		Ix/7/rehfhpxc8xZ6qXxnAwSKVIOJRq2mPCs2NbiXdMVmmRLISDVrZjsEbvUOaKHKbTvHqidbklB3f+Z38slPN5Bd/BOv2HLyt+AHrck+l5q7QVxGBqK3fMZ+M6vWZp0jwMgC8I63Zc5gXM9EiUewwm4Q8nugY7NS6JCCuOkT1Y=
//		System.out.println(sign1);
		//校验
		String encryptStr= "body=order&buyer_email=924320105@qq.com&buyer_id=2088202972471125&discount=0.00&gmt_create=2016-10-21 19:39:57&is_total_fee_adjust=Y&notify_id=2d15910335cb4600df33ee6b0a2e3bdgxe&notify_time=2016-10-21 19:39:57&notify_type=trade_status_sync&out_trade_no=20161021193821126&payment_type=1&price=0.01&quantity=1&seller_email=haiwaixunyi@qq.com&seller_id=2088421644927885&subject=order&total_fee=0.01&trade_no=2016102121001004120248900870&trade_status=WAIT_BUYER_PAY&use_coupon=N";
		String sign2="HU/Xgng4mmOUBy1uWyqCh3hhbaML292IDwzZGoO/rUrl5XzGTJbhu102PZ4/7Z0mGJgseQAHWZBhqp3IMPMvRuawXSK8dyTAssU8cn6HlxyxCLebz1P4KOJ6mQcAlr2mFxMjXNRFMQxTFF+KDajXwnaBF1uiYEHyhGJaKvREuOs=";
		boolean f=verify(encryptStr, sign2, AlipayConfig.ALIPAY_PUBLIC_KEY, AlipayConfig.input_charset);
		System.out.println(f);
	}
}
